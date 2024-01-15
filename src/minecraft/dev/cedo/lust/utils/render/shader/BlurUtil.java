package dev.cedo.lust.utils.render.shader;

import dev.cedo.lust.Lust;
import dev.cedo.lust.event.impl.render.ShaderEvent;
import dev.cedo.lust.module.impl.render.NotificationsMod;
import dev.cedo.lust.utils.Utils;
import dev.cedo.lust.utils.render.RenderUtil;
import dev.cedo.lust.utils.render.StencilUtil;
import dev.cedo.lust.utils.render.shader.ShaderUtil;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.shader.Framebuffer;
import org.lwjgl.opengl.GL11;

/**
 * @author cedo
 * @since 05/18/2022
 */
public class BlurUtil implements Utils {

  private static final ShaderUtil gaussianBlur = new ShaderUtil("gaussian-blur.frag");

  private static Framebuffer blurBuffer = new Framebuffer(1, 1, false);

  private static void stuffToBlur() {
    NotificationsMod notificationsMod =
        Lust.INSTANCE.getModuleCollection().getModule(NotificationsMod.class);
    notificationsMod.renderEffects();
  }

  public static void renderGaussianBlur(float radius, float compression) {
    ScaledResolution sr = new ScaledResolution(mc);

    GlStateManager.enableBlend();
    OpenGlHelper.glBlendFunc(
        GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ZERO);

    StencilUtil.initStencilToWrite();
    stuffToBlur();
    Lust.INSTANCE.getEventProtocol().handleEvent(new ShaderEvent(false));

    StencilUtil.readStencilBuffer(1);

    blurBuffer = RenderUtil.createFrameBuffer(blurBuffer);

    gaussianBlur.init();
    blurUniforms(radius, compression, 0f);
    blurBuffer.framebufferClear();
    blurBuffer.bindFramebuffer(false);
    RenderUtil.bindTexture(mc.getFramebuffer().framebufferTexture);
    ShaderUtil.drawQuads();
    gaussianBlur.unload();
    blurBuffer.unbindFramebuffer();

    gaussianBlur.init();
    blurUniforms(radius, 0f, compression);
    mc.getFramebuffer().bindFramebuffer(false);
    RenderUtil.bindTexture(blurBuffer.framebufferTexture);
    ShaderUtil.drawQuads();
    gaussianBlur.unload();

    StencilUtil.uninitStencilBuffer();
  }

  private static void blurUniforms(float radius, float directionX, float directionY) {
    gaussianBlur.setUniformi("u_texture", 0);
    gaussianBlur.setUniformf("texelSize", 1.0f / mc.displayWidth, 1.0f / mc.displayHeight);
    gaussianBlur.setUniformf("radius", radius);
    gaussianBlur.setUniformf("direction", directionX, directionY);
  }
}

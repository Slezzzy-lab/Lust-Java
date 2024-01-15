package dev.cedo.lust.utils.render;

import dev.cedo.lust.utils.Utils;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;

import java.awt.*;

import static org.lwjgl.opengl.GL11.*;

/**
 * @author cedo
 * @since 05/17/2022
 */
public class RenderUtil implements Utils {


    public static Framebuffer createFrameBuffer(Framebuffer framebuffer) {
        return createFrameBuffer(framebuffer, false);
    }

    public static Framebuffer createFrameBuffer(Framebuffer framebuffer, boolean depth) {
        if (needsNewFramebuffer(framebuffer)) {
            if (framebuffer != null) {
                framebuffer.deleteFramebuffer();
            }
            return new Framebuffer(mc.displayWidth, mc.displayHeight, depth);
        }
        return framebuffer;
    }

    public static boolean needsNewFramebuffer(Framebuffer framebuffer) {
        return framebuffer == null || framebuffer.framebufferWidth != mc.displayWidth || framebuffer.framebufferHeight != mc.displayHeight;
    }

    // animation for sliders and stuff
    public static double animate(double endPoint, double current, double speed) {
        boolean shouldContinueAnimation = endPoint > current;
        if (speed < 0.0D) {
            speed = 0.0D;
        } else if (speed > 1.0D) {
            speed = 1.0D;
        }

        double dif = Math.max(endPoint, current) - Math.min(endPoint, current);
        double factor = dif * speed;
        return current + (shouldContinueAnimation ? factor : -factor);
    }

    // Draws a circle using traditional methods of rendering
    public static void drawCircle(double x, double y, double radius, int color) {
        radius /= 2;
        glDisable(GL_TEXTURE_2D);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        color(color);
        glBegin(GL_TRIANGLE_FAN);

        for (double i = 0; i <= 180; i++) {
            double angle = (i * 2) * (Math.PI / 180);
            glVertex2d(x + (radius * Math.sin(angle)) + radius, y + (radius * Math.cos(angle)) + radius);
        }

        glEnd();
        glEnable(GL_TEXTURE_2D);
    }

    public static void drawRoundedRect(double x, double y, double width, double height, double cornerRadius, int color) {
        drawRoundedRect((float) x, (float) y, (float) width, (float) height, (float) cornerRadius,new Color(color));
    }

    public static void drawRoundedRect(float x, float y, float width, float height, float cornerRadius, Color color) {
        final int slices = 10;

        float alpha = color.getAlpha();

        if(alpha < 255) {
            Gui.drawRect2(x + cornerRadius, y, width - 2 * cornerRadius, height, color.getRGB());
            Gui.drawRect2(x, y + cornerRadius, cornerRadius, height - 2 * cornerRadius, color.getRGB());
            Gui.drawRect2(x + width - cornerRadius, y + cornerRadius, cornerRadius, height - 2 * cornerRadius, color.getRGB());
        }else {
            Gui.drawRect2(x + cornerRadius, y, width - 2 * cornerRadius, height, color.getRGB());
            Gui.drawRect2(x, y + cornerRadius, width, height - 2 * cornerRadius, color.getRGB());
        }

        drawCirclePart(x + cornerRadius, y + cornerRadius, -MathHelper.PI, -MathHelper.PId2, cornerRadius, slices, color.getRGB());
        drawCirclePart(x + cornerRadius, y + height - cornerRadius, -MathHelper.PId2, 0.0F, cornerRadius, slices, color.getRGB());

        drawCirclePart(x + width - cornerRadius, y + cornerRadius, MathHelper.PId2, MathHelper.PI, cornerRadius, slices, color.getRGB());
        drawCirclePart(x + width - cornerRadius, y + height - cornerRadius, 0, MathHelper.PId2, cornerRadius, slices, color.getRGB());

    }

    public static void drawCirclePart(double x, double y, float fromAngle, float toAngle, float radius, int slices, int color) {
        GlStateManager.enableBlend();
        GlStateManager.color(1,1,1,1);
        color(color);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glBegin(GL11.GL_TRIANGLE_FAN);
        GL11.glVertex2d(x, y);
        final float increment = (toAngle - fromAngle) / slices;

        for (int i = 0; i <= slices; i++) {
            final float angle = fromAngle + i * increment;

            final float dX = MathHelper.sin(angle);
            final float dY = MathHelper.cos(angle);

            GL11.glVertex2d(x + dX * radius, y + dY * radius);
        }
        GL11.glEnd();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
    }


    public static void scaleStart(float x, float y, float scale) {
        GL11.glPushMatrix();
        GL11.glTranslatef(x, y, 0);
        GL11.glScalef(scale, scale, 1);
        GL11.glTranslatef(-x, -y, 0);
    }

    public static void scaleEnd() {
        GL11.glPopMatrix();
    }


    public static void scissor(double x, double y, double width, double height, Runnable data) {
        scissorStart(x, y, width, height);
        data.run();
        scissorEnd();
    }

    public static void scissorStart(double x, double y, double width, double height) {
        glEnable(GL_SCISSOR_TEST);
        ScaledResolution sr = new ScaledResolution(mc);
        final double scale = sr.getScaleFactor();
        double finalHeight = height * scale;
        double finalY = (sr.getScaledHeight() - y) * scale;
        double finalX = x * scale;
        double finalWidth = width * scale;
        glScissor((int) finalX, (int) (finalY - finalHeight), (int) finalWidth, (int) finalHeight);
    }

    public static void scissorEnd() {
        glDisable(GL_SCISSOR_TEST);
    }

    // This will set the alpha limit to a specified value ranging from 0-1
    public static void setAlphaLimit(float limit) {
        GlStateManager.enableAlpha();
        GlStateManager.alphaFunc(GL_GREATER, (float) (limit * .01));
    }

    // This method colors the next avalible texture with a specified alpha value ranging from 0-1
    public static void color(int color, float alpha) {
        float r = (float) (color >> 16 & 255) / 255.0F;
        float g = (float) (color >> 8 & 255) / 255.0F;
        float b = (float) (color & 255) / 255.0F;
        GlStateManager.color(r, g, b, alpha);
    }

    // Colors the next texture without a specified alpha value
    public static void color(int color) {
        color(color, (float) (color >> 24 & 255) / 255.0F);
    }

    /**
     * Bind a texture using the specified integer refrence to the texture.
     *
     * @see org.lwjgl.opengl.GL13 for more information about texture bindings
     */
    public static void bindTexture(int texture) {
        glBindTexture(GL_TEXTURE_2D, texture);
    }

    // Sometimes colors get messed up in for loops, so we use this method to reset it to allow new colors to be used
    public static void resetColor() {
        GlStateManager.color(1, 1, 1, 1);
    }

    public static boolean isHovered(float mouseX, float mouseY, float x, float y, float width, float height) {
        return mouseX >= x && mouseY >= y && mouseX < x + width && mouseY < y + height;
    }

    public static void drawPlayerFace(EntityPlayer player, float x, float y, int width, int height) {
        if (mc.getNetHandler() != null && player.getUniqueID() != null) {
            NetworkPlayerInfo i = mc.getNetHandler().getPlayerInfo(player.getUniqueID());
            if (i != null) {
                GlStateManager.pushMatrix();
                mc.getTextureManager().bindTexture(i.getLocationSkin());
                GL11.glEnable(GL11.GL_BLEND);
                GlStateManager.color(1, 1, 1);
                Gui.drawModalRectWithCustomSizedTexture(x, y, (float) (40), (float) (40), width, height, (float) width * 8, (float) height * 8);
                GlStateManager.popMatrix();
            }
        }
    }
}

package dev.cedo.lust.module.impl.render;

import dev.cedo.lust.Lust;
import dev.cedo.lust.event.impl.render.Render2DEvent;
import dev.cedo.lust.event.impl.render.ShaderEvent;
import dev.cedo.lust.module.Category;
import dev.cedo.lust.module.Module;
import dev.cedo.lust.module.settings.impl.ModeSetting;
import dev.cedo.lust.utils.font.FontUtil;
import dev.cedo.lust.utils.player.MovementUtils;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

/**
 * @author cedo
 * @since 05/17/2022
 */
public class HudMod extends Module {

    private final ModeSetting modeSetting = new ModeSetting("Watermark Type", "Text", "Logo", "Text");

    public HudMod() {
        super("Hud", Category.RENDER, "Displays the hud", 0);
        addSettings(modeSetting);
        toggleSilent();
    }


    private final ResourceLocation lustLogo = new ResourceLocation("Lust/lust-client.png");


    @Override
    public void onShaderEvent(ShaderEvent event) {
        if(modeSetting.getMode().equalsIgnoreCase("Text") && event.isBloom()) {
            FontUtil.lustFont40.drawString("lust", 6, 5, -1);
        }
    }

    @Override
    public void onRender2DEvent(Render2DEvent event) {
        switch (modeSetting.getMode()) {
            case "Logo":
                float logoWidth = 167 / 3f;
                float logoHeight = 234 / 3f;
                GL11.glEnable(GL11.GL_BLEND);
                GL11.glColor4d(1, 1, 1, 1);
                mc.getTextureManager().bindTexture(lustLogo);
                Gui.drawModalRectWithCustomSizedTexture(6, 6, 0, 0, logoWidth, logoHeight, logoWidth, logoHeight);
                break;
            case "Text":
                FontUtil.lustFont40.drawString("lust", 6, 5, -1);
                break;
        }
    }


}

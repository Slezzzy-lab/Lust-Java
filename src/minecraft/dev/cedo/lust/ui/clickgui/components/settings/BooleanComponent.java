package dev.cedo.lust.ui.clickgui.components.settings;

import dev.cedo.lust.Lust;
import dev.cedo.lust.module.settings.impl.BooleanSetting;
import dev.cedo.lust.ui.Screen;
import dev.cedo.lust.ui.clickgui.components.SettingComponent;
import dev.cedo.lust.utils.animations.Animation;
import dev.cedo.lust.utils.animations.Direction;
import dev.cedo.lust.utils.animations.impl.DecelerateAnimation;
import dev.cedo.lust.utils.font.FontUtil;
import dev.cedo.lust.utils.misc.HoveringUtil;
import dev.cedo.lust.utils.render.ColorUtil;
import dev.cedo.lust.utils.render.RenderUtil;
import net.minecraft.client.gui.Gui;

import java.awt.*;

/**
 * @author cedo
 * @since 05/19/2022
 */
public class BooleanComponent extends SettingComponent<BooleanSetting> {
    private final Animation toggleAnimation = new DecelerateAnimation(250, 1, Direction.BACKWARDS);

    public BooleanComponent(BooleanSetting booleanSetting) {
        super(booleanSetting);
    }


    @Override
    public void initGui() {

    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {

    }

    @Override
    public void drawScreen(int mouseX, int mouseY) {

        FontUtil.lustFont16.drawString(getSetting().getName(), x + 5, y + FontUtil.lustFont16.getMiddleOfBox(height), -1);

        toggleAnimation.setDirection(getSetting().isEnabled() ? Direction.FORWARDS : Direction.BACKWARDS);

        float toggleWidth = 17;
        float toggleHeight = 7;
        float toggleX = (x + width) - (toggleWidth + 3);
        float toggleY = y + height / 2f - toggleHeight / 2f;
        Color backgroundColor = settingRectColor.brighter().brighter();
        RenderUtil.drawRoundedRect(toggleX, toggleY, toggleWidth, toggleHeight, 3,
                ColorUtil.interpolateColorC(backgroundColor, Lust.INSTANCE.getClientColor(), (float) toggleAnimation.getOutput()));

        float radius = toggleHeight - 1;

        RenderUtil.drawCircle((toggleX + .5f) + ((toggleWidth - (1 + radius)) * toggleAnimation.getOutput()), toggleY + .5f, radius, -1);


    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) {
        if (isHoveringBox(mouseX, mouseY)) {
            getSetting().toggle();
        }

    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {

    }
}

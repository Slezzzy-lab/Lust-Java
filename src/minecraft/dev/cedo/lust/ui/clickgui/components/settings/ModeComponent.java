package dev.cedo.lust.ui.clickgui.components.settings;

import dev.cedo.lust.Lust;
import dev.cedo.lust.module.settings.impl.BooleanSetting;
import dev.cedo.lust.module.settings.impl.ModeSetting;
import dev.cedo.lust.ui.clickgui.components.SettingComponent;
import dev.cedo.lust.utils.animations.Animation;
import dev.cedo.lust.utils.animations.Direction;
import dev.cedo.lust.utils.animations.impl.DecelerateAnimation;
import dev.cedo.lust.utils.font.FontUtil;
import dev.cedo.lust.utils.misc.HoveringUtil;

/**
 * @author cedo
 * @since 05/19/2022
 */
public class ModeComponent extends SettingComponent<ModeSetting> {


    public ModeComponent(ModeSetting modeSetting) {
        super(modeSetting);
    }


    @Override
    public void initGui() {

    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {

    }

    @Override
    public void drawScreen(int mouseX, int mouseY) {
        FontUtil.lustFont16.drawString("§f" + getSetting().getName() + ":§r " + getSetting().getMode(),
                x + 5, y + FontUtil.lustFont16.getMiddleOfBox(height), Lust.INSTANCE.getClientColor());
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) {
        if (isHoveringBox(mouseX, mouseY)) {
            switch (button) {
                case 0:
                    getSetting().cycleForwards();
                    break;
                case 1:
                    getSetting().cycleBackwards();
                    break;
            }
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {

    }
}

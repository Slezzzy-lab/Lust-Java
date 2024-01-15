package dev.cedo.lust.ui.clickgui.components.settings;

import dev.cedo.lust.Lust;
import dev.cedo.lust.module.Module;
import dev.cedo.lust.module.impl.render.ClickGuiMod;
import dev.cedo.lust.module.settings.impl.BooleanSetting;
import dev.cedo.lust.module.settings.impl.KeybindSetting;
import dev.cedo.lust.ui.clickgui.components.SettingComponent;
import dev.cedo.lust.utils.animations.Animation;
import dev.cedo.lust.utils.animations.Direction;
import dev.cedo.lust.utils.animations.impl.DecelerateAnimation;
import dev.cedo.lust.utils.font.FontUtil;
import dev.cedo.lust.utils.render.ColorUtil;
import dev.cedo.lust.utils.render.RenderUtil;
import org.lwjgl.input.Keyboard;

/**
 * @author cedo
 * @since 05/19/2022
 */
public class KeybindComponent extends SettingComponent<KeybindSetting> {
    private boolean binding;

    private final Animation clickAnimation = new DecelerateAnimation(250, 1, Direction.BACKWARDS);

    public KeybindComponent(KeybindSetting keybindSetting) {
        super(keybindSetting);
    }


    @Override
    public void initGui() {

    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {
        if (binding) {
            if (keyCode == Keyboard.KEY_SPACE || keyCode == Keyboard.KEY_ESCAPE || keyCode == Keyboard.KEY_DELETE) {
                getSetting().setCode(Keyboard.KEY_NONE);
            } else {
                getSetting().setCode(keyCode);
            }

            ClickGuiMod.clickGui.binding = false;
            binding = false;
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY) {
        clickAnimation.setDirection(binding ? Direction.FORWARDS : Direction.BACKWARDS);

        String bind = Keyboard.getKeyName(getSetting().getCode());
        String text = "§fBind: §r" + bind;
        float textWidth = FontUtil.lustFont18.getStringWidth(text);
        float startX = x + width / 2f - textWidth / 2f;
        float startY = y + FontUtil.lustFont18.getMiddleOfBox(height);

        RenderUtil.drawRoundedRect(startX - 3, startY - 2, textWidth + 6, FontUtil.lustFont18.getHeight() + 4, 4,
                ColorUtil.interpolateColorC(settingRectColor.brighter(), settingRectColor.brighter().brighter(), (float) clickAnimation.getOutput()));

        FontUtil.lustFont18.drawCenteredString("§fBind: §r" + bind, x + width / 2f, y + FontUtil.lustFont18.getMiddleOfBox(height), Lust.INSTANCE.getClientAltColor());
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) {
        if (isHoveringBox(mouseX, mouseY) && button == 0) {
            binding = true;
            ClickGuiMod.clickGui.binding = true;
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {

    }
}

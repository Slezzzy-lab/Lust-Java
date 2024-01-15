package dev.cedo.lust.ui.clickgui.components.settings;

import dev.cedo.lust.Lust;
import dev.cedo.lust.module.settings.impl.KeybindSetting;
import dev.cedo.lust.module.settings.impl.NumberSetting;
import dev.cedo.lust.ui.clickgui.components.SettingComponent;
import dev.cedo.lust.utils.animations.Animation;
import dev.cedo.lust.utils.animations.Direction;
import dev.cedo.lust.utils.animations.impl.DecelerateAnimation;
import dev.cedo.lust.utils.font.FontUtil;
import dev.cedo.lust.utils.misc.MathUtils;
import dev.cedo.lust.utils.render.RenderUtil;

/**
 * @author cedo
 * @since 05/19/2022
 */
public class NumberComponent extends SettingComponent<NumberSetting> {

    private boolean dragging;

    public NumberComponent(NumberSetting numberSetting) {
        super(numberSetting);
    }


    @Override
    public void initGui() {

    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {

    }


    private float animationWidth = 0;

    @Override
    public void drawScreen(int mouseX, int mouseY) {
        NumberSetting numberSetting = getSetting();

        float totalSliderWidth = width - 10;
        float sliderX = x + 5;
        float sliderHeight = 4;
        float sliderY = y + ((height / 2f) - (sliderHeight / 2f)) + 2;

        sliderY = (float) MathUtils.roundToHalf(sliderY);

        FontUtil.lustFont16.drawString(numberSetting.getName(), sliderX, sliderY - (FontUtil.lustFont18.getHeight() + 3), -1);

        RenderUtil.drawRoundedRect(sliderX, sliderY, totalSliderWidth, sliderHeight, 2, settingRectColor.brighter());

        double currentValue = numberSetting.getValue();

        if (dragging) {
            float percent = Math.min(1, Math.max(0, (mouseX - sliderX) / totalSliderWidth));
            double newValue = MathUtils.interpolate(numberSetting.getMinValue(), numberSetting.getMaxValue(), percent);
            numberSetting.setValue(newValue);
        }

        float widthPercentage = (float) (((currentValue) - numberSetting.getMinValue()) / (numberSetting.getMaxValue() - numberSetting.getMinValue()));


        animationWidth = (float) RenderUtil.animate(totalSliderWidth * widthPercentage, animationWidth, .02);

        RenderUtil.drawRoundedRect(sliderX, sliderY, animationWidth, sliderHeight, 2, Lust.INSTANCE.getClientColor());


        String value = Double.toString(MathUtils.round(numberSetting.getValue(), 2));
        FontUtil.lustFont14.drawCenteredString(value, sliderX + animationWidth, sliderY + sliderHeight + 2, -1);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) {
        if (isHoveringBox(mouseX, mouseY) && button == 0) {
            dragging = true;
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {
        dragging = false;
    }
}

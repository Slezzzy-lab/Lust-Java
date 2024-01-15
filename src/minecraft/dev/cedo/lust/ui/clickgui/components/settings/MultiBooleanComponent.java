package dev.cedo.lust.ui.clickgui.components.settings;

import dev.cedo.lust.Lust;
import dev.cedo.lust.module.settings.impl.BooleanSetting;
import dev.cedo.lust.module.settings.impl.ModeSetting;
import dev.cedo.lust.module.settings.impl.MultipleBoolSetting;
import dev.cedo.lust.module.settings.impl.NumberSetting;
import dev.cedo.lust.ui.clickgui.components.SettingComponent;
import dev.cedo.lust.utils.animations.Animation;
import dev.cedo.lust.utils.animations.Direction;
import dev.cedo.lust.utils.animations.impl.DecelerateAnimation;
import dev.cedo.lust.utils.font.FontUtil;
import dev.cedo.lust.utils.misc.HoveringUtil;
import dev.cedo.lust.utils.render.ColorUtil;
import dev.cedo.lust.utils.render.RenderUtil;

import java.util.HashMap;

/**
 * @author cedo
 * @since 05/19/2022
 */
public class MultiBooleanComponent extends SettingComponent<MultipleBoolSetting> {
    public float realHeight, additionalCount = 0;


    private boolean opened;
    private final Animation openAnimation = new DecelerateAnimation(250, 1);
    private final Animation hoverAnimation = new DecelerateAnimation(250, 1);
    private final HashMap<BooleanSetting, Animation[]> booleanSettingHashMap = new HashMap<>();

    public MultiBooleanComponent(MultipleBoolSetting multipleBoolSetting) {
        super(multipleBoolSetting);

        for (BooleanSetting booleanSetting : multipleBoolSetting.getBoolSettings()) {
            booleanSettingHashMap.put(booleanSetting, new Animation[]{new DecelerateAnimation(250, 1), new DecelerateAnimation(250, 1)});
        }
    }


    @Override
    public void initGui() {

    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {

    }

    @Override
    public void drawScreen(int mouseX, int mouseY) {
        openAnimation.setDirection(opened ? Direction.FORWARDS : Direction.BACKWARDS);
        float rectWidth = width - 20;
        float rectX = x + 10;
        float rectHeight = 17;
        float rectY = y + realHeight / 2f - rectHeight / 2f;

        boolean hovering = HoveringUtil.isHovering(rectX, rectY, rectWidth, rectHeight, mouseX, mouseY);

        hoverAnimation.setDirection(hovering ? Direction.FORWARDS : Direction.BACKWARDS);

        RenderUtil.drawRoundedRect(rectX, rectY, rectWidth, rectHeight, 4,
                ColorUtil.interpolateColorC(settingRectColor.brighter(), settingRectColor.brighter().brighter(), (float) hoverAnimation.getOutput()));

        FontUtil.lustFont18.drawCenteredString(getSetting().getName(), rectX + rectWidth / 2f, rectY + FontUtil.lustFont18.getMiddleOfBox(rectHeight), -1);

        additionalCount = .5f;

        if (opened || !openAnimation.finished(Direction.BACKWARDS)) {
            float boolHeight = 14;
            float size = boolHeight * getSetting().getBoolSettings().size();
            float anim = (float) openAnimation.getOutput();
            RenderUtil.drawRoundedRect(rectX, rectY + (rectHeight + 5), rectWidth, size * anim, 4,
                    ColorUtil.applyOpacity(settingRectColor.brighter(), anim));
            int count = 0;


            for (BooleanSetting booleanSetting : getSetting().getBoolSettings()) {
                float textY = rectY + (rectHeight + 10) + (count * anim);
                float boolY = rectY + (rectHeight + 5) + (count * anim);
                RenderUtil.resetColor();
                Animation toggleAnimation = booleanSettingHashMap.get(booleanSetting)[0];
                Animation hoverAnimation = booleanSettingHashMap.get(booleanSetting)[1];

                toggleAnimation.setDirection(booleanSetting.isEnabled() ? Direction.FORWARDS : Direction.BACKWARDS);

                boolean hoveringBool = HoveringUtil.isHovering(rectX, boolY, rectWidth, boolHeight, mouseX, mouseY);
                hoverAnimation.setDirection(hoveringBool ? Direction.FORWARDS : Direction.BACKWARDS);

                RenderUtil.drawRoundedRect(rectX, boolY, rectWidth, boolHeight, 4, ColorUtil.applyOpacity(settingRectColor.brighter().brighter(), (float) hoverAnimation.getOutput()));


                FontUtil.lustFont14.drawString(booleanSetting.getName(), rectX + 5, textY, ColorUtil.applyOpacity(-1, anim));

                float textWidth = FontUtil.iconFont20.getStringWidth(FontUtil.CHECKMARK);
                FontUtil.iconFont20.drawString(FontUtil.CHECKMARK, rectX + rectWidth - (textWidth + 2), textY,
                        ColorUtil.applyOpacity(Lust.INSTANCE.getClientAltColor(), (float) toggleAnimation.getOutput()));


                count += boolHeight;
            }
            additionalCount += (size / realHeight) * openAnimation.getOutput();
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) {
        float rectWidth = width - 20;
        float rectX = x + 10;
        float rectHeight = 17;
        float rectY = y + realHeight / 2f - rectHeight / 2f;
        if (HoveringUtil.isHovering(rectX, rectY, rectWidth, rectHeight, mouseX, mouseY)) {
            if (button == 1) opened = !opened;
            return;
        }

        if (opened) {
            float boolHeight = 14;
            float anim = (float) openAnimation.getOutput();
            int count = 0;
            for (BooleanSetting booleanSetting : getSetting().getBoolSettings()) {
                float boolY = rectY + (rectHeight + 5) + (count * anim);
                boolean hoveringBool = HoveringUtil.isHovering(rectX, boolY, rectWidth, boolHeight, mouseX, mouseY);
                if (hoveringBool && button == 0) {
                    booleanSetting.toggle();
                }
                count += boolHeight;
            }
        }

    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {

    }
}

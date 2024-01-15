package dev.cedo.lust.ui.clickgui.components;

import dev.cedo.lust.module.settings.Setting;
import dev.cedo.lust.ui.Screen;
import dev.cedo.lust.utils.animations.Animation;
import dev.cedo.lust.utils.misc.HoveringUtil;
import lombok.Getter;
import net.minecraft.client.gui.Gui;

import java.awt.*;

/**
 * @author cedo
 * @since 05/19/2022
 */
public abstract class SettingComponent<T extends Setting> implements Screen {
    public float x, y, width, height;
    public Animation settingHeightScissor;
    public Color settingRectColor;

    @Getter
    private final T setting;

    public SettingComponent(T setting) {
        this.setting = setting;
    }



    public boolean isHoveringBox(int mouseX, int mouseY){
        return HoveringUtil.isHovering(x,y,width,height, mouseX, mouseY);
    }
}

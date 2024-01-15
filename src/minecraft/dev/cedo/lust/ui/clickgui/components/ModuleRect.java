package dev.cedo.lust.ui.clickgui.components;

import dev.cedo.lust.Lust;
import dev.cedo.lust.module.Module;
import dev.cedo.lust.module.impl.render.PostProcessing;
import dev.cedo.lust.module.settings.Setting;
import dev.cedo.lust.module.settings.impl.*;
import dev.cedo.lust.ui.Screen;
import dev.cedo.lust.ui.clickgui.components.settings.*;
import dev.cedo.lust.utils.animations.Animation;
import dev.cedo.lust.utils.animations.Direction;
import dev.cedo.lust.utils.animations.impl.DecelerateAnimation;
import dev.cedo.lust.utils.font.FontUtil;
import dev.cedo.lust.utils.misc.HoveringUtil;
import dev.cedo.lust.utils.render.ColorUtil;
import dev.cedo.lust.utils.render.RenderUtil;
import lombok.Getter;
import net.minecraft.client.gui.Gui;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author cedo
 * @since 05/19/2022
 */
public class ModuleRect implements Screen {
    public float x, y, width, height;

    private final Animation settingAnimation = new DecelerateAnimation(250, 1, Direction.BACKWARDS);
    private final Animation toggleAnimation = new DecelerateAnimation(300, 1, Direction.BACKWARDS);
    private final Animation hoverAnimation = new DecelerateAnimation(250, 1, Direction.BACKWARDS);
    private double settingSize;
    private final List<SettingComponent> settingComponents;


    @Getter
    private final Module module;

    public ModuleRect(Module module) {
        this.module = module;
        settingComponents = new ArrayList<>();
        for (Setting setting : module.getSettingsList()) {
            if (setting instanceof BooleanSetting) {
                settingComponents.add(new BooleanComponent((BooleanSetting) setting));
            }
            if (setting instanceof ModeSetting) {
                settingComponents.add(new ModeComponent((ModeSetting) setting));
            }
            if (setting instanceof KeybindSetting) {
                settingComponents.add(new KeybindComponent((KeybindSetting) setting));
            }
            if (setting instanceof NumberSetting) {
                settingComponents.add(new NumberComponent((NumberSetting) setting));
            }
            if (setting instanceof MultipleBoolSetting) {
                settingComponents.add(new MultiBooleanComponent((MultipleBoolSetting) setting));
            }
        }

    }


    @Override
    public void initGui() {
        settingAnimation.setDirection(Direction.BACKWARDS);
        toggleAnimation.setDirection(Direction.BACKWARDS);
        hoverAnimation.setDirection(Direction.BACKWARDS);
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {
        if (module.isExpanded()) {
            settingComponents.forEach(settingComponent -> settingComponent.keyTyped(typedChar, keyCode));
        }
    }

    double actualSettingCount;

    @Override
    public void drawScreen(int mouseX, int mouseY) {
        toggleAnimation.setDirection(module.isEnabled() ? Direction.FORWARDS : Direction.BACKWARDS);

        boolean hoveringModule = HoveringUtil.isHovering(x, y, width, height, mouseX, mouseY);
        hoverAnimation.setDirection(hoveringModule ? Direction.FORWARDS : Direction.BACKWARDS);

        Color moduleRectColor = new Color(42, 42, 42);

        Gui.drawRect2(x, y, width, height, ColorUtil.interpolateColorC(moduleRectColor, moduleRectColor.brighter(), (float) hoverAnimation.getOutput()).getRGB());

        Color toggleColor = ColorUtil.interpolateColorsBackAndForth(15, 1, Lust.INSTANCE.getClientColor(), Lust.INSTANCE.getClientAltColor());

        Gui.drawRect2(x, y, width, height, ColorUtil.applyOpacity(toggleColor, (float) toggleAnimation.getOutput()).getRGB());

        FontUtil.lustFont20.drawString(module.getName(), x + 5, y + FontUtil.lustFont20.getMiddleOfBox(height), -1);

        Color settingRectColor = new Color(32, 32, 32);

        settingAnimation.setDirection(module.isExpanded() ? Direction.FORWARDS : Direction.BACKWARDS);

        double settingHeight = (actualSettingCount) * settingAnimation.getOutput();
        actualSettingCount = 0;
        if (module.isExpanded() || !settingAnimation.isDone()) {
            Gui.drawRect2(x, y + height, width, (float) (settingHeight * height), settingRectColor.getRGB());

            for (SettingComponent settingComponent : settingComponents) {
                settingComponent.settingRectColor = settingRectColor;
                settingComponent.x = x;
                settingComponent.y = (float) (y + height + (actualSettingCount * height));
                settingComponent.width = width;
                if (settingComponent instanceof NumberComponent) {
                    settingComponent.height = height * 1.5f;
                } else if (settingComponent instanceof MultiBooleanComponent) {
                    MultiBooleanComponent multiBooleanComponent = (MultiBooleanComponent) settingComponent;
                    actualSettingCount += multiBooleanComponent.additionalCount;
                    multiBooleanComponent.realHeight = height;
                    settingComponent.height = height * (1 + multiBooleanComponent.additionalCount);
                } else {
                    settingComponent.height = height;
                }
                settingComponent.settingHeightScissor = settingAnimation;

                if (!settingAnimation.isDone()) {
                    RenderUtil.scissorStart(x, y + height, width, settingHeight * height);
                    settingComponent.drawScreen(mouseX, mouseY);
                    RenderUtil.scissorEnd();
                } else {
                    settingComponent.drawScreen(mouseX, mouseY);
                }

                if (settingComponent instanceof NumberComponent) {
                    actualSettingCount += 1.5f;
                } else actualSettingCount++;
            }
        }
        settingSize = settingHeight;

    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) {
        boolean hoveringModule = HoveringUtil.isHovering(x, y, width, height, mouseX, mouseY);
        if (hoveringModule) {
            switch (button) {
                case 0:
                    toggleAnimation.setDirection(!module.isEnabled() ? Direction.FORWARDS : Direction.BACKWARDS);
                    module.toggleSilent();
                    break;
                case 1:
                    module.setExpanded(!module.isExpanded());
                    break;
            }
        }
        if (module.isExpanded()) {
            settingComponents.forEach(settingComponent -> settingComponent.mouseClicked(mouseX, mouseY, button));
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {
        if (module.isExpanded()) {
            settingComponents.forEach(settingComponent -> settingComponent.mouseReleased(mouseX, mouseY, state));
        }
    }


    public double getSettingSize() {
        return settingSize;
    }

}

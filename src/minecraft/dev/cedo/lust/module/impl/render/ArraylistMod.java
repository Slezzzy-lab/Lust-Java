package dev.cedo.lust.module.impl.render;

import dev.cedo.lust.Lust;
import dev.cedo.lust.event.impl.render.Render2DEvent;
import dev.cedo.lust.event.impl.render.ShaderEvent;
import dev.cedo.lust.module.Category;
import dev.cedo.lust.module.Module;
import dev.cedo.lust.module.settings.impl.ModeSetting;
import dev.cedo.lust.module.settings.impl.NumberSetting;
import dev.cedo.lust.utils.animations.Animation;
import dev.cedo.lust.utils.animations.Direction;
import dev.cedo.lust.utils.font.FontUtil;
import dev.cedo.lust.utils.font.MinecraftFontRenderer;
import dev.cedo.lust.utils.render.ColorUtil;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.util.Comparator;
import java.util.List;

/**
 * @author cedo
 * @since 05/18/2022
 */
public class ArraylistMod extends Module {

    private final ModeSetting mode = new ModeSetting("Mode", "Lust", "Lust", "Jello");
    private final NumberSetting height = new NumberSetting("Height", 11, 14, 10, .5f);


    public ArraylistMod() {
        super("Arraylist", Category.RENDER, "Displays enabled modules", 0);
        addSettings(mode, height);
        toggleSilent();
    }

    public List<Module> modules;

    private final MinecraftFontRenderer font = FontUtil.lustFont20;

    public void getModulesAndSort() {
        if (modules == null) {
            modules = Lust.INSTANCE.getModuleCollection().getModuleList();
        }
        modules.sort(Comparator.comparingDouble(m -> {
            Module module = (Module) m;
            String name = module.getName();
            return font.getStringWidth(name);
        }).reversed());
    }

    @Override
    public void onShaderEvent(ShaderEvent event) {
        float xOffset = 10;
        float y = 10;
        ScaledResolution sr = new ScaledResolution(mc);
        for (Module module : modules) {
            final Animation moduleAnimation = module.animation;
            moduleAnimation.setDirection(module.isEnabled() ? Direction.FORWARDS : Direction.BACKWARDS);

            if (!module.isEnabled() && moduleAnimation.finished(Direction.BACKWARDS)) continue;

            String displayText = module.getName();
            float textWidth = font.getStringWidth(displayText);

            float heightVal = (float) (height.getValue() + 1);

            float x = (float) (sr.getScaledWidth() - (moduleAnimation.getOutput() * (textWidth + xOffset)));

            float offset = 5;
            switch(mode.getMode()) {
                case "Lust":
                    Gui.drawRect2(x - 2, y - 3, font.getStringWidth(displayText) + offset, heightVal, -1);
                    break;
                case "Jello":
                    if(event.isBloom())
                        font.drawString(displayText, x, (y - 3) + font.getMiddleOfBox(heightVal), -1);
                    break;
            }

            y += moduleAnimation.getOutput() * heightVal;
        }
    }

    @Override
    public void onRender2DEvent(Render2DEvent event) {
        getModulesAndSort();
        float xOffset = 10;
        float y = 10;
        ScaledResolution sr = new ScaledResolution(mc);
        int count = 0;
        for (Module module : modules) {
            final Animation moduleAnimation = module.animation;

            moduleAnimation.setDirection(module.isEnabled() ? Direction.FORWARDS : Direction.BACKWARDS);

            if (!module.isEnabled() && moduleAnimation.finished(Direction.BACKWARDS)) continue;

            String displayText = module.getName();
            float textWidth = font.getStringWidth(displayText);

            float heightVal = (float) (height.getValue() + 1);

            float x = (float) (sr.getScaledWidth() - (moduleAnimation.getOutput() * (textWidth + xOffset)));

            Color textColor = ColorUtil.interpolateColorsBackAndForth(15, count * 20, Lust.INSTANCE.getClientColor(), Lust.INSTANCE.getClientAltColor());


            float alphaAnimation = (float) moduleAnimation.getOutput();

            float offset = 5;
            switch(mode.getMode()) {
                case "Lust":
                    Gui.drawRect2(x - 2, y - 3, font.getStringWidth(displayText) + offset, heightVal, ColorUtil.applyOpacity(Color.BLACK, .5f * alphaAnimation).getRGB());
                    font.drawString(displayText, x, (y - 3) + font.getMiddleOfBox(heightVal), ColorUtil.applyOpacity(textColor, alphaAnimation).getRGB());
                    break;
                case "Jello":
                    font.drawString(displayText, x, (y - 3) + font.getMiddleOfBox(heightVal), -1);
                    break;
            }
            y += moduleAnimation.getOutput() * heightVal;
            count++;
        }


    }
}

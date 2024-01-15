package dev.cedo.lust.module.impl.render;

import dev.cedo.lust.Lust;
import dev.cedo.lust.event.impl.render.NametagRenderEvent;
import dev.cedo.lust.event.impl.render.Render2DEvent;
import dev.cedo.lust.event.impl.render.Render3DEvent;
import dev.cedo.lust.module.Category;
import dev.cedo.lust.module.Module;
import dev.cedo.lust.module.settings.impl.BooleanSetting;
import dev.cedo.lust.module.settings.impl.ModeSetting;
import dev.cedo.lust.module.settings.impl.MultipleBoolSetting;
import dev.cedo.lust.module.settings.impl.NumberSetting;
import dev.cedo.lust.utils.font.FontUtil;
import dev.cedo.lust.utils.font.MinecraftFontRenderer;
import dev.cedo.lust.utils.misc.MathUtils;
import dev.cedo.lust.utils.player.EntityUtils;
import dev.cedo.lust.utils.render.ColorUtil;
import dev.cedo.lust.utils.render.ESPUtil;
import dev.cedo.lust.utils.render.RenderUtil;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.StringUtils;
import org.lwjgl.util.vector.Vector4f;

import java.awt.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL11.*;

/**
 * @author cedo
 * @since 05/18/2022
 */
public class ESP2D extends Module {

    private final MultipleBoolSetting targets = new MultipleBoolSetting("Targets",
            new BooleanSetting("Players", true),
            new BooleanSetting("Animals", false),
            new BooleanSetting("Mobs", false),
            new BooleanSetting("Invisibles",false));
    private final BooleanSetting boxEsp = new BooleanSetting("Box", true);

    private final BooleanSetting itemHeld = new BooleanSetting("Item Held Text", true);
    private final BooleanSetting equipmentVisual = new BooleanSetting("Equipment", true);
    private final BooleanSetting healthBar = new BooleanSetting("Health Bar", true);
    private final ModeSetting healthBarMode = new ModeSetting("Health Bar Mode", "Health", "Health", "Color");
    private final BooleanSetting healthBarText = new BooleanSetting("Health Bar Text", true);


    private final BooleanSetting nametags = new BooleanSetting("Nametags", true);
    private final BooleanSetting redTags = new BooleanSetting("Red Tags", true);
    private final BooleanSetting formattedTags = new BooleanSetting("Formatted Tags", true);
    private final NumberSetting scale = new NumberSetting("Tag Scale", .80, 1, .35, .05);

    private final BooleanSetting redBackground = new BooleanSetting("Red Background", false);

    public ESP2D() {
        super("2D ESP", Category.RENDER, "Draws a box in 2D space around entitys");

        addSettings(targets, boxEsp, itemHeld, healthBar, healthBarMode, healthBarText, equipmentVisual, nametags, scale, redTags, formattedTags, redBackground);
    }


    private final Map<Entity, Vector4f> entityPosition = new HashMap<>();

    @Override
    public void onNametagRenderEvent(NametagRenderEvent event) {
        if (nametags.isEnabled()) event.cancel();
    }


    @Override
    public void onRender3DEvent(Render3DEvent event) {
        entityPosition.clear();
        for (final Entity entity : EntityUtils.getEntities(targets.getSetting("Players").isEnabled(),
                targets.getSetting("Mobs").isEnabled(),targets.getSetting("Animals").isEnabled(),targets.getSetting("Animals").isEnabled(),targets.getSetting("Invisibles").isEnabled())) {
            if (shouldRender(entity) && ESPUtil.isInView(entity)) {
                entityPosition.put(entity, ESPUtil.getEntityPositionsOn2D(entity));
            }
        }
    }


    private final NumberFormat df = new DecimalFormat("0.#");
    private final Color backgroundColor = new Color(10, 10, 10, 130);

    @Override
    public void onRender2DEvent(Render2DEvent event) {
        Color mainColor = Lust.INSTANCE.getClientColor();
        Color colorAlt = Lust.INSTANCE.getClientAltColor();
        Color firstColor = ColorUtil.interpolateColorsBackAndForth(15, 0, mainColor, colorAlt);
        Color secondColor = ColorUtil.interpolateColorsBackAndForth(15, 90, mainColor, colorAlt);
        Color thirdColor = ColorUtil.interpolateColorsBackAndForth(15, 180, mainColor, colorAlt);
        Color fourthColor = ColorUtil.interpolateColorsBackAndForth(15, 270, mainColor, colorAlt);

        for (Entity entity : entityPosition.keySet()) {
            Vector4f pos = entityPosition.get(entity);
            float x = pos.getX(),
                    y = pos.getY(),
                    right = pos.getZ(),
                    bottom = pos.getW();

            if (entity instanceof EntityLivingBase) {
                MinecraftFontRenderer font = FontUtil.lustFont20;
                EntityLivingBase renderingEntity = (EntityLivingBase) entity;
                if (nametags.isEnabled()) {
                    float healthValue = renderingEntity.getHealth() / renderingEntity.getMaxHealth();
                    Color healthColor = healthValue > .75 ? new Color(66, 246, 123) : healthValue > .5 ? new Color(228, 255, 105) : healthValue > .35 ? new Color(236, 100, 64) : new Color(255, 65, 68);
                    String name = formattedTags.isEnabled() ? renderingEntity.getDisplayName().getFormattedText() : StringUtils.stripControlCodes(renderingEntity.getDisplayName().getUnformattedText());
                    name = (redTags.isEnabled() ? "§c" : "§f") + name;
                    double fontScale = scale.getValue();
                    float middle = x + ((right - x) / 2);
                    float textWidth = 0;
                    double fontHeight;
                    textWidth = font.getStringWidth(name);
                    middle -= (textWidth * fontScale) / 2f;
                    fontHeight = font.getHeight() * fontScale;

                    glPushMatrix();
                    glTranslated(middle, y - (fontHeight + 2), 0);
                    glScaled(fontScale, fontScale, 1);
                    glTranslated(-middle, -(y - (fontHeight + 2)), 0);

                    Color backgroundTagColor = redBackground.isEnabled() ? ColorUtil.applyOpacity(Color.RED, .65f) : backgroundColor;
                    Gui.drawRect2(middle - 3, (float) (y - (fontHeight + 7)), textWidth + 6,
                            (float) ((fontHeight / fontScale) + 4), backgroundTagColor.getRGB());


                    RenderUtil.resetColor();
                    font.drawSmoothString(name, middle, (float) (y - (fontHeight + 5)), healthColor.getRGB());
                    glPopMatrix();
                }

                if (itemHeld.isEnabled()) {
                    if (renderingEntity.getHeldItem() != null) {

                        float fontScale = .5f;
                        float middle = x + ((right - x) / 2);
                        float textWidth;
                        String text = renderingEntity.getHeldItem().getDisplayName();
                        textWidth = font.getStringWidth(text);
                        middle -= (textWidth * fontScale) / 2f;

                        glPushMatrix();
                        glTranslated(middle, (bottom + 4), 0);
                        glScaled(fontScale, fontScale, 1);
                        glTranslated(-middle, -(bottom + 4), 0);
                        GlStateManager.bindTexture(0);
                        RenderUtil.resetColor();
                        Gui.drawRect2(middle - 3, bottom + 1, font.getStringWidth(text) + 6, font.getHeight() + 5, backgroundColor.getRGB());
                        font.drawSmoothStringWithShadow(text.toString(), middle, bottom + 4, -1);
                        glPopMatrix();
                    }
                }

                if (equipmentVisual.isEnabled()) {
                    float scale = .4f;
                    float equipmentX = right + 5;
                    float equipmentY = y - 1;
                    glPushMatrix();
                    glTranslated(equipmentX, equipmentY, 0);
                    glScaled(scale, scale, 1);
                    glTranslated(-equipmentX, -y, 0);
                    RenderUtil.resetColor();
                    RenderHelper.enableGUIStandardItemLighting();
                    float seperation = 0f;
                    float length = (bottom - y) - 2;
                    for (int i = 3; i >= 0; i--) {
                        if (renderingEntity.getCurrentArmor(i) == null) {
                            seperation += (length / 3) / scale;
                            continue;
                        }
                        mc.getRenderItem().renderItemAndEffectIntoGUI(renderingEntity.getCurrentArmor(i), (int) equipmentX, (int) (equipmentY + seperation));
                        seperation += (length / 3) / scale;
                    }

                    RenderHelper.disableStandardItemLighting();
                    glPopMatrix();
                }


                if (healthBar.isEnabled()) {
                    float healthValue = renderingEntity.getHealth() / renderingEntity.getMaxHealth();
                    Color healthColor = healthValue > .75 ? new Color(66, 246, 123) : healthValue > .5 ? new Color(228, 255, 105) : healthValue > .35 ? new Color(236, 100, 64) : new Color(255, 65, 68);

                    float height = (bottom - y) + 1;
                    Gui.drawRect2(x - 3.5f, y - .5f, 2, height + 1, new Color(0, 0, 0, 180).getRGB());
                    if (healthBarMode.is("Color")) {
                        Gui.drawGradientRect2(x - 3, y, 1, height, ColorUtil.applyOpacity(firstColor, .3f).getRGB(), ColorUtil.applyOpacity(fourthColor, .3f).getRGB());
                        Gui.drawGradientRect2(x - 3, y + (height - (height * healthValue)), 1, height * healthValue, firstColor.getRGB(), fourthColor.getRGB());
                    } else {
                        Gui.drawRect2(x - 3, y, 1, height, ColorUtil.applyOpacity(healthColor, .3f).getRGB());
                        Gui.drawRect2(x - 3, y + (height - (height * healthValue)), 1, height * healthValue, healthColor.getRGB());
                    }

                    if (healthBarText.isEnabled()) {
                        healthValue *= 100;
                        String health = String.valueOf(MathUtils.round(healthValue, 1)).substring(0, healthValue == 100 ? 3 : 2);
                        String text = health + "%";
                        double fontScale = .5;
                        float textX = x - ((font.getStringWidth(text) / 2f) + 2);
                        float fontHeight = (float) (font.getHeight() * fontScale);
                        float newHeight = height - fontHeight;
                        float textY = y + (newHeight - (newHeight * (healthValue / 100)));

                        glPushMatrix();
                        glTranslated(textX - 5, textY, 1);
                        glScaled(fontScale, fontScale, 1);
                        glTranslated(-(textX - 5), -textY, 1);
                        font.drawSmoothStringWithShadow(text, textX, textY, -1);
                        glPopMatrix();
                    }


                }


            }

            if (boxEsp.isEnabled()) {
                float outlineThickness = .5f;
                RenderUtil.resetColor();
                //top
                Gui.drawGradientRectSideways2(x, y, (right - x), 1, firstColor.getRGB(), secondColor.getRGB());
                //left
                Gui.drawGradientRect2(x, y, 1, bottom - y, firstColor.getRGB(), fourthColor.getRGB());
                //bottom
                Gui.drawGradientRectSideways2(x, bottom, right - x, 1, fourthColor.getRGB(), thirdColor.getRGB());
                //right
                Gui.drawGradientRect2(right, y, 1, (bottom - y) + 1, secondColor.getRGB(), thirdColor.getRGB());

                //Outline

                //top
                Gui.drawRect2(x - .5f, y - outlineThickness, (right - x) + 2, outlineThickness, Color.BLACK.getRGB());
                //Left
                Gui.drawRect2(x - outlineThickness, y, outlineThickness, (bottom - y) + 1, Color.BLACK.getRGB());
                //bottom
                Gui.drawRect2(x - .5f, (bottom + 1), (right - x) + 2, outlineThickness, Color.BLACK.getRGB());
                //Right
                Gui.drawRect2(right + 1, y, outlineThickness, (bottom - y) + 1, Color.BLACK.getRGB());


                //top
                Gui.drawRect2(x + 1, y + 1, (right - x) - 1, outlineThickness, Color.BLACK.getRGB());
                //Left
                Gui.drawRect2(x + 1, y + 1, outlineThickness, (bottom - y) - 1, Color.BLACK.getRGB());
                //bottom
                Gui.drawRect2(x + 1, (bottom - outlineThickness), (right - x) - 1, outlineThickness, Color.BLACK.getRGB());
                //Right
                Gui.drawRect2(right - outlineThickness, y + 1, outlineThickness, (bottom - y) - 1, Color.BLACK.getRGB());

            }

        }


        mc.getFramebuffer().bindFramebuffer(true);
    }

    ;

    private boolean shouldRender(Entity entity) {
        if (entity.isDead || entity.isInvisible()) {
            return false;
        }
        if (entity instanceof EntityPlayer) {
            if (entity == mc.thePlayer) {
                return mc.gameSettings.thirdPersonView != 0;
            }
            return !entity.getDisplayName().getUnformattedText().contains("[NPC");
        }
        if (entity instanceof EntityAnimal) {
            if (entity == mc.theWorld.loadedEntityList.stream().filter(EntityAnimal.class::isInstance)) {
                return mc.gameSettings.thirdPersonView != 0;
            }
            return !entity.getDisplayName().getUnformattedText().contains("[NPC");
        }
        if (entity instanceof EntityMob) {
            if (entity == mc.theWorld.loadedEntityList.stream().filter(EntityMob.class::isInstance)) {
                return mc.gameSettings.thirdPersonView != 0;
            }
            return !entity.getDisplayName().getUnformattedText().contains("[NPC");
        }
        return false;
    }

}

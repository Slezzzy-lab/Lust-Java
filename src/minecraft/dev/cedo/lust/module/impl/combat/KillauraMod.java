package dev.cedo.lust.module.impl.combat;

import dev.cedo.lust.Lust;
import dev.cedo.lust.event.impl.player.MotionEvent;
import dev.cedo.lust.event.impl.render.Render2DEvent;
import dev.cedo.lust.event.impl.render.ShaderEvent;
import dev.cedo.lust.module.Category;
import dev.cedo.lust.module.Module;
import dev.cedo.lust.module.settings.impl.BooleanSetting;
import dev.cedo.lust.module.settings.impl.ModeSetting;
import dev.cedo.lust.module.settings.impl.MultipleBoolSetting;
import dev.cedo.lust.module.settings.impl.NumberSetting;
import dev.cedo.lust.utils.font.FontUtil;
import dev.cedo.lust.utils.player.EntityUtils;
import dev.cedo.lust.utils.render.ColorUtil;
import dev.cedo.lust.utils.render.RenderUtil;
import dev.cedo.lust.utils.time.TimerUtil;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.lang.annotation.Target;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import static dev.cedo.lust.utils.network.PacketUtils.sendPacket;
import static org.lwjgl.opengl.GL11.glDisable;

/**
 * @author K1
 * @since 05/22/2022
 */
public class KillauraMod extends Module {

    public static EntityLivingBase target;
    private final NumberSetting cps = new NumberSetting("CPS", 9, 12, 5, .5);
    private final NumberSetting reach = new NumberSetting("Reach", 3, 6, 2, .1);
    private final MultipleBoolSetting targets = new MultipleBoolSetting("Targets",
            new BooleanSetting("Players", true),
            new BooleanSetting("Animals", false),
            new BooleanSetting("Mobs", false),
            new BooleanSetting("Villagers", true),
            new BooleanSetting("Invisibles", true));
    private final BooleanSetting keepSprint = new BooleanSetting("Keep Sprint", false);
    private final ModeSetting targetHud = new ModeSetting("TargetHUD","Lust","Lust", "Basic");

    public KillauraMod() {
        super("Killaura", Category.COMBAT, "Automatically attack entities", Keyboard.KEY_R);
        addSettings(cps, reach, targets, keepSprint, targetHud);
    }


    public TimerUtil timer = new TimerUtil();
    private final ResourceLocation lustLogo = new ResourceLocation("Lust/lust-client.png");

    @Override
    public void onRender2DEvent(Render2DEvent event) {
        EntityLivingBase target = KillauraMod.target;
        if(KillauraMod.target == null && mc.currentScreen instanceof GuiChat) {
            target = mc.thePlayer;
        }
        if(target == null)
            return;
        switch(targetHud.getMode()) {

            case "Lust":
                float width = 150, height = 50, x = event.getScaledResolution().getScaledWidth()/2f + 5, y = event.getScaledResolution().getScaledHeight()/2f + 5;
                Color bgColor = ColorUtil.interpolateColorsBackAndForth(15, (int) (y * 20), Lust.INSTANCE.getClientColor(), Lust.INSTANCE.getClientAltColor());
                if(width < FontUtil.lustFont32.getStringWidth(target.getName()) + 50) {

                }
                RenderUtil.drawRoundedRect(x, y, width, height, 5, ColorUtil.applyOpacity(bgColor,0.4f));
                Gui.drawRect(x + 50,y + height - 20,x + 50 + (width - 60), y + height - 10,0x80000000);
                Gui.drawRect(x + 50,y + height - 20,x + 50 + (mc.thePlayer.getHealth() / mc.thePlayer.getMaxHealth() * (width - 60)), y + height - 10,0xff87ff54);
                String percent = (Math.round((target.getHealth() * 100.0) / target.getMaxHealth()) * 100 / 100.0) + "%";
                FontUtil.lustFont20.drawCenteredString(percent, x + 50 + (width - 60) / 2f ,y + height + 5.0f - 25 + 1, -1);
                FontUtil.lustFont32.drawString(target.getName(),x + 50,y + 7,-1);
                if(target instanceof EntityPlayer)
                    RenderUtil.drawPlayerFace((EntityPlayer) target,x + 5,y + height/2f - 20,40,40);
                break;
            case "Basic":

                break;
        }

        super.onRender2DEvent(event);
    }

    @Override
    public void onShaderEvent(ShaderEvent event) {
        ScaledResolution sr = new ScaledResolution(mc);
        EntityLivingBase target = KillauraMod.target;
        if(KillauraMod.target == null && mc.currentScreen instanceof GuiChat) {
            target = mc.thePlayer;
        }
        if(target == null)
            return;
        switch(targetHud.getMode()) {
            case "Lust":
                float width = 150, height = 50, x = sr.getScaledWidth()/2f + 5, y = sr.getScaledHeight()/2f + 5;
                Color bgColor = ColorUtil.interpolateColorsBackAndForth(15, (int) (y * 20), Lust.INSTANCE.getClientColor(), Lust.INSTANCE.getClientAltColor());

                RenderUtil.drawRoundedRect(x, y, width, height, 5, ColorUtil.applyOpacity(bgColor,0.5f));
                break;
            case "Basic":

                break;
        }
        super.onShaderEvent(event);
    }

    @Override
    public void onMotionEvent(MotionEvent e) {
        if (e.isPre()) {
            ArrayList<EntityLivingBase> entities = EntityUtils.getEntities(targets.getSetting("Players").isEnabled(),
                    targets.getSetting("Mobs").isEnabled(),targets.getSetting("Animals").isEnabled(),
                    targets.getSetting("Villagers").isEnabled(),targets.getSetting("Invisibles").isEnabled());

            entities.sort(Comparator.comparingDouble(ent -> ent.getDistanceToEntity(mc.thePlayer)));

            if(target != null){
                float[] rots = ncpRotations(target);
                e.setYaw(rots[0]);
                e.setPitch(rots[1]);
            }

            target = null;
            if(entities.size() > 0 && entities.get(0) != null && entities.get(0).getDistanceToEntity(mc.thePlayer) < reach.getValue()) {
                target = entities.get(0);
                if(this.timer.hasTimeElapsed((long) (1000 / ThreadLocalRandom.current().nextDouble(cps.getValue() - 2, cps.getValue())))) {
                    mc.thePlayer.swingItem();
                    if(keepSprint.isEnabled())
                        mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(target, C02PacketUseEntity.Action.ATTACK));
                    else
                        mc.playerController.attackEntity(mc.thePlayer, target);
                    timer.reset();
                }
            }
        }
    }

    public static float[] ncpRotations(Entity e) {
        double x = e.posX + (e.posX - e.lastTickPosX) - mc.thePlayer.posX;
        double y = (e.posY + e.getEyeHeight()) - (mc.thePlayer.posY + mc.thePlayer.getEyeHeight()) - 0.1;
        double z = e.posZ + (e.posZ - e.lastTickPosZ) - mc.thePlayer.posZ;
        double dist = Math.sqrt(Math.pow(x, 2) + Math.pow(z, 2));

        float yaw = (float) Math.toDegrees(-Math.atan(x / z));
        float pitch = (float) -Math.toDegrees(Math.atan(y / dist));

        if (x < 0 && z < 0)
            yaw = 90 + (float) Math.toDegrees(Math.atan(z / x));
        else if (x > 0 && z < 0)
            yaw = -90 + (float) Math.toDegrees(Math.atan(z / x));

        // yaw += Math.random() * 5 - Math.random();
        // pitch += Math.random() * 5 - Math.random();

        if (pitch > 90)
            pitch = 90;
        if (pitch < -90)
            pitch = -90;
        if (yaw > 180)
            yaw = 180;
        if (yaw < -180)
            yaw = -180;

        return new float[]{yaw, pitch};
    }

}

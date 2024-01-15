package dev.cedo.lust.module.impl.movement;

import dev.cedo.lust.event.impl.network.PacketReceiveEvent;
import dev.cedo.lust.event.impl.player.CollideEvent;
import dev.cedo.lust.event.impl.player.MotionEvent;
import dev.cedo.lust.module.Category;
import dev.cedo.lust.module.Module;
import dev.cedo.lust.module.settings.impl.BooleanSetting;
import dev.cedo.lust.module.settings.impl.ModeSetting;
import dev.cedo.lust.module.settings.impl.NumberSetting;
import dev.cedo.lust.utils.network.PacketUtils;
import dev.cedo.lust.utils.player.MovementUtils;
import dev.cedo.lust.utils.time.TimerUtil;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;

/**
 * @author K1
 * @since 05/22/2022
 */
public class FlightMod extends Module {

    private final ModeSetting modeSetting = new ModeSetting("Mode", "Vanilla", "Vanilla", "Vanilla Damage", "AGC Damage", "AirWalk", "Verus", "NCP");
    private final NumberSetting speedSetting = new NumberSetting("Speed", 0.6, 5, 0.2, 0.01);
    private final NumberSetting verticalSpeedSetting = new NumberSetting("Vertical Speed", 0.4, 1, 0, 0.01);
    private final BooleanSetting stopMotion = new BooleanSetting("Stop Motion", true);

    public FlightMod() {
        super("Flight", Category.MOVEMENT, "Allows you to fly", Keyboard.KEY_F);
        addSettings(modeSetting, speedSetting, verticalSpeedSetting, stopMotion);
    }


    public TimerUtil timer = new TimerUtil();

    @Override
    public void onEnable() {
        switch (modeSetting.getMode()) {
            case "Vanilla Damage":
                for (int i = 0; i < 60; i++) {
                    mc.thePlayer.sendQueue.addToSendQueueSilent(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.06f, mc.thePlayer.posZ, false));
                    mc.thePlayer.sendQueue.addToSendQueueSilent(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, false));
                }
                mc.thePlayer.sendQueue.addToSendQueueSilent(new C03PacketPlayer(true));
                break;
            case "AGC Damage":
                mc.thePlayer.sendQueue.addToSendQueueSilent(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY - 3, mc.thePlayer.posZ, false));
                mc.thePlayer.sendQueue.addToSendQueueSilent(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, mc.thePlayer.onGround));
                break;
            case "Verus":

                MovementUtils.damageVerus();
                double startedY = mc.thePlayer.posY;
                break;
        }
        super.onEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();

        mc.timer.timerSpeed = 1;

        if (stopMotion.isEnabled()) {
            MovementUtils.setSpeed(0);
        }

    }

    @Override
    public void onMotionEvent(MotionEvent event) {

        switch (modeSetting.getMode()) {
            case "Vanilla Damage":
            case "AGC Damage":
            case "Verus":
                /*if (!mc.thePlayer.isSneaking() && ((CollideEvent) ev).getBlock() == Blocks.air && ((CollideEvent) ev).getY() <= startedY)
                    {
                        ((CollideEvent)ev).setBoundingBox(AxisAlignedBB.fromBounds(((CollideEvent) ev).getX(), ((CollideEvent) ev).getY(), ((CollideEvent) ev).getZ(), ((CollideEvent) ev).getX() + 1.0, startedY, ((CollideEvent) ev).getZ() + 1.0));
                    }*/
            case "Vanilla":
                MovementUtils.setSpeed(MovementUtils.isMovingStrafing() ? speedSetting.getValue() : 0);

                float motionY = 0;
                if (mc.gameSettings.keyBindSneak.pressed) motionY -= verticalSpeedSetting.getValue();
                if (mc.gameSettings.keyBindJump.pressed) motionY += verticalSpeedSetting.getValue();

                mc.thePlayer.motionY = motionY;
                break;
            case "AirWalk":
                mc.thePlayer.motionY = 0;
                break;
            case "NCP":
                mc.thePlayer.motionY = -.03f;
                mc.timer.timerSpeed = 1.7f;

                break;
        }



                super.onMotionEvent(event);
        }

        @Override
        public void onPacketReceiveEvent (PacketReceiveEvent event){
        }


    }


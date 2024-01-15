package dev.cedo.lust.module.impl.movement;

import dev.cedo.lust.event.impl.network.PacketReceiveEvent;
import dev.cedo.lust.event.impl.player.MotionEvent;
import dev.cedo.lust.module.Category;
import dev.cedo.lust.module.Module;
import dev.cedo.lust.module.settings.impl.BooleanSetting;
import dev.cedo.lust.module.settings.impl.ModeSetting;
import dev.cedo.lust.module.settings.impl.NumberSetting;
import dev.cedo.lust.utils.player.MovementUtils;
import dev.cedo.lust.utils.time.TimerUtil;
import org.lwjgl.input.Keyboard;

/**
 * @author K1
 * @since 05/22/2022
 */
public class SpeedMod extends Module {

    private final ModeSetting modeSetting = new ModeSetting("Mode", "Vanilla", "Vanilla", "Vanilla OnGround", "Vanilla Low", "Verus", "NCP");
    private final NumberSetting speedSetting = new NumberSetting("Speed",0.6, 1, 0.2, 0.01);
    private final BooleanSetting stopMotion = new BooleanSetting("Stop Motion", true);

    public SpeedMod() {
        super("Speed", Category.MOVEMENT, "Move Faster", Keyboard.KEY_V);
        addSettings(modeSetting, speedSetting, stopMotion);
    }


    public TimerUtil timer = new TimerUtil();

    @Override
    public void onEnable() {
        super.onEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();

        if(stopMotion.isEnabled()) {
            MovementUtils.setSpeed(0);
        }

    }

    @Override
    public void onMotionEvent(MotionEvent event) {

        switch(modeSetting.getMode()) {
            case "Vanilla":
                MovementUtils.setSpeed(MovementUtils.isMovingStrafing() ? speedSetting.getValue() : 0);
                if(mc.thePlayer.onGround && MovementUtils.isMovingStrafing())
                    mc.thePlayer.jump();
                break;
            case "Vanilla Low":
                MovementUtils.setSpeed(MovementUtils.isMovingStrafing() ? speedSetting.getValue() : 0);
                if(mc.thePlayer.onGround && MovementUtils.isMovingStrafing())
                    mc.thePlayer.jump(0.36);
                break;
            case "Vanilla OnGround":
                MovementUtils.setSpeed(MovementUtils.isMovingStrafing() ? speedSetting.getValue() : 0);
                break;
            case "Verus":
                break;
            case "NCP":
                break;
        }


        super.onMotionEvent(event);
    }

    @Override
    public void onPacketReceiveEvent(PacketReceiveEvent event) {}


}

package dev.cedo.lust.module.impl.player;

import dev.cedo.lust.event.impl.network.PacketReceiveEvent;
import dev.cedo.lust.event.impl.player.MotionEvent;
import dev.cedo.lust.module.Category;
import dev.cedo.lust.module.Module;
import dev.cedo.lust.module.settings.impl.BooleanSetting;
import dev.cedo.lust.module.settings.impl.NumberSetting;
import dev.cedo.lust.utils.player.MovementUtils;
import dev.cedo.lust.utils.time.TimerUtil;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;

/**
 * @author K1
 * @since 05/22/2022
 */
public class SprintMod extends Module {

    private final BooleanSetting omniSprint = new BooleanSetting("Omni-Sprint", false);

    public SprintMod() {
        super("Sprint", Category.PLAYER, "Automatically Sprints.", Keyboard.KEY_NONE);
        addSettings(omniSprint);
    }

    @Override
    public void onMotionEvent(MotionEvent event) {
        mc.thePlayer.setSprinting(omniSprint.isEnabled() ? MovementUtils.isMovingStrafing() : MovementUtils.isMovingForward() && mc.thePlayer.getFoodStats().getFoodLevel() > 6 && !mc.thePlayer.isSneaking() && !mc.thePlayer.isCollidedHorizontally);
        super.onMotionEvent(event);
    }

}

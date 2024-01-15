package dev.cedo.lust.module.impl.player;

import dev.cedo.lust.event.impl.network.PacketSendEvent;
import dev.cedo.lust.event.impl.player.MotionEvent;
import dev.cedo.lust.module.Category;
import dev.cedo.lust.module.Module;
import dev.cedo.lust.module.settings.impl.ModeSetting;
import net.minecraft.network.play.client.C03PacketPlayer;
import org.lwjgl.input.Keyboard;

/**
 * @author K1
 * @since 05/22/2022
 */
public class NoFallMod extends Module {

    private final ModeSetting modeSetting = new ModeSetting("Mode", "Edit", "Edit");

    public NoFallMod() {
        super("NoFall", Category.PLAYER, "Removes Fall Damage.", Keyboard.KEY_NONE);
        addSettings(modeSetting);
    }

    @Override
    public void onPacketSendEvent(PacketSendEvent event) {
        if(mc.thePlayer == null) return;
        if(mc.thePlayer.fallDistance >= 3 && event.getPacket() instanceof C03PacketPlayer){
            ((C03PacketPlayer) event.getPacket()).onGround = true;
            mc.thePlayer.fallDistance = 0;
        }
        super.onPacketSendEvent(event);
    }
}

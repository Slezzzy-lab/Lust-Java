package dev.cedo.lust.utils.network;

import dev.cedo.lust.utils.Utils;
import net.minecraft.network.Packet;

/**
 * @author cedo
 * @since 05/17/2022
 */
public class PacketUtils implements Utils {


    public static void sendPacket(Packet<?> packet, boolean silent) {
        if (mc.thePlayer != null) {
            mc.getNetHandler().getNetworkManager().sendPacket(packet, silent);
        }
    }

    public static void sendPacketNoEvent(Packet packet) {
        sendPacket(packet, true);
    }

    public static void sendPacket(Packet packet) {
        sendPacket(packet, false);
    }

}

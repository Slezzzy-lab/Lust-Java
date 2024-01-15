package dev.cedo.lust.event.impl.network;

import dev.cedo.lust.event.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.network.Packet;

@Getter
@Setter
@AllArgsConstructor
public class PacketSendEvent extends Event {

    private Packet<?> packet;

}

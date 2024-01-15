package dev.cedo.lust.event.impl.player;

import dev.cedo.lust.event.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author cedo
 * @since 05/17/2022
 */
@Getter
@Setter
@AllArgsConstructor
public class PlayerMoveUpdateEvent extends Event {
    private float strafe, forward, friction, yaw, pitch;
}

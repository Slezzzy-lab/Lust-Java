package dev.cedo.lust.event.impl.player;

import dev.cedo.lust.event.Event;
import dev.cedo.lust.utils.player.MovementUtils;
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
public class MoveEvent extends Event {
    private double x, y, z;
    public void setSpeed(double speed) {
        MovementUtils.setSpeed(this, speed);
    }
}

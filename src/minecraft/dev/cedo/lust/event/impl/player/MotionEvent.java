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
public class MotionEvent extends Event.StateEvent {

    private double x, y, z;
    private float yaw, pitch;
    private boolean onGround;

    public void setRotations(float yaw, float pitch) {
        this.yaw = yaw;
        this.pitch = pitch;
    }
}

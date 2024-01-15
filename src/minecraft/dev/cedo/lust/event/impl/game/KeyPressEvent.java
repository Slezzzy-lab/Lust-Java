package dev.cedo.lust.event.impl.game;

import dev.cedo.lust.event.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author cedo
 * @since 05/17/2022
 */
@Getter
@AllArgsConstructor
public class KeyPressEvent extends Event {
    private final int key;
}

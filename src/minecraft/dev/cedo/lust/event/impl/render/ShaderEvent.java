package dev.cedo.lust.event.impl.render;

import dev.cedo.lust.event.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author cedo
 * @since 05/18/2022
 */
@Getter
@AllArgsConstructor
public class ShaderEvent extends Event {
    private final boolean bloom;
}

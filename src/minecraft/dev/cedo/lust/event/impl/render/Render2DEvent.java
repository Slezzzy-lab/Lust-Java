package dev.cedo.lust.event.impl.render;

import dev.cedo.lust.event.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraft.client.gui.ScaledResolution;

/**
 * @author cedo
 * @since 05/17/2022
 */
@Getter
@AllArgsConstructor
public class Render2DEvent extends Event {
    private final ScaledResolution scaledResolution;
}

package dev.cedo.lust.module.impl.misc;

import dev.cedo.lust.event.impl.player.MotionEvent;
import dev.cedo.lust.module.Category;
import dev.cedo.lust.module.Module;
import dev.cedo.lust.module.settings.impl.BooleanSetting;
import dev.cedo.lust.module.settings.impl.ModeSetting;
import dev.cedo.lust.utils.player.MovementUtils;
import org.lwjgl.input.Keyboard;

/**
 * @author K1
 * @since 05/22/2022
 */
public class ClientSpooferMod extends Module {

    public static ModeSetting mode = new ModeSetting("Mode", "Lunar", "Lunar");

    public ClientSpooferMod() {
        super("ClientSpoofer", Category.MISC, "Spoof Client Brand.", Keyboard.KEY_NONE);
        addSettings(mode);
    }

}

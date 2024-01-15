package dev.cedo.lust.module.impl.combat;

import dev.cedo.lust.event.impl.player.MotionEvent;
import dev.cedo.lust.module.Category;
import dev.cedo.lust.module.Module;
import dev.cedo.lust.module.settings.impl.BooleanSetting;
import dev.cedo.lust.module.settings.impl.NumberSetting;
import dev.cedo.lust.utils.player.MovementUtils;
import org.lwjgl.input.Keyboard;

/**
 * @author K1
 * @since 05/24/2022
 */
public class ReachMod extends Module {

    public static NumberSetting reach = new NumberSetting("Reach", 4,6,3,0.01);

    public ReachMod() {
        super("Reach", Category.COMBAT, "Extends Reach.", Keyboard.KEY_NONE);
        addSettings(reach);
    }

}

package dev.cedo.lust.module.impl.misc;

import dev.cedo.lust.module.Category;
import dev.cedo.lust.module.Module;
import dev.cedo.lust.module.settings.impl.ModeSetting;
import org.lwjgl.input.Keyboard;

/**
 * @author Slezzzy
 * @since 6/5/2022
 */

public class BZFlipperMod extends Module {

    public static ModeSetting mode = new ModeSetting("Mode", "Low Value", "Low Value", "High Value");
    public static ModeSetting speed = new ModeSetting("Speed", "Fast", "Fast", "Slow");
    public static ModeSetting agressiveness = new ModeSetting("Agressiveness", "Medium", "Low", "Medium","High");

    public BZFlipperMod() {
        super("Bazaar Flipper", Category.MISC, "Automatically flips items on the bazaar in skyblock.", Keyboard.KEY_NONE);
        addSettings(mode, speed, agressiveness);
    }

    //API
    //https://api.hypixel.net/skyblock/bazaar
    

}

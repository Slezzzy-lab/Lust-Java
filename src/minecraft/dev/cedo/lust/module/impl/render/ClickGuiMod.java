package dev.cedo.lust.module.impl.render;

import dev.cedo.lust.module.Category;
import dev.cedo.lust.module.Module;
import dev.cedo.lust.ui.clickgui.ClickGui;
import org.lwjgl.input.Keyboard;

/**
 * @author cedo
 * @since 05/19/2022
 */
public class ClickGuiMod extends Module {

    public static ClickGui clickGui;

    public ClickGuiMod() {
        super("ClickGui", Category.RENDER, "Clickgui module", Keyboard.KEY_RSHIFT);
    }

    @Override
    public void toggle() {
        if(clickGui == null)
            clickGui = new ClickGui();
        super.toggleSilent();
    }

    @Override
    public void toggleSilent() {
        if(!isEnabled())
            super.toggleSilent();
    }

    @Override
    public void onEnable() {
        mc.displayGuiScreen(clickGui);
    }
}

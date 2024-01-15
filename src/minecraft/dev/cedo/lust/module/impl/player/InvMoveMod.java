package dev.cedo.lust.module.impl.player;

import dev.cedo.lust.event.impl.player.MotionEvent;
import dev.cedo.lust.event.impl.player.UpdateEvent;
import dev.cedo.lust.module.Category;
import dev.cedo.lust.module.Module;
import dev.cedo.lust.module.settings.impl.BooleanSetting;
import dev.cedo.lust.utils.player.MovementUtils;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.inventory.ContainerChest;
import org.lwjgl.input.Keyboard;

/**
 * @author K1
 * @since 05/23/2022
 */
public class InvMoveMod extends Module {
    public InvMoveMod() {
        super("InvMove", Category.PLAYER, "Automatically Sprints.", Keyboard.KEY_NONE);
    }

    @Override
    public void onUpdateEvent(UpdateEvent event) {
        if (mc.currentScreen != null && !(mc.currentScreen instanceof GuiChat)) {
            mc.gameSettings.keyBindForward.pressed = Keyboard.isKeyDown(mc.gameSettings.keyBindForward.getKeyCode());
            mc.gameSettings.keyBindSprint.pressed = Keyboard.isKeyDown(mc.gameSettings.keyBindSprint.getKeyCode());
            mc.gameSettings.keyBindLeft.pressed = Keyboard.isKeyDown(mc.gameSettings.keyBindLeft.getKeyCode());
            mc.gameSettings.keyBindRight.pressed = Keyboard.isKeyDown(mc.gameSettings.keyBindRight.getKeyCode());
            mc.gameSettings.keyBindBack.pressed = Keyboard.isKeyDown(mc.gameSettings.keyBindBack.getKeyCode());
            mc.gameSettings.keyBindJump.pressed = Keyboard.isKeyDown(mc.gameSettings.keyBindJump.getKeyCode());
        }
    }

}

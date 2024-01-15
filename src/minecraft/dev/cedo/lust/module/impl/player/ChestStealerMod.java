package dev.cedo.lust.module.impl.player;

import dev.cedo.lust.event.impl.player.MotionEvent;
import dev.cedo.lust.event.impl.player.UpdateEvent;
import dev.cedo.lust.module.Category;
import dev.cedo.lust.module.Module;
import dev.cedo.lust.module.settings.impl.BooleanSetting;
import dev.cedo.lust.module.settings.impl.NumberSetting;
import dev.cedo.lust.utils.player.MovementUtils;
import dev.cedo.lust.utils.time.TimerUtil;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGlassBottle;
import org.lwjgl.input.Keyboard;

/**
 * @author K1
 * @since 05/22/2022
 */
public class ChestStealerMod extends Module {

    private final TimerUtil timer = new TimerUtil();
    private final NumberSetting delay = new NumberSetting("Delay", 50, 150, 1, 1);
    private final BooleanSetting title = new BooleanSetting("Title Check", true);

    public ChestStealerMod() {
        super("ChestStealer", Category.PLAYER, "Automatically steals from chests.", Keyboard.KEY_GRAVE);
        addSettings(delay, title);
    }

    int count = 0;
    @Override
    public void onUpdateEvent(UpdateEvent event) {
        if (mc.thePlayer.openContainer != null && mc.thePlayer.openContainer instanceof ContainerChest) {

            ContainerChest chest = (ContainerChest) mc.thePlayer.openContainer;
            boolean titleCheck = !title.isEnabled() || (chest.getLowerChestInventory().getDisplayName().getUnformattedText().contains("Chest") || chest.getLowerChestInventory().getDisplayName().getUnformattedText().contains("Contai") || chest.getLowerChestInventory().getDisplayName().getUnformattedText().contains("Crate")) || chest.getLowerChestInventory().getDisplayName().getUnformattedText().equalsIgnoreCase("LOW");
            if (!titleCheck) return;
            int items = 0;
            for (int i = 0; i < chest.getLowerChestInventory().getSizeInventory(); i++) {
                if (chest.getLowerChestInventory().getStackInSlot(i) != null && !isBad(chest.getLowerChestInventory().getStackInSlot(i).getItem())) {
                    items++;
                }
            }
            for (int i = 0; i < chest.getLowerChestInventory().getSizeInventory(); i++) {
                if (chest.getLowerChestInventory().getStackInSlot(i) != null && !isBad(chest.getLowerChestInventory().getStackInSlot(i).getItem()) && timer.hasTimeElapsed(delay.getValue())) {
                    mc.playerController.windowClick(chest.windowId, i, 0, 1, mc.thePlayer);
                    count++;
                    timer.reset();
                }
            }
            if (items == 0) {
                mc.thePlayer.closeScreen();
                count = 0;
            }
        }
    }

    private boolean isBad(Item i) {
        return i.getUnlocalizedName().contains("stick") ||
                i.getUnlocalizedName().contains("string") ||
                i.getUnlocalizedName().contains("flint") ||
                i.getUnlocalizedName().contains("bucket") ||
                i.getUnlocalizedName().contains("feather") ||
                i.getUnlocalizedName().contains("snow") ||
                i.getUnlocalizedName().contains("piston") ||
                i instanceof ItemGlassBottle ||
                i.getUnlocalizedName().contains("web") ||
                i.getUnlocalizedName().contains("slime") ||
                i.getUnlocalizedName().contains("trip") ||
                i.getUnlocalizedName().contains("wire") ||
                i.getUnlocalizedName().contains("sugar") ||
                i.getUnlocalizedName().contains("note") ||
                i.getUnlocalizedName().contains("record") ||
                i.getUnlocalizedName().contains("flower") ||
                i.getUnlocalizedName().contains("wheat") ||
                i.getUnlocalizedName().contains("fishing") ||
                i.getUnlocalizedName().contains("boat") ||
                i.getUnlocalizedName().contains("leather") ||
                i.getUnlocalizedName().contains("seeds") ||
                i.getUnlocalizedName().contains("skull") ||
                i.getUnlocalizedName().contains("torch") ||
                i.getUnlocalizedName().contains("anvil") ||
                i.getUnlocalizedName().contains("enchant") ||
                i.getUnlocalizedName().contains("exp") ||
                i.getUnlocalizedName().contains("shears");
    }
}

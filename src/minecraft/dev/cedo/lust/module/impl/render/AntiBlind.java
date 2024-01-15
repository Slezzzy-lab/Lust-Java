package dev.cedo.lust.module.impl.render;

import dev.cedo.lust.event.impl.player.UpdateEvent;
import dev.cedo.lust.module.Category;
import dev.cedo.lust.module.Module;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import org.apache.commons.lang3.ArrayUtils;

public class AntiBlind extends Module {
    public AntiBlind() {
        super("AntiBlind", Category.RENDER, "Prevents you from being blinded");
    }

    @Override
    public void onUpdateEvent(UpdateEvent event) {
        for(PotionEffect pot : mc.thePlayer.getActivePotionEffects()) {
            if(ArrayUtils.contains(bannedIDS, pot.getPotionID())) {
                mc.thePlayer.removePotionEffect(pot.getPotionID());
            }
        }
        super.onUpdateEvent(event);
    }

    public int[] bannedIDS = {
            Potion.blindness.getId(),Potion.confusion.getId()
    };
}

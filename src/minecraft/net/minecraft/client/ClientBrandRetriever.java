package net.minecraft.client;

import dev.cedo.lust.Lust;
import dev.cedo.lust.module.impl.misc.ClientSpooferMod;
import dev.cedo.lust.module.impl.exploit.DisablerMod;
import dev.cedo.lust.utils.misc.StringUtils;

public class ClientBrandRetriever
{
    public static String getClientModName()
    {
        return Lust.INSTANCE.getModuleCollection().getModule(DisablerMod.class).isEnabled() && DisablerMod.mode.getMode().equalsIgnoreCase("AGC") ? "\\$$FIREDISABLER\247cXDDD"
        : Lust.INSTANCE.getModuleCollection().getModule(ClientSpooferMod.class).isEnabled() ? "lunarclient:" + StringUtils.random(7) : "vanilla";
    }
}

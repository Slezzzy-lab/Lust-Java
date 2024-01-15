package dev.cedo.lust.module.impl.combat;

/**
 * @author Slezzzy
 * @since 06/12/2022
 */

import dev.cedo.lust.event.impl.network.PacketSendEvent;
import dev.cedo.lust.module.Category;
import dev.cedo.lust.module.Module;
import dev.cedo.lust.module.settings.impl.ModeSetting;
import dev.cedo.lust.utils.network.PacketUtils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import org.lwjgl.input.Keyboard;

public class CriticalsMod extends Module {

    private final ModeSetting modeSetting = new ModeSetting("Mode", "Packet",  "Packet", "Packet2", "Legit", "Low");
    public CriticalsMod() {
        super("Criticals", Category.COMBAT, "Automatically critically strikes the opponet", Keyboard.KEY_NONE);
        addSettings(modeSetting);
    }

    @Override
    public void onPacketSendEvent(PacketSendEvent event) {
        if(event.getPacket() instanceof C02PacketUseEntity){
            switch (modeSetting.getMode()) {
                case "Packet":
                    packetCrit();
                    break;
                case "Packet2":
                    packetCrit2();
                    break;
                case "Legit":
                    Legit();
                    break;
                case "Low":
                    Low();
                    break;
            }
        }
    }

    public void packetCrit() {
        if (mc.thePlayer.onGround) {
            final double[] values = {0.0625, 0.001 - (Math.random() / 10000)};
            for (final double d : values)
                PacketUtils.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + d, mc.thePlayer.posZ, false));
        }
    }
    public void packetCrit2(){
        if(mc.thePlayer.onGround){
            PacketUtils.sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.0625, mc.thePlayer.posZ, false));
            PacketUtils.sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, false));
        }
    }

    public void Legit(){
        if(mc.thePlayer.onGround){
            mc.thePlayer.jump();
        }
    }

    private int offGroundTicks;

    public void Low(){
        if (mc.thePlayer.onGround)
            offGroundTicks = 0;
        else
            offGroundTicks++;

        if (offGroundTicks == 1)
            mc.thePlayer.motionY = -(0.01 - Math.random() / 500);

        if (mc.thePlayer.onGround)
            mc.thePlayer.motionY = 0.1 + Math.random() / 500;
    }

}


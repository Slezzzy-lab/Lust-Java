package dev.cedo.lust.module.impl.combat;

import dev.cedo.lust.event.impl.network.PacketReceiveEvent;
import dev.cedo.lust.event.impl.player.MotionEvent;
import dev.cedo.lust.module.Category;
import dev.cedo.lust.module.Module;
import dev.cedo.lust.module.settings.impl.BooleanSetting;
import dev.cedo.lust.module.settings.impl.MultipleBoolSetting;
import dev.cedo.lust.module.settings.impl.NumberSetting;
import dev.cedo.lust.utils.player.EntityUtils;
import dev.cedo.lust.utils.time.TimerUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * @author K1
 * @since 05/22/2022
 */
public class VelocityMod extends Module {

    private final NumberSetting xVelocity = new NumberSetting("Horizontal Velocity", 50, 100, 0, 1);
    private final NumberSetting yVelocity = new NumberSetting("Vertical Velocity", 50, 100, 0, 1);

    public VelocityMod() {
        super("Velocity", Category.COMBAT, "Reduces Player Velocity", Keyboard.KEY_NONE);
        addSettings(xVelocity,yVelocity);
    }


    public TimerUtil timer = new TimerUtil();
    private final ResourceLocation lustLogo = new ResourceLocation("Lust/lust-client.png");

    @Override
    public void onPacketReceiveEvent(PacketReceiveEvent event) {
        if(event.getPacket() instanceof S12PacketEntityVelocity) {
            S12PacketEntityVelocity packet = (S12PacketEntityVelocity) event.getPacket();

            if(packet.getEntityID() == mc.thePlayer.getEntityId()) {
                packet.motionX = (int) ((packet.motionX / 100) * xVelocity.getValue());
                packet.motionY = (int) ((packet.motionY / 100) * yVelocity.getValue());
                packet.motionZ = (int) ((packet.motionZ / 100) * xVelocity.getValue());
            }
        }
    }


}

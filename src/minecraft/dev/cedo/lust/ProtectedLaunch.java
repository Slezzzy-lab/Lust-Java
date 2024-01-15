package dev.cedo.lust;

import com.mojang.authlib.Agent;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
import dev.cedo.lust.module.BackgroundProcess;
import dev.cedo.lust.module.Module;
import dev.cedo.lust.module.ModuleCollection;
import dev.cedo.lust.module.impl.combat.CriticalsMod;
import dev.cedo.lust.module.impl.combat.KillauraMod;
import dev.cedo.lust.module.impl.combat.ReachMod;
import dev.cedo.lust.module.impl.combat.VelocityMod;
import dev.cedo.lust.module.impl.misc.BZFlipperMod;
import dev.cedo.lust.module.impl.misc.ClientSpooferMod;
import dev.cedo.lust.module.impl.exploit.DisablerMod;
import dev.cedo.lust.module.impl.exploit.FastBowMod;
import dev.cedo.lust.module.impl.movement.FlightMod;
import dev.cedo.lust.module.impl.movement.SpeedMod;
import dev.cedo.lust.module.impl.player.*;
import dev.cedo.lust.module.impl.render.*;
import dev.cedo.lust.utils.font.FontUtil;
import dev.cedo.lust.utils.font.MinecraftFontRenderer;
import dev.cedo.lust.utils.misc.MultiThreading;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Session;
import org.lwjgl.opengl.Display;

import java.awt.*;
import java.net.Proxy;
import java.util.HashMap;
import java.util.Map;

/**
 * @author cedo
 * @since 05/17/2022
 */

public class ProtectedLaunch {

    private static final HashMap<Class<? extends Module>, Module> modules = new HashMap<>();

    public static void start() {

        MultiThreading.runAsync(() -> {
            Map<String, Font> locationMap = new HashMap<>();

            // Greycliff font
            FontUtil.lustFont14_ = FontUtil.getFont(locationMap, "tenacity.ttf", 14);
            FontUtil.lustFont16_ = FontUtil.getFont(locationMap, "tenacity.ttf", 16);
            FontUtil.lustBoldFont16_ = FontUtil.getFont(locationMap, "tenacitybold.ttf", 16);
            FontUtil.lustFont18_ = FontUtil.getFont(locationMap, "tenacity.ttf", 18);
            FontUtil.lustFont20_ = FontUtil.getFont(locationMap, "tenacity.ttf", 20);
            FontUtil.lustFont22_ = FontUtil.getFont(locationMap, "tenacity.ttf", 22);
            FontUtil.lustFont28_ = FontUtil.getFont(locationMap, "tenacity.ttf", 28);
            FontUtil.lustFont32_ = FontUtil.getFont(locationMap, "tenacity.ttf", 32);
            FontUtil.lustBoldFont18_ = FontUtil.getFont(locationMap, "tenacitybold.ttf", 18);
            FontUtil.lustBoldFont20_ = FontUtil.getFont(locationMap, "tenacitybold.ttf", 20);
            FontUtil.lustBoldFont22_ = FontUtil.getFont(locationMap, "tenacitybold.ttf", 22);
            FontUtil.lustBoldFont26_ = FontUtil.getFont(locationMap, "tenacitybold.ttf", 26);
            FontUtil.lustFont24_ = FontUtil.getFont(locationMap, "tenacity.ttf", 24);
            FontUtil.lustBoldFont32_ = FontUtil.getFont(locationMap, "tenacitybold.ttf", 32);
            FontUtil.lustFont40_ = FontUtil.getFont(locationMap, "tenacity.ttf", 40);
            FontUtil.lustBoldFont40_ = FontUtil.getFont(locationMap, "tenacitybold.ttf", 40);

            // Icon font made using https://fontello.com
            FontUtil.iconFont16_ = FontUtil.getFont(locationMap, "icon.ttf", 16);
            FontUtil.iconFont20_ = FontUtil.getFont(locationMap, "icon.ttf", 20);
            FontUtil.iconFont26_ = FontUtil.getFont(locationMap, "icon.ttf", 26);
            FontUtil.iconFont35_ = FontUtil.getFont(locationMap, "icon.ttf", 35);
            FontUtil.iconFont40_ = FontUtil.getFont(locationMap, "icon.ttf", 40);


            FontUtil.completed++;
        });

        MultiThreading.runAsync(() -> FontUtil.completed++);
        MultiThreading.runAsync(() -> FontUtil.completed++);

        while (!FontUtil.hasLoaded()) {
            try {
                //noinspection BusyWait
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Initialize all the fonts
        FontUtil.iconFont16 = new MinecraftFontRenderer(FontUtil.iconFont16_);
        FontUtil.iconFont20 = new MinecraftFontRenderer(FontUtil.iconFont20_);
        FontUtil.iconFont26 = new MinecraftFontRenderer(FontUtil.iconFont26_);
        FontUtil.iconFont35 = new MinecraftFontRenderer(FontUtil.iconFont35_);
        FontUtil.iconFont40 = new MinecraftFontRenderer(FontUtil.iconFont40_);
        FontUtil.lustFont14 = new MinecraftFontRenderer(FontUtil.lustFont14_);
        FontUtil.lustFont16 = new MinecraftFontRenderer(FontUtil.lustFont16_);
        FontUtil.lustBoldFont16 = new MinecraftFontRenderer(FontUtil.lustBoldFont16_);
        FontUtil.lustFont18 = new MinecraftFontRenderer(FontUtil.lustFont18_);
        FontUtil.lustFont20 = new MinecraftFontRenderer(FontUtil.lustFont20_);
        FontUtil.lustFont22 = new MinecraftFontRenderer(FontUtil.lustFont22_);
        FontUtil.lustFont28 = new MinecraftFontRenderer(FontUtil.lustFont28_);
        FontUtil.lustFont32 = new MinecraftFontRenderer(FontUtil.lustFont32_);
        FontUtil.lustBoldFont26 = new MinecraftFontRenderer(FontUtil.lustBoldFont26_);
        FontUtil.lustBoldFont22 = new MinecraftFontRenderer(FontUtil.lustBoldFont22_);
        FontUtil.lustBoldFont18 = new MinecraftFontRenderer(FontUtil.lustBoldFont18_);
        FontUtil.lustBoldFont20 = new MinecraftFontRenderer(FontUtil.lustBoldFont20_);
        FontUtil.lustBoldFont40 = new MinecraftFontRenderer(FontUtil.lustBoldFont40_);
        FontUtil.lustFont40 = new MinecraftFontRenderer(FontUtil.lustFont40_);
        FontUtil.lustFont24 = new MinecraftFontRenderer(FontUtil.lustFont24_);
        FontUtil.lustBoldFont32 = new MinecraftFontRenderer(FontUtil.lustBoldFont32_);


        Display.setTitle(Lust.NAME + " " + Lust.INSTANCE.getVersion());

        //Modules
        modules.put(NotificationsMod.class, new NotificationsMod());
        modules.put(ClientSpooferMod.class, new ClientSpooferMod());
        modules.put(ChestStealerMod.class, new ChestStealerMod());
        modules.put(PostProcessing.class, new PostProcessing());
        modules.put(ArraylistMod.class, new ArraylistMod());
        modules.put(KillauraMod.class, new KillauraMod());
        modules.put(DisablerMod.class, new DisablerMod());
        modules.put(VelocityMod.class, new VelocityMod());
        modules.put(InvMoveMod.class, new InvMoveMod());
        modules.put(FastBowMod.class, new FastBowMod());
        modules.put(AntiBlind.class, new AntiBlind());
        modules.put(NoSlowMod.class, new NoSlowMod());
        modules.put(NoFallMod.class, new NoFallMod());
        modules.put(FlightMod.class, new FlightMod());
        modules.put(SprintMod.class, new SprintMod());
        modules.put(SpeedMod.class, new SpeedMod());
        modules.put(ReachMod.class, new ReachMod());
        modules.put(HudMod.class, new HudMod());
        modules.put(ESP2D.class, new ESP2D());
        modules.put(BZFlipperMod.class, new BZFlipperMod());
        modules.put(CriticalsMod.class, new CriticalsMod());

        modules.put(ClickGuiMod.class, new ClickGuiMod());

        Lust.INSTANCE.setModuleCollection(new ModuleCollection(modules));

        Lust.INSTANCE.getEventProtocol().register(new BackgroundProcess());


        boolean login = true;
        if (login) {
            final YggdrasilAuthenticationService service = new YggdrasilAuthenticationService(Proxy.NO_PROXY, "");
            final YggdrasilUserAuthentication auth = (YggdrasilUserAuthentication) service.createUserAuthentication(Agent.MINECRAFT);
            auth.setUsername("alessandrojoe4@gmail.com");
            auth.setPassword("Patriots8787");
            try {
                auth.logIn();
                Minecraft.getMinecraft().session = new Session(auth.getSelectedProfile().getName(), auth.getSelectedProfile().getId().toString(), auth.getAuthenticatedToken(), "mojang");
            } catch (AuthenticationException e) {
                System.err.println("Failed to login");
            }
        }
    }

}

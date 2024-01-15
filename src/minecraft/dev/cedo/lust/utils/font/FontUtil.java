package dev.cedo.lust.utils.font;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

import java.awt.*;
import java.io.InputStream;
import java.util.Map;

/**
 * @author cedo
 * @since 05/17/2022
 */
public class FontUtil {
    public static volatile int completed;

    //These are for the icon font for ease of access
    public final static String
            BUG = "a",
            LIST = "b",
            BOMB = "c",
            EYE = "d",
            PERSON = "e",
            WHEELCHAIR = "f",
            SCRIPT = "g",
            SKIP_LEFT = "h",
            PAUSE = "i",
            PLAY = "j",
            SKIP_RIGHT = "k",
            SHUFFLE = "l",
            INFO = "m",
            SETTINGS = "n",
            CHECKMARK = "o",
            XMARK = "p",
            TRASH = "q",
            WARNING = "r",
            FOLDER = "s",
            LOAD = "t",
            SAVE = "u";


    public static MinecraftFontRenderer
            lustFont14,
            lustFont16, lustBoldFont16,
            lustFont18, lustBoldFont18,
            lustFont20, lustBoldFont20,
            lustFont22, lustBoldFont22,
            lustFont24,
            lustBoldFont26,
            lustFont28,
            lustFont32, lustBoldFont32,
            lustFont40, lustBoldFont40,
            iconFont16, iconFont20, iconFont26, iconFont35, iconFont40;
    public static Font
            lustFont14_,
            lustFont16_, lustBoldFont16_,
            lustFont18_, lustBoldFont18_,
            lustFont20_, lustBoldFont20_,
            lustFont22_, lustBoldFont22_,
            lustFont24_,
            lustBoldFont26_,
            lustFont28_,
            lustFont32_, lustBoldFont32_,
            lustFont40_, lustBoldFont40_,
            iconFont16_, iconFont20_, iconFont26_, iconFont35_, iconFont40_;


    public static Font getFont(Map<String, Font> locationMap, String location, int size) {
        Font font;
        try {
            if (locationMap.containsKey(location)) {
                font = locationMap.get(location).deriveFont(Font.PLAIN, size);
            } else {
                InputStream is = Minecraft.getMinecraft().getResourceManager()
                        .getResource(new ResourceLocation("Lust/Fonts/" + location)).getInputStream();
                locationMap.put(location, font = Font.createFont(0, is));
                font = font.deriveFont(Font.PLAIN, size);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error loading font");
            font = new Font("default", Font.PLAIN, +10);
        }
        return font;
    }

    public static boolean hasLoaded() {
        return completed >= 3;
    }

}

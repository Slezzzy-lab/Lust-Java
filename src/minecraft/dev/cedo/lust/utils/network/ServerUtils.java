package dev.cedo.lust.utils.network;

import dev.cedo.lust.utils.Utils;

import java.util.Arrays;
import java.util.List;

public class ServerUtils implements Utils {

    public static String getServer() {
        if (mc.isSingleplayer()) {
            return "Singleplayer";
        }
        if (mc.getCurrentServerData() != null) {
            return mc.getCurrentServerData().serverIP;
        }
        return "Main Menu";
    }

    public static List<String> KILL_MESSAGES = Arrays.asList(
            "was killed by",
            "was slain by",
            "was thrown into the void by",
            "a asesinado"
    );

    public static List<String> DEATH_MESSAGES = Arrays.asList(
            "was too toxic to",
            "Got rejected by",
            "Didn't have enough Umf to succeed in their attack",
            "was ¿que paso? by",
            "lost a Hot Pepper contest to",
            "was cleaned by janitor",
            "got put in a meme by",
            "lost his homework because of",
            "restarted his career because of",
            "took bad advice from",
            "was sniped by a 50 cal from",
            "lost the floor was lava to",
            "was fake banned by",
            "stood too close to",
            "stood too far away from a player.... with a bow.",
            "stood a mile away from a player.... with a blaze",
            "stood all the way to the other edge of the map avoiding the other player... but failing to avoid",
            "Sneezed to hard due to pollen set by",
            "had allergies because of ",
            "was mauled by",
            "was sent to the lobby because of ",
            " Got OOF'd by"
            );

}

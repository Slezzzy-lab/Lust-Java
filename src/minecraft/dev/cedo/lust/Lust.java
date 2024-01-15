package dev.cedo.lust;

import dev.cedo.lust.event.EventProtocol;
import dev.cedo.lust.module.ModuleCollection;
import dev.cedo.lust.utils.Utils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.io.File;

/**
 * @author cedo
 * @since 05/17/2022
 */
@Getter
@Setter
public class Lust implements Utils {
    public static final Lust INSTANCE = new Lust();

    public static final String NAME = "Lust";
    public static final ReleaseType RELEASE = ReleaseType.DEV;
    public static final String VERSION = "1.0";
    public static final File DIRECTORY = new File(mc.mcDataDir, "Lust");


    private final EventProtocol eventProtocol = new EventProtocol();
    private ModuleCollection moduleCollection;

    public String getVersion() {
        return VERSION + (RELEASE != ReleaseType.PUBLIC ? " (" + RELEASE.getName() + ")" : "");
    }

    public Color getClientColor() {
        return new Color(255, 47, 91);
    }

    public Color getClientAltColor() {
        return new Color(252, 137, 164);
    }


    @Getter
    @AllArgsConstructor
    public enum ReleaseType {
        PUBLIC("Public"),
        BETA("Beta"),
        DEV("Developer");

        private final String name;
    }

}

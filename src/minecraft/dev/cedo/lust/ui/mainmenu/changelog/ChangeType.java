package dev.cedo.lust.ui.mainmenu.changelog;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.awt.*;

/**
 * @author cedo
 * @since 05/19/2022
 */
@Getter
@AllArgsConstructor
public enum ChangeType {
    NEW(new Color(17, 255, 85).getRGB()),
    UPDATE(new Color(0, 255, 255).getRGB()),
    FIX(new Color(255, 255, 69).getRGB()),
    DELETE(Color.RED.getRGB());

    private final int color;
}

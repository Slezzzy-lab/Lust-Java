package dev.cedo.lust.module;

import dev.cedo.lust.utils.font.FontUtil;
import dev.cedo.lust.utils.objects.Drag;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author cedo
 * @since 05/17/2022
 */
@Getter
public enum Category {
    COMBAT("Combat"),
    MOVEMENT("Movement"),
    RENDER("Render"),
    PLAYER("Player"),
    EXPLOIT("Exploit"),
    MISC("Misc");

    private final String name;
    public final int posX;
    public final boolean expanded;
    private final Drag drag;


    Category(String name){
        this.name = name;
        posX = 40 + (Module.categoryCount * 120);
        expanded = true;
        drag = new Drag(posX, 20);
        Module.categoryCount++;
    }

}

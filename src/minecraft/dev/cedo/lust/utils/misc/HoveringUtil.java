package dev.cedo.lust.utils.misc;

/**
 * @author cedo
 * @since 05/19/2022
 */
public class HoveringUtil {

    public static boolean isHovering(float x, float y, float width, float height, int mouseX, int mouseY) {
        return mouseX >= x && mouseY >= y && mouseX < x + width && mouseY < y + height;
    }

}
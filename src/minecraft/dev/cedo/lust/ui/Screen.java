package dev.cedo.lust.ui;

import dev.cedo.lust.utils.Utils;

/**
 * @author cedo
 * @since 05/19/2022
 */
public interface Screen extends Utils {

    void initGui();

    void keyTyped(char typedChar, int keyCode);

    void drawScreen(int mouseX, int mouseY);

    void mouseClicked(int mouseX, int mouseY, int button);

    void mouseReleased(int mouseX, int mouseY, int state);

}

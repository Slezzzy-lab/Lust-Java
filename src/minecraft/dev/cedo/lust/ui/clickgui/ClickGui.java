package dev.cedo.lust.ui.clickgui;

import dev.cedo.lust.Lust;
import dev.cedo.lust.module.Category;
import dev.cedo.lust.module.impl.render.ClickGuiMod;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.input.Keyboard;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author cedo
 * @since 05/19/2022
 */
public class ClickGui extends GuiScreen {

    private List<CategoryPanel> categoryPanels;
    public boolean binding;

    @Override
    public void initGui() {
        if (categoryPanels == null) {
            categoryPanels = new ArrayList<>();
            for (Category category : Category.values()) {
                categoryPanels.add(new CategoryPanel(category));
            }
        }

        categoryPanels.forEach(CategoryPanel::initGui);
    }


    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (keyCode == Keyboard.KEY_ESCAPE && !binding) {
            mc.displayGuiScreen(null);
            return;
        }

        categoryPanels.forEach(categoryPanel -> categoryPanel.keyTyped(typedChar, keyCode));
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }


    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        categoryPanels.forEach(categoryPanel -> categoryPanel.drawScreen(mouseX, mouseY));
    }

    @Override
    public void onGuiClosed() {
        Lust.INSTANCE.getModuleCollection().getModule(ClickGuiMod.class).setEnabled(false);
        super.onGuiClosed();
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        categoryPanels.forEach(categoryPanel -> categoryPanel.mouseClicked(mouseX, mouseY, mouseButton));
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        categoryPanels.forEach(categoryPanel -> categoryPanel.mouseReleased(mouseX, mouseY, state));
    }
}

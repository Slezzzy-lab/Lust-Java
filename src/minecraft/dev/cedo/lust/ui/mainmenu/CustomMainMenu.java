package dev.cedo.lust.ui.mainmenu;

import dev.cedo.lust.ui.altManager.GuiAltLogin;
import dev.cedo.lust.ui.mainmenu.changelog.Change;
import dev.cedo.lust.ui.mainmenu.changelog.Changelog;
import dev.cedo.lust.utils.font.FontUtil;
import dev.cedo.lust.utils.render.RenderUtil;
import net.minecraft.client.gui.*;
import net.minecraft.util.ResourceLocation;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author cedo
 * @since 05/19/2022
 */
public class CustomMainMenu extends GuiScreen {

    private static List<CustomButton> customButtons;

    @Override
    public void initGui() {
        if (customButtons == null) {
            customButtons = new ArrayList<>();
            customButtons.add(new CustomButton("single-player"));
            customButtons.add(new CustomButton("multi-player"));
            customButtons.add(new CustomButton("alt-manager"));
            customButtons.add(new CustomButton("language"));
            customButtons.add(new CustomButton("settings"));
            customButtons.add(new CustomButton("exit"));
        }

        Changelog.collectChangelog();
    }


    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {

        mc.getTextureManager().bindTexture(new ResourceLocation("Lust/background.jpg"));
        Gui.drawModalRectWithCustomSizedTexture(0, 0, 0, 0, this.width, this.height, this.width, this.height);

        FontUtil.lustBoldFont40.drawCenteredString("lust", width / 2f, FontUtil.lustBoldFont40.getMiddleOfBox(height) - 40, -1);

        int count = 0;
        int yOffset = 0;
        int xOffset = 0;
        float buttonWidth = 80;
        float buttonHeight = 40;
        float spacing = 5;
        float buttonX = width / 2f - ((buttonWidth * 2) + spacing) / 2f;
        float buttonY = (height / 2f - buttonHeight / 2f);
        for (CustomButton customButton : customButtons) {
            if (count == 2 || count == 4) {
                yOffset += spacing + buttonHeight;
                xOffset = 0;
            }
            customButton.x = buttonX + xOffset;
            customButton.y = buttonY + yOffset;
            customButton.width = buttonWidth;
            customButton.height = buttonHeight;
            customButton.clickAction = () -> {
                switch (customButton.getName()) {
                    case "single-player":
                        mc.displayGuiScreen(new GuiSelectWorld(this));
                        break;
                    case "multi-player":
                        mc.displayGuiScreen(new GuiMultiplayer(this));
                        break;
                    case "settings":
                        mc.displayGuiScreen(new GuiOptions(this, mc.gameSettings));
                        break;
                    case "alt-manager":
                        mc.displayGuiScreen(new GuiAltLogin(this));
                        break;
                    case "language":
                        mc.displayGuiScreen(new GuiLanguage(this,mc.gameSettings, mc.getLanguageManager()));
                        break;
                    case "exit":
                        mc.shutdown();
                        break;
                }
            };

            customButton.drawScreen(mouseX, mouseY);

            count++;
            xOffset += buttonWidth + spacing;
        }


        float initialX = 5;
        float initialY = 10;
        for(Change change : Changelog.changelog){
            RenderUtil.resetColor();
            FontUtil.lustFont18.drawString(change.getDescription(), initialX, initialY, change.getType().getColor());
            initialY += FontUtil.lustFont18.getHeight() + 3;
        }

    }


    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        customButtons.forEach(customButton -> customButton.mouseClicked(mouseX, mouseY, mouseButton));
    }


}

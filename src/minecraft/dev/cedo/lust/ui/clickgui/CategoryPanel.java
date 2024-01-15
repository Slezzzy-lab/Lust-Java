package dev.cedo.lust.ui.clickgui;

import dev.cedo.lust.Lust;
import dev.cedo.lust.module.Category;
import dev.cedo.lust.module.Module;
import dev.cedo.lust.ui.Screen;
import dev.cedo.lust.ui.clickgui.components.ModuleRect;
import dev.cedo.lust.utils.animations.Animation;
import dev.cedo.lust.utils.animations.Direction;
import dev.cedo.lust.utils.animations.impl.DecelerateAnimation;
import dev.cedo.lust.utils.font.FontUtil;
import dev.cedo.lust.utils.misc.HoveringUtil;
import net.minecraft.client.gui.Gui;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author cedo
 * @since 05/19/2022
 */
public class CategoryPanel implements Screen {

    private final Category category;

    private final float categoryRectHeight = 18;
    private final float rectWidth = 110;
    private List<ModuleRect> moduleRects;

    public CategoryPanel(Category category) {
        this.category = category;
    }


    @Override
    public void initGui() {
        if (moduleRects == null) {
            moduleRects = new ArrayList<>();
            for (Module module : Lust.INSTANCE.getModuleCollection().getModulesInCategory(category)) {
                ModuleRect moduleRect = new ModuleRect(module);
                moduleRects.add(moduleRect);
            }
        }

        if (moduleRects != null) {
            moduleRects.forEach(ModuleRect::initGui);
        }

    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {
        if (moduleRects != null) {
            moduleRects.forEach(moduleRect -> moduleRect.keyTyped(typedChar, keyCode));
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY) {
        category.getDrag().onDraw(mouseX, mouseY);


        float x = category.getDrag().getX(), y = category.getDrag().getY();


        int categoryRectColor = new Color(29, 29, 29).getRGB();

        Gui.drawRect2(x, y, rectWidth, categoryRectHeight, categoryRectColor);
        FontUtil.lustBoldFont22.drawCenteredString(category.getName(), x + rectWidth / 2f, y + FontUtil.lustBoldFont22.getMiddleOfBox(categoryRectHeight), -1);

        double count = 0;
        for(ModuleRect moduleRect : moduleRects) {
            moduleRect.x = x;
            moduleRect.height = 17;
            moduleRect.y = (float) (y + categoryRectHeight + (count * 17));
            moduleRect.width = rectWidth;
            moduleRect.drawScreen(mouseX, mouseY);

            // count ups by one but then accounts for setting animation opening
            count += 1 + (moduleRect.getSettingSize());
        }

    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) {
        boolean canDrag = HoveringUtil.isHovering(category.getDrag().getX(), category.getDrag().getY(), rectWidth, categoryRectHeight, mouseX, mouseY);
        category.getDrag().onClick(mouseX, mouseY, button, canDrag);
        moduleRects.forEach(moduleRect -> moduleRect.mouseClicked(mouseX, mouseY, button));
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {
        category.getDrag().onRelease(state);
        moduleRects.forEach(moduleRect -> moduleRect.mouseReleased(mouseX, mouseY, state));
    }
}

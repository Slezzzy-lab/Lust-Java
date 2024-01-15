package dev.cedo.lust.ui.mainmenu;

import dev.cedo.lust.Lust;
import dev.cedo.lust.ui.Screen;
import dev.cedo.lust.utils.animations.Animation;
import dev.cedo.lust.utils.animations.Direction;
import dev.cedo.lust.utils.animations.impl.DecelerateAnimation;
import dev.cedo.lust.utils.font.FontUtil;
import dev.cedo.lust.utils.misc.HoveringUtil;
import dev.cedo.lust.utils.render.ColorUtil;
import dev.cedo.lust.utils.render.RenderUtil;
import lombok.Getter;

import java.awt.*;

/**
 * @author cedo
 * @since 05/19/2022
 */
public class CustomButton implements Screen {

    private final Animation hoverAnimation = new DecelerateAnimation(250, 1);

    @Getter
    private final String name;

    public float x, y, width, height;
    public Runnable clickAction;


    public CustomButton(String name) {
        this.name = name;
    }


    @Override
    public void initGui() {

    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {

    }

    @Override
    public void drawScreen(int mouseX, int mouseY) {
        Color interpolatedColor = ColorUtil.interpolateColorsBackAndForth(15, 1, Lust.INSTANCE.getClientColor(), Lust.INSTANCE.getClientAltColor());

        boolean hovered = HoveringUtil.isHovering(x, y, width, height, mouseX, mouseY);
        hoverAnimation.setDirection(hovered ? Direction.FORWARDS : Direction.BACKWARDS);

        RenderUtil.drawRoundedRect(x, y, width, height, 4, ColorUtil.applyOpacity(interpolatedColor, (float) (.4f + (.4 * hoverAnimation.getOutput()))));
        FontUtil.lustFont20.drawCenteredString(name, x + width / 2f, y + FontUtil.lustFont20.getMiddleOfBox(height), -1);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) {
        if (HoveringUtil.isHovering(x, y, width, height, mouseX, mouseY)) {
            clickAction.run();
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {

    }
}

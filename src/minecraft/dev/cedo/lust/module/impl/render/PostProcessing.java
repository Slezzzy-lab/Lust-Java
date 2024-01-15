package dev.cedo.lust.module.impl.render;

import dev.cedo.lust.Lust;
import dev.cedo.lust.module.Category;
import dev.cedo.lust.module.Module;
import dev.cedo.lust.module.settings.impl.BooleanSetting;
import dev.cedo.lust.module.settings.impl.NumberSetting;
import dev.cedo.lust.ui.notifications.Notification;
import dev.cedo.lust.ui.notifications.NotificationManager;
import dev.cedo.lust.ui.notifications.NotificationType;
import dev.cedo.lust.utils.render.shader.BloomUtil;
import dev.cedo.lust.utils.render.shader.BlurUtil;
import org.lwjgl.input.Keyboard;

/**
 * @author cedo
 * @since 05/18/2022
 */
public class PostProcessing extends Module {

    private final NumberSetting blurRadius = new NumberSetting("Blur Radius", 8, 25, 1, 1);
    private final NumberSetting bloomRadius = new NumberSetting("Bloom Radius", 10, 25, 1, 1);

    public PostProcessing() {
        super("PostProcessing", Category.RENDER, "Adds effects to things", 0);
        addSettings(blurRadius, bloomRadius);
        toggleSilent();
    }

    @Override
    public void toggleSilent() {
        if (!isEnabled() && mc.gameSettings.ofFastRender) {
            NotificationManager.post(NotificationType.WARNING, "Disabled Fast Render", "Post Processing doesn't support fast render.", 5);
            mc.gameSettings.ofFastRender = false;
        }
        super.toggleSilent();
    }

    public void blur() {
        if(!this.isEnabled()) return;
        BlurUtil.renderGaussianBlur(blurRadius.getValue().floatValue(), 2);
        BloomUtil.renderGaussianBloom(bloomRadius.getValue().floatValue());
    }


}

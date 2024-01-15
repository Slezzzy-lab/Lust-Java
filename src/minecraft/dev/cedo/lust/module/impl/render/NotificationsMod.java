package dev.cedo.lust.module.impl.render;

import dev.cedo.lust.module.Category;
import dev.cedo.lust.module.Module;
import dev.cedo.lust.module.settings.impl.BooleanSetting;
import dev.cedo.lust.module.settings.impl.ModeSetting;
import dev.cedo.lust.ui.notifications.Notification;
import dev.cedo.lust.ui.notifications.NotificationManager;
import dev.cedo.lust.utils.animations.Animation;
import dev.cedo.lust.utils.animations.Direction;
import dev.cedo.lust.utils.font.FontUtil;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.ScaledResolution;

/**
 * @author cedo
 * @since 05/18/2022
 */
public class NotificationsMod extends Module {

  public NotificationsMod() {
    super("Notifications", Category.RENDER, "Allows you to customize the client notifications");
    toggleSilent();
  }

  public void render() {
    float yOffset = 0;
    int notificationHeight = 0;
    int notificationWidth;
    int actualOffset = 0;
    ScaledResolution sr = new ScaledResolution(mc);
    for (Notification notification : NotificationManager.getNotifications()) {
      Animation animation = notification.getAnimation();
      animation.setDirection(
          notification.getTimerUtil().hasTimeElapsed((long) notification.getTime())
              ? Direction.BACKWARDS
              : Direction.FORWARDS);

      if (animation.finished(Direction.BACKWARDS)) {
        NotificationManager.getNotifications().remove(notification);
        continue;
      }

      float x, y;

      animation.setDuration(250);
      actualOffset = 8;
      notificationHeight = 28;
      notificationWidth =
          (int)
                  Math.max(
                      FontUtil.lustBoldFont22.getStringWidth(notification.getTitle()),
                      FontUtil.lustFont18.getStringWidth(notification.getDescription()))
              + 35;
      x = sr.getScaledWidth() - (notificationWidth + 5) * (float) animation.getOutput();
      y = sr.getScaledHeight() - (yOffset + 18 + notificationHeight);

      notification.drawDefault(
          x, y, notificationWidth, notificationHeight, (float) animation.getOutput());

      yOffset += (notificationHeight + actualOffset) * animation.getOutput();
    }
  }

  public void renderEffects() {
    float yOffset = 0;
    int notificationHeight = 0;
    int notificationWidth;
    int actualOffset = 0;
    ScaledResolution sr = new ScaledResolution(mc);

    for (Notification notification : NotificationManager.getNotifications()) {
      Animation animation = notification.getAnimation();
      animation.setDirection(
          notification.getTimerUtil().hasTimeElapsed((long) notification.getTime())
              ? Direction.BACKWARDS
              : Direction.FORWARDS);

      if (animation.finished(Direction.BACKWARDS)) {
        NotificationManager.getNotifications().remove(notification);
        continue;
      }
      float x, y;
      actualOffset = 8;
      notificationHeight = 28;
      notificationWidth =
          (int)
                  Math.max(
                      FontUtil.lustBoldFont22.getStringWidth(notification.getTitle()),
                      FontUtil.lustFont18.getStringWidth(notification.getDescription()))
              + 35;
      x = sr.getScaledWidth() - (notificationWidth + 5) * (float) animation.getOutput();
      y = sr.getScaledHeight() - (yOffset + 18 + notificationHeight);
      notification.blurDefault(
          x, y, notificationWidth, notificationHeight, (float) animation.getOutput());
      yOffset += (notificationHeight + actualOffset) * animation.getOutput();
    }
  }
}

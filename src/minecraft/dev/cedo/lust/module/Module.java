package dev.cedo.lust.module;

import dev.cedo.lust.Lust;
import dev.cedo.lust.event.ListenerAdapter;
import dev.cedo.lust.module.settings.Setting;
import dev.cedo.lust.module.settings.impl.KeybindSetting;
import dev.cedo.lust.ui.notifications.NotificationManager;
import dev.cedo.lust.ui.notifications.NotificationType;
import dev.cedo.lust.utils.Utils;
import dev.cedo.lust.utils.animations.Animation;
import dev.cedo.lust.utils.animations.impl.DecelerateAnimation;
import lombok.Getter;
import lombok.Setter;
import org.lwjgl.input.Keyboard;

import java.util.Arrays;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author cedo
 * @since 05/17/2022
 */
@Getter
@Setter
public class Module extends ListenerAdapter implements Utils {
    private final String name, description;
    private final Category category;

    private boolean enabled;

    private final CopyOnWriteArrayList<Setting> settingsList = new CopyOnWriteArrayList<>();
    private final KeybindSetting keybind = new KeybindSetting(Keyboard.KEY_NONE);

    public static int categoryCount;
    private boolean expanded = false;

    //Arraylist Animation
    public final Animation animation = new DecelerateAnimation(250, 1);

    public Module(String name, Category category, String description, int key) {
        this.name = name;
        this.category = category;
        this.description = description;

        keybind.setCode(key);
        addSettings(keybind);
    }

    public Module(String name, Category category, String description) {
        this(name, category, description, Keyboard.KEY_NONE);
    }


    public void toggle() {
        toggleSilent();
        if (enabled) {
            NotificationManager.post(NotificationType.SUCCESS, "Module toggled", this.getName() + " was " + "§aenabled\r");
        } else {
            NotificationManager.post(NotificationType.DISABLE, "Module toggled", this.getName() + " was " + "§cdisabled\r");
        }
    }

    //Toggles without the notifications
    public void toggleSilent() {
        this.enabled = !this.enabled;
        if (this.enabled) {
            this.onEnable();
        } else {
            this.onDisable();
        }
    }

    public void onEnable() {
        Lust.INSTANCE.getEventProtocol().register(this);
    }

    public void onDisable() {
        Lust.INSTANCE.getEventProtocol().unregister(this);
    }


    public void addSettings(Setting... settings) {
        settingsList.addAll(Arrays.asList(settings));
    }

    public void setKey(int code) {
        this.keybind.setCode(code);
    }



}

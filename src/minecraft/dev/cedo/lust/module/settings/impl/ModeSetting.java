package dev.cedo.lust.module.settings.impl;

import dev.cedo.lust.module.settings.Setting;

import java.util.Arrays;
import java.util.List;

public class ModeSetting extends Setting {

    public final List<String> modes;
    private int modeIndex;

    private String currentMode;

    public ModeSetting(String name, String defaultMode, String... modes) {
        setName(name);
        this.modes = Arrays.asList(modes);
        this.modeIndex = this.modes.indexOf(defaultMode);
        if (currentMode == null) currentMode = defaultMode;
    }

    public String getMode() {
        return currentMode;
    }

    public boolean is(String mode) {
        return currentMode.equalsIgnoreCase(mode);
    }

    public void cycleForwards() {
        modeIndex++;
        if (modeIndex > modes.size() - 1) modeIndex = 0;
        currentMode = modes.get(modeIndex);
    }

    public void cycleBackwards() {
        modeIndex--;
        if (modeIndex < 0) modeIndex = modes.size() - 1;
        currentMode = modes.get(modeIndex);
    }

    public void setCurrentMode(String currentMode) {
        this.currentMode = currentMode;
    }

}

package dev.cedo.lust.module.settings.impl;

import dev.cedo.lust.module.settings.Setting;
import lombok.Getter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Getter
public class MultipleBoolSetting extends Setting {

    private final List<BooleanSetting> boolSettings;

    public MultipleBoolSetting(String name, BooleanSetting... booleanSettings) {
        setName(name);
        boolSettings = Arrays.asList(booleanSettings);
    }

    public BooleanSetting getSetting(String settingName) {
        return boolSettings.stream().filter(booleanSetting -> booleanSetting.getName().equalsIgnoreCase(settingName)).findFirst().orElse(null);
    }

}

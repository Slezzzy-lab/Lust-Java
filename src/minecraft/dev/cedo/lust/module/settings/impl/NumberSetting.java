package dev.cedo.lust.module.settings.impl;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import dev.cedo.lust.module.settings.Setting;
import lombok.Getter;

@Getter
public class NumberSetting extends Setting {
    private final double maxValue, minValue, increment, defaultValue;

    @Expose
    @SerializedName("value")
    private Double value;

    public NumberSetting(String name, double defaultValue, double maxValue, double minValue, double increment) {
        setName(name);
        this.maxValue = maxValue;
        this.minValue = minValue;
        this.value = defaultValue;
        this.defaultValue = defaultValue;
        this.increment = increment;
    }

    private static double clamp(double value, double min, double max) {
        value = Math.max(min, value);
        value = Math.min(max, value);
        return value;
    }

    public void setValue(double value) {
        value = clamp(value, this.minValue, this.maxValue);
        value = Math.round(value * (1.0 / this.increment)) / (1.0 / this.increment);
        this.value = value;
    }
}

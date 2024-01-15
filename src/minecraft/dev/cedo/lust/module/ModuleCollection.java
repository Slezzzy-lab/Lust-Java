package dev.cedo.lust.module;

import dev.cedo.lust.module.impl.render.ArraylistMod;
import dev.cedo.lust.module.impl.render.ClickGuiMod;
import dev.cedo.lust.module.impl.render.NotificationsMod;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author cedo
 * @since 05/17/2022
 */
@Getter
@Setter
public class ModuleCollection {

    private final HashMap<Class<? extends Module>, Module> modules;

    public ModuleCollection(HashMap<Class<? extends Module>, Module> modules) {
        this.modules = modules;
    }

    private List<Module> moduleList;

    public List<Module> getModuleList() {
        if(moduleList == null) {
            moduleList = new ArrayList<>(modules.values());
        }
        return moduleList;
    }

    public Module get(Class<? extends Module> mod) {
        return this.modules.get(mod);
    }

    public <T extends Module> T getModule(Class<? extends Module> mod) {
        return (T) this.modules.get(mod);
    }

    public List<Module> getModulesInCategory(Category c) {
        return this.modules.values().stream().filter(m -> m.getCategory() == c).collect(Collectors.toList());
    }

}

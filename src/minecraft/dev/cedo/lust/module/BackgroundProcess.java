package dev.cedo.lust.module;

import dev.cedo.lust.Lust;
import dev.cedo.lust.event.ListenerAdapter;
import dev.cedo.lust.event.impl.game.GameCloseEvent;
import dev.cedo.lust.event.impl.game.KeyPressEvent;
import dev.cedo.lust.utils.Utils;

/**
 * @author cedo
 * @since 05/17/2022
 */
public class BackgroundProcess extends ListenerAdapter implements Utils {

    @Override
    public void onKeyPressEvent(KeyPressEvent event) {
        for(Module module : Lust.INSTANCE.getModuleCollection().getModuleList()) {
            if (module.getKeybind().getCode() == event.getKey()) {
                module.toggle();
            }
        }
    }


    @Override
    public void onGameCloseEvent(GameCloseEvent event) {
        //put stuff in here you want to do on game close
    }
}

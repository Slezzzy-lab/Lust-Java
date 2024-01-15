package dev.cedo.lust.event;

import dev.cedo.lust.event.impl.game.GameCloseEvent;
import dev.cedo.lust.event.impl.game.KeyPressEvent;
import dev.cedo.lust.event.impl.network.PacketReceiveEvent;
import dev.cedo.lust.event.impl.network.PacketSendEvent;
import dev.cedo.lust.event.impl.player.*;
import dev.cedo.lust.event.impl.render.NametagRenderEvent;
import dev.cedo.lust.event.impl.render.Render2DEvent;
import dev.cedo.lust.event.impl.render.Render3DEvent;
import dev.cedo.lust.event.impl.render.ShaderEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * @author cedo
 * @since 05/17/2022
 */
public abstract class ListenerAdapter implements EventListener {
    public void onRender2DEvent(Render2DEvent event) {}
    public void onKeyPressEvent(KeyPressEvent event) {}
    public void onGameCloseEvent(GameCloseEvent event) {}
    public void onMotionEvent(MotionEvent event) {}
    public void onUpdateEvent(UpdateEvent event) {}
    public void onMoveEvent(MoveEvent event) {}
    public void onPlayerMoveUpdateEvent(PlayerMoveUpdateEvent event) {}
    public void onPacketReceiveEvent(PacketReceiveEvent event) {}
    public void onPacketSendEvent(PacketSendEvent event) {}
    public void onNametagRenderEvent(NametagRenderEvent event) {}
    public void onRender3DEvent(Render3DEvent event) {}
    public void onShaderEvent(ShaderEvent event) {}
    public void CollideEvent(CollideEvent event){}

    private final Map<Class<? extends Event>, Consumer<Event>> methods = new HashMap<>();
    private boolean registered = false;

    @Override
    public void onEvent(Event event) {
        if (!registered) {
            start();
            registered = true;
        }

        methods.get(event.getClass()).accept(event);

    }

    private <T> void registerEvent(Class<T> clazz, Consumer<T> consumer) {
        methods.put((Class<? extends Event>) clazz, (Consumer<Event>) consumer);
    }

    private void start() {
        registerEvent(Render2DEvent.class, this::onRender2DEvent);
        registerEvent(KeyPressEvent.class, this::onKeyPressEvent);
        registerEvent(GameCloseEvent.class, this::onGameCloseEvent);
        registerEvent(MotionEvent.class, this::onMotionEvent);
        registerEvent(UpdateEvent.class, this::onUpdateEvent);
        registerEvent(MoveEvent.class, this::onMoveEvent);
        registerEvent(PlayerMoveUpdateEvent.class, this::onPlayerMoveUpdateEvent);
        registerEvent(PacketReceiveEvent.class, this::onPacketReceiveEvent);
        registerEvent(PacketSendEvent.class, this::onPacketSendEvent);
        registerEvent(NametagRenderEvent.class, this::onNametagRenderEvent);
        registerEvent(Render3DEvent.class, this::onRender3DEvent);
        registerEvent(ShaderEvent.class, this::onShaderEvent);
        registerEvent(CollideEvent.class, this::CollideEvent);
    }
}

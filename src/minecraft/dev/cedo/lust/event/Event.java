package dev.cedo.lust.event;

import lombok.Getter;

/**
 * @author cedo
 * @since 05/17/2022
 */
public class Event {
    @Getter
    private boolean cancelled;
    public void cancel() {
        this.cancelled = true;
    }

    public static class StateEvent extends Event {
        private boolean pre = true;

        public boolean isPre() { return pre;}
        public boolean isPost() { return !pre;}
        public void setPost() { pre = false; }
    }
}

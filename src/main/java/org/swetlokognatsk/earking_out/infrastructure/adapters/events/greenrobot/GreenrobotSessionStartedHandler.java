package org.swetlokognatsk.earking_out.infrastructure.adapters.events.greenrobot;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import org.greenrobot.eventbus.Subscribe;
import org.swetlokognatsk.earking_out.core.domain.events.session.SessionStartedEvent;

public final class GreenrobotSessionStartedHandler {

    protected final List<Consumer<SessionStartedEvent>> callbacks = new LinkedList<>();

    public void appendCallback(final Consumer<SessionStartedEvent> callback) {
        this.callbacks.add(callback);
    }

    protected final void traverseCallbacks(final SessionStartedEvent event) {
        for (var callback : callbacks) {
            callback.accept(event);
        }
    }

    @Subscribe
    public void onEvent(final SessionStartedEvent e) {
        traverseCallbacks();
    }
}

package org.swetlokognatsk.earking_out.inftrastructure.adapters.events.spring;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import org.swetlokognatsk.earking_out.core.domain.events.DomainEvent;

abstract class SpringEventHandler<DE extends DomainEvent> {
    protected final List<Consumer<DE>> callbacks = new LinkedList<>();

    // TODO the most awkward implementation. need to dive deep in how it should be refactored
    public void appendCallback(final Consumer<DE> callback) {
        this.callbacks.add(callback);
    }

    protected final void traverseCallbacks(final DE event) {
        for (var callback : callbacks) {
            callback.accept(event);
        }
    }
}

package org.swetlokognatsk.earking_out.inftrastructure.adapters.events.spring;

import java.util.function.Consumer;
import org.swetlokognatsk.earking_out.core.domain.events.DomainEvent;

abstract class SpringEventHandler<DE extends DomainEvent> {
    Consumer<DE> callback;

    // TODO the most awkward implementation. need to dive deep in how it should be refactored
    public void setCallback(final Consumer<DE> callback) {
        this.callback = callback;
    }
}

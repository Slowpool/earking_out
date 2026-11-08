package org.swetlokognatsk.earking_out.core.domain.events.handlers.base;

import org.swetlokognatsk.earking_out.core.domain.events.DomainEvent;
import java.util.function.Consumer;

@FunctionalInterface
public interface DomainEventHandler<DE extends DomainEvent> extends Consumer<DE> {

    void handle(final DE domainEvent);

    default void accept(DE domainEvent) {
        handle(domainEvent);
    }
}

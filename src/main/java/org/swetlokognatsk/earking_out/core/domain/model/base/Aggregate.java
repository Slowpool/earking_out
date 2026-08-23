package org.swetlokognatsk.earking_out.core.domain.model.base;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.NotImplementedException;
import org.swetlokognatsk.earking_out.core.domain.events.DomainEvent;
import org.swetlokognatsk.earking_out.core.domain.events.DomainEventsFactory;
import org.swetlokognatsk.earking_out.core.ports.di.DI;

public abstract class Aggregate<ID> extends Entity<ID> {
    private static final long serialVersionUID = 1L;

    private final List<DomainEvent> events = new ArrayList<>();

    public Aggregate(final ID id) {
        super(id);
    }

    protected final void addEvent(final DomainEvent event) {
        events.add(event);
    }

    public final List<DomainEvent> flushEvents() {
        var eventsCopy = List.copyOf(events);
        events.clear();
        return eventsCopy;
    }

    protected DomainEventsFactory getDomainEventsFactory() {
        return DI.get(DomainEventsFactory.class);
    }
}

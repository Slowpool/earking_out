package org.swetlokognatsk.earking_out.core.domain.model.base;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.NotImplementedException;
import org.swetlokognatsk.earking_out.core.domain.events.DomainEvent;
import org.swetlokognatsk.earking_out.core.domain.events.pianokeyboard.DomainEventsFactory;
import org.swetlokognatsk.earking_out.core.ports.DI;

public abstract class Aggregate<ID> extends Entity<ID> implements Model {
    private static final long serialVersionUID = 1L;

    protected final List<DomainEvent> events = new ArrayList<>();

    public Aggregate(final ID id) {
        super(id);
    }

    // TODO delete this latch, implement it inside each aggregate
    public List<String> getErrors() {
        throw new NotImplementedException();
    }

    protected final void addEvent(final DomainEvent event) {
        events.add(event);
    }

    public final List<DomainEvent> releaseEvents() {
        var eventsCopy = List.copyOf(events);
        events.clear();
        return eventsCopy;
    }

    protected DomainEventsFactory getDomainEventsFactory() {
        return DI.get(DomainEventsFactory.class);
    }
}

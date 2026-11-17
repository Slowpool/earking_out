package org.swetlokognatsk.earking_out.achievements_service.core.domain.model.events;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public final class DomainEvents extends HashMap<String, List<DomainEvent>> {

    public DomainEvents() {
    }

    public DomainEvents(final DomainEvent... domainEvents) {
        for (var event : domainEvents) {
            add(event);
        }
    }

    public void add(final DomainEvent event) {
        compute(event.type(), (key, value) -> {
            List<DomainEvent> list = value == null
                    ? list = new LinkedList<DomainEvent>()
                    : value;
            list.add(event);
            return list;
        });
    }

    /**
     * Use
     * {@link org.swetlokognatsk.earking_out.achievements_service.core.domain.model.events.DomainEvents#add}
     * instead
     */
    @Deprecated
    public List<DomainEvent> put(final String eventType, final List<DomainEvent> events) {
        return super.put(eventType, events);
    }
}

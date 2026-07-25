package org.swetlokognatsk.earking_out.inftrastructure.adapters.base;

import java.util.List;
import org.swetlokognatsk.earking_out.core.ports.DI;
import org.swetlokognatsk.earking_out.core.ports.events.EventPublisher;
import org.swetlokognatsk.earking_out.core.domain.events.DomainEvent;

// TODO should it be here or in core?
public abstract class AggregateRepository {

    protected void publishEvents(final List<DomainEvent> events) {
        var eventPublisher = DI.get(EventPublisher.class);
        eventPublisher.publish(events);
    }
}

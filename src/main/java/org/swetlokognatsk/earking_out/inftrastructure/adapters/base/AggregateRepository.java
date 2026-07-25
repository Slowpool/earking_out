package org.swetlokognatsk.earking_out.inftrastructure.adapters.base;

import org.swetlokognatsk.earking_out.core.ports.DI;
import org.swetlokognatsk.earking_out.core.ports.events.EventPublisher;

public class AggregateRepository {

    protected void publishEvents() {
        // TODO publishEvents() method? abstract Repository class?
        var eventPublisher = DI.get(EventPublisher.class);
        eventPublisher.publish(events);
    }
}

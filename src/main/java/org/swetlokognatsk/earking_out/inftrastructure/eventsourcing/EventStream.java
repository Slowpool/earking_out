package org.swetlokognatsk.earking_out.inftrastructure.eventsourcing;

import org.swetlokognatsk.earking_out.core.domain.events.DomainEvent;
import org.swetlokognatsk.earking_out.core.domain.model.base.AggregateRoot;

// TODO use immutable collection for events
public record EventStream<ID>(ID id, DomainEvent[] events) {
    
    // public 
}

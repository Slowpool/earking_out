package org.swetlokognatsk.earking_out.infrastructure.adapters.eventsourcing;

import org.springframework.stereotype.Repository;
import org.swetlokognatsk.earking_out.core.domain.events.EventStream;
import org.swetlokognatsk.earking_out.core.ports.eventsourcing.EventStore;
import jakarta.persistence.EntityManager;

public class SpringJpaEventStore implements EventStore {

    private final EntityManager entityManager;

    public SpringJpaEventStore(final EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void append(final EventStream<?> eventStream) {
        // TODO JpaEventStore.append 
    }

    public <ID> EventStream<ID> getAllEvents(final ID id) {
        // TODO JpaEventStore.getAllEvents 
        return null;
    }

}

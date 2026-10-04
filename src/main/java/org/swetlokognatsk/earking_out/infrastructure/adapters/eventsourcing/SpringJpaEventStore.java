package org.swetlokognatsk.earking_out.infrastructure.adapters.eventsourcing;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import org.swetlokognatsk.earking_out.core.domain.events.DomainEvent;
import org.swetlokognatsk.earking_out.core.domain.events.EventSourcingEventId;
import org.swetlokognatsk.earking_out.core.domain.events.EventStream;
import org.swetlokognatsk.earking_out.core.domain.events.session.UserTriedToGuessPuzzleEvent;
import org.swetlokognatsk.earking_out.core.domain.model.identity.User;
import org.swetlokognatsk.earking_out.core.domain.model.identity.UserId;
import org.swetlokognatsk.earking_out.core.ports.events.DomainEventJsonSerializer;
import org.swetlokognatsk.earking_out.core.ports.eventsourcing.EventStore;
import org.swetlokognatsk.earking_out.infrastructure.adapters.events.EventVersionsRegistry;

import jakarta.persistence.EntityManager;

public class SpringJpaEventStore implements EventStore {

    private final EntityManager entityManager;
    private final TransactionTemplate transactionTemplate;
    private final DomainEventJsonSerializer domainEventJsonSerializer;
    private final UserId userId;

    public SpringJpaEventStore(final EntityManager entityManager, final PlatformTransactionManager transactionManager, final DomainEventJsonSerializer domainEventJsonSerializer, final User user) {
        this.entityManager = entityManager;
        this.transactionTemplate = new TransactionTemplate(transactionManager);
        this.domainEventJsonSerializer = domainEventJsonSerializer;
        this.userId = user.id;
    }

    public void append(final EventStream<?> eventStream) {
        var streamId = eventStream.id()
                .toString();

        // TODO think about optimization (via taking the series of transactions out of loop)
        for (var event : eventStream) {
            var eventEntity = mapEventToEntity(event, streamId);
            persist(eventEntity);
        }
    }

    private EventSourcingEventEntity mapEventToEntity(final DomainEvent event, final String streamId) {
        var payload = domainEventJsonSerializer.serializeDomainEvent(event);
        var eventId = EventSourcingEventId.random()
                .toString();
        var version = EventVersionsRegistry.getVersion(event.getClass());

        // TODO temporarily fix. migrate column type to jsonb in psql and order by json's payload.timestamp field instead, return programmatically generated date here
        return new EventSourcingEventEntity(eventId, streamId, userId.id(), getEventType(event), /*generateCreatedOn()*/event.timestamp, payload, version);
    }

    private void persist(final EventSourcingEventEntity eventEntity) {
        transactionTemplate.execute(status -> {
            entityManager.persist(eventEntity);
            return null;
        });
    }

    private String getEventType(final DomainEvent event) {
        return event.getClass()
                .getName()
                .toString();
    }

    // TODO do it in more elegant way
    private static LocalDateTime generateCreatedOn() {
        return LocalDateTime.now();
    }

    public <ID> EventStream<ID> getAllEvents(final ID id) {
        var jpql = """
                SELECT event
                FROM EventSourcingEventEntity event
                WHERE event.streamId = :streamId
                    AND event.userId = :userId
                ORDER BY event.createdOn ASC
                """;
        var eventEntities = entityManager.createQuery(jpql, EventSourcingEventEntity.class)
                .setParameter("streamId", id.toString())
                // TODO write integration test
                .setParameter("userId", userId.id())
                .getResultList();
        var domainEvents = mapEventEntitiesToDomainEvents(eventEntities)
                .toArray(DomainEvent[]::new);
        return new EventStream<>(id, domainEvents);
    }

    private List<DomainEvent> mapEventEntitiesToDomainEvents(final List<EventSourcingEventEntity> eventEntities) {
        return eventEntities.stream()
                // TODO ideally it should somehow mark that there was exception, but continue processing and return all possible events
                .map(this::mapEventEntityToDomainEvent)
                .toList();
    }

    private DomainEvent mapEventEntityToDomainEvent(final EventSourcingEventEntity eventEntity) {
        try {
            var eventClass = (Class<? extends DomainEvent>) Class.forName(eventEntity.getType());
            var serializedEvent = eventEntity.getPayload();
            return domainEventJsonSerializer.deserializeDomainEvent(serializedEvent, eventClass);
        } catch (ClassNotFoundException e) {
            // TODO temporary unchecked exception hack
            throw new RuntimeException(e);
        }
    }

}

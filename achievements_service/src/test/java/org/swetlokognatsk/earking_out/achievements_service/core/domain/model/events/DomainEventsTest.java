package org.swetlokognatsk.earking_out.achievements_service.core.domain.model.events;

import static org.junit.jupiter.api.Assertions.*;
import static org.swetlokognatsk.earking_out.achievements_service.core.domain.DomainEventsTestHelper.*;
import java.time.LocalDateTime;
import org.junit.jupiter.api.*;

public class DomainEventsTest {

    private DomainEvent createSomeEvent(final String type) {
        return new DomainEvent(type, USER_ID, LocalDateTime.now(), newProperties());
    }

    private DomainEvent createSomeEvent() {
        return new DomainEvent(SESSION_EVENT, USER_ID, LocalDateTime.now(), newProperties());
    }

    private void assertHasOnlyThisEvent(final DomainEvents haystack, final DomainEvent needle) {
        assertEquals(1, haystack.size());
        assertTrue(haystack.containsKey(needle.type()));
        assertEquals(needle, haystack.get(needle.type())
                .getFirst());
    }

    private void assertContainsEvent(final DomainEvents haystack, final DomainEvent needle) {
        assertTrue(haystack.containsKey(needle.type()));
        var obtainedEvents = haystack.get(needle.type());
        assertTrue(obtainedEvents.contains(needle));
    }

    @Test
    void emptyDomainEvents() {
        var domainEvents = new DomainEvents();

        assertTrue(domainEvents.isEmpty());
    }

    @Test
    void addToEmptyCollection() {
        var domainEvents = new DomainEvents();
        var domainEvent = createSomeEvent();

        domainEvents.add(domainEvent);

        assertHasOnlyThisEvent(domainEvents, domainEvent);
    }

    @Test
    void initWithOneDomainEvent() {
        var domainEvent = createSomeEvent();

        var domainEvents = new DomainEvents(domainEvent);

        assertHasOnlyThisEvent(domainEvents, domainEvent);
    }

    @Test
    void addToNotEmpty() {
        var type = SESSION_EVENT;
        var sourceEvent = createSomeEvent(type);
        var domainEvents = new DomainEvents(sourceEvent);
        var extraEvent = createSomeEvent(type);

        domainEvents.add(extraEvent);

        // 1 because only 1 event type is used
        assertEquals(1, domainEvents.size());
        assertContainsEvent(domainEvents, sourceEvent);
        assertContainsEvent(domainEvents, extraEvent);
    }

    @Test
    void addToNotEmptyWithDifferentType() {
        var type = SESSION_EVENT;
        var sourceEvent = createSomeEvent(type);
        var domainEvents = new DomainEvents(sourceEvent);
        var anotherType = IDENTITY_EVENT;
        var extraEvent = createSomeEvent(anotherType);

        domainEvents.add(extraEvent);

        assertEquals(2, domainEvents.size());
        assertContainsEvent(domainEvents, sourceEvent);
        assertContainsEvent(domainEvents, extraEvent);
    }
}

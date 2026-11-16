package org.swetlokognatsk.earking_out.achievements_service.core.domain.model.events;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.swetlokognatsk.earking_out.achievements_service.core.domain.model.events.DomainEventTypes.GUESS;
import java.time.LocalDateTime;
import java.util.HashMap;
import org.junit.jupiter.api.Test;
import org.swetlokognatsk.earking_out.achievements_service.core.domain.model.UserId;

public class DomainEventTest {

    private static final UserId USER_ID = new UserId(1);
    private static final String USER = "user";

    @Test
    void getNotExistingProperty() {
        var event = new DomainEvent(GUESS, USER_ID, LocalDateTime.now(), new HashMap<>());

        try {
            event.get("anything");
            fail();
        } catch (IllegalArgumentException e) {
        }
    }

    @Test
    void getNullProperty() {
        var properties = new HashMap<String, Object>();
        properties.put(USER, null);
        var event = new DomainEvent(GUESS, USER_ID, LocalDateTime.now(), properties);

        var obtainedPassport = event.get("user");

        assertEquals(null, obtainedPassport);
    }

    @Test
    void getNotExistingNestedProperty() {
        var properties = new HashMap<String, Object>();
        properties.put(USER, "john");
        var event = new DomainEvent(GUESS, USER_ID, LocalDateTime.now(), properties);

        try {
            event.get("user.age");
            fail();
        } catch (IllegalArgumentException e) {
        }
    }

    @Test
    void getExistingProperty() {
        var properties = new HashMap<String, Object>();
        var name = "john";
        properties.put(USER, name);
        var event = new DomainEvent(GUESS, USER_ID, LocalDateTime.now(), properties);

        var obtainedName = event.get(USER);

        assertEquals(name, obtainedName);
    }

    @Test
    void getExistingNestedProperty() {
        var user = new HashMap<String, Object>();
        int someId = 1937;
        user.put("id", someId);
        var properties = new HashMap<String, Object>();
        properties.put(USER, user);
        var event = new DomainEvent(GUESS, USER_ID, LocalDateTime.now(), properties);

        var obtainedPassport = event.get("user.id");

        assertEquals(someId, obtainedPassport);
    }

}

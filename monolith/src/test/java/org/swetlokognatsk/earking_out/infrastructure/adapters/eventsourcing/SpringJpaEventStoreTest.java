package org.swetlokognatsk.earking_out.infrastructure.adapters.eventsourcing;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.swetlokognatsk.earking_out.EOSpringBootTest;
import org.swetlokognatsk.earking_out.core.domain.events.DomainEvent;
import org.swetlokognatsk.earking_out.core.domain.events.EventStream;
import org.swetlokognatsk.earking_out.core.domain.events.session.NewPuzzleCreatedEvent;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.perfectpitch.AudioPerfectPitchPuzzle;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionId;
import org.swetlokognatsk.earking_out.core.domain.model.solutions.perfectpitch.AudioPerfectPitchSolution;
import org.swetlokognatsk.earking_out.core.ports.di.DI;
import static org.junit.jupiter.api.Assertions.*;
import static org.swetlokognatsk.earking_out.SpringProfiles.*;
import static org.swetlokognatsk.earking_out.TestHelper.USER_ID;
import static org.swetlokognatsk.earking_out.core.domain.model.exercises.ExercisesFactory.AUDIO_PERFECT_PITCH_EXERCISE;
import static org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber.FIRST_NOTE_NUMBER;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.UUID;
import javax.sql.DataSource;
import org.junit.jupiter.api.*;

@EOSpringBootTest
@ActiveProfiles({ TEST, JPA })
@EnableAutoConfiguration(exclude = {})
public class SpringJpaEventStoreTest {

    private SpringJpaEventStore store;

    private NewPuzzleCreatedEvent createSomeEvent() {
        return new NewPuzzleCreatedEvent(USER_ID, LocalDateTime.now(), SessionId.random(), new AudioPerfectPitchPuzzle(AUDIO_PERFECT_PITCH_EXERCISE, new AudioPerfectPitchSolution(FIRST_NOTE_NUMBER)));
    }

    @BeforeEach
    public void setup() {
        // yes, it's only useful on first time when `this.store` is null, whereas all further assignings for further test methods here are redundant
        store = DI.get(SpringJpaEventStore.class);
    }

    @Test
    void getAllEventsWhenNoEvents() {
        var events = store.getAllEvents(UUID.randomUUID());

        assertEquals(0, events.events().length);
    }

    @Test
    void appendOneEvent() {
        var event = createSomeEvent();
        var eventStream = new EventStream<>(event.sessionId, new DomainEvent[] { event });

        store.append(eventStream);

        var events = store.getAllEvents(event.sessionId).events();
        assertEquals(1, events.length);
        var obtainedEvent = events[0];
        assertEquals(event, obtainedEvent);
    }
}

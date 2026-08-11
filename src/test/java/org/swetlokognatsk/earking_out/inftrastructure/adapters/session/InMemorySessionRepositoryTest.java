package org.swetlokognatsk.earking_out.inftrastructure.adapters.session;

import static org.junit.Assert.*;
import org.junit.*;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionId;
import org.swetlokognatsk.earking_out.core.domain.model.session.factories.SessionAggregatesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.session.perfectpitch.AudioPerfectPitchSessionAggregate;
import org.swetlokognatsk.earking_out.core.ports.di.DI;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.session.perfectpitch.InMemoryAudioPerfectPitchSessionRepository;

public final class InMemorySessionRepositoryTest {

    private InMemorySessionRepository<AudioPerfectPitchSessionAggregate> repository;
    private SessionAggregatesFactory sessionAggregatesFactory;
    private SessionId seededSessionId;

    @Before
    public void setup() {
        // TODO is there another way to test exactly InMemorySesisonRepository abstract class?
        repository = DI.get(InMemoryAudioPerfectPitchSessionRepository.class);
        seededSessionId = TestSessionRepositoryHelper.seedTestSession(repository);

        sessionAggregatesFactory = DI.get(SessionAggregatesFactory.class);
    }

    @Test
    public void gettingActiveSession() {
        var activeSession = repository.getActiveSession();

        assertTrue(seededSessionId.equals(activeSession.getId()));
    }

    @Test
    public void gettingActiveSessionAfterAbortingTheActiveOne() {
        var sessionAggregate = repository.get(seededSessionId);
        sessionAggregate.abort();
        repository.save(sessionAggregate);

        try {
            repository.getActiveSession();
            fail();
        }
        catch (Throwable e) {
        }
    }

    @Test
    public void addingAnotherActiveSessionWhenTheFirstOneIsYetActive() {
        AudioPerfectPitchSessionAggregate newSessionAggregate = sessionAggregatesFactory.create(new AudioPerfectPitchExercise());
        
        repository.save(newSessionAggregate);

        var activeSession = repository.getActiveSession();
        assertEquals(newSessionAggregate.getId(), activeSession.getId());
    }
}

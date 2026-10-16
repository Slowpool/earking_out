package org.swetlokognatsk.earking_out.infrastructure.adapters.session;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.annotation.DirtiesContext.MethodMode;
import org.swetlokognatsk.earking_out.EOSpringBootTest;
import org.swetlokognatsk.earking_out.core.domain.model.identity.UserId;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfigAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfigTestHelper;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionId;
import org.swetlokognatsk.earking_out.core.domain.model.session.exceptions.NoActiveSessionException;
import org.swetlokognatsk.earking_out.core.domain.model.session.factories.SessionAggregatesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.session.perfectpitch.AudioPerfectPitchSessionAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.solutions.perfectpitch.AudioPerfectPitchSolution;
import org.swetlokognatsk.earking_out.core.ports.di.DI;
import org.swetlokognatsk.earking_out.infrastructure.adapters.identity.TestUserResolver;
import org.swetlokognatsk.earking_out.infrastructure.adapters.puzzles.generators.perfectpitch.FakeAudioPerfectPitchSolutionGenerator;
import org.swetlokognatsk.earking_out.infrastructure.adapters.session.perfectpitch.InMemoryAudioPerfectPitchSessionRepository;
import static org.swetlokognatsk.earking_out.core.domain.model.exercises.ExercisesFactory.*;
import static org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber.FIRST_NOTE_NUMBER;

@EOSpringBootTest
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
public final class InMemorySessionRepositoryTest {
    private static final AudioPerfectPitchSolution SOLUTION = new AudioPerfectPitchSolution(FIRST_NOTE_NUMBER);

    private static final UserId USER_1 = new UserId(1);
    private static final UserId USER_2 = new UserId(2);

    private InMemorySessionRepository<AudioPerfectPitchSessionAggregate> repository;
    private SessionAggregatesFactory sessionAggregatesFactory;
    private SessionId seededSessionId;
    private SessionRepositoryTestHelper sessionRepositoryHelper;
    private PuzzleConfigTestHelper puzzleConfigHelper;

    private PuzzleConfigAggregate<?> puzzleConfig;

    private void switchToUser(final UserId userId) {
        DI.get(TestUserResolver.class)
                .setCurrentUserId(userId);
    }

    private void assertActiveSessionExists() {
        // if there's no exception, then some active session was returned, then active session does exist
        repository.getActiveSession();
    }

    private void assertNoActiveSession() {
        try {
            repository.getActiveSession();
            fail();
        } catch (NoActiveSessionException e) {
        }
    }

    private void abortActiveSession() {
        var activeSession = repository.getActiveSession();
        activeSession.abort();
        repository.save(activeSession);
    }

    private void completeSession() {
        var activeSession = repository.getActiveSession();
        for (int i = 0; i < puzzleConfig.getTargetNumberOfPuzzles(); i++) {
            activeSession.guess(SOLUTION);
        }
        repository.save(activeSession);
    }

    @BeforeEach
    public void setup() {
        // TODO is there another way to test exactly InMemorySesisonRepository abstract class?
        repository = DI.get(InMemoryAudioPerfectPitchSessionRepository.class);
        sessionRepositoryHelper = DI.get(SessionRepositoryTestHelper.class);
        sessionAggregatesFactory = DI.get(SessionAggregatesFactory.class);
        puzzleConfigHelper = DI.get(PuzzleConfigTestHelper.class);

        DI.get(FakeAudioPerfectPitchSolutionGenerator.class).fakeSolution = SOLUTION;
        switchToUser(USER_1);
        puzzleConfig = puzzleConfigHelper.configureSomeValidPuzzleConfig();

        switchToUser(USER_2);
        puzzleConfig = puzzleConfigHelper.configureSomeValidPuzzleConfig();

        switchToUser(USER_1);
        seededSessionId = sessionRepositoryHelper.startSession(repository);
    }

    @Test
    public void gettingActiveSession() {
        var activeSession = repository.getActiveSession();

        assertTrue(seededSessionId.equals(activeSession.getId()));
    }

    @Test
    @DirtiesContext(methodMode = MethodMode.AFTER_METHOD)
    public void gettingActiveSessionAfterAbortingTheActiveOne() {
        var sessionAggregate = repository.get(seededSessionId);
        sessionAggregate.abort();
        repository.save(sessionAggregate);

        try {
            repository.getActiveSession();
            fail();
        } catch (Throwable e) {
        }
    }

    @Test
    @DirtiesContext(methodMode = MethodMode.AFTER_METHOD)
    public void addingAnotherActiveSessionWhenTheFirstOneIsYetActive() {
        AudioPerfectPitchSessionAggregate newSessionAggregate = sessionAggregatesFactory.create(AUDIO_PERFECT_PITCH_EXERCISE);

        repository.save(newSessionAggregate);

        var activeSession = repository.getActiveSession();
        assertEquals(newSessionAggregate.getId(), activeSession.getId());
    }

    @Test
    @DirtiesContext(methodMode = MethodMode.AFTER_METHOD)
    public void createdSessionIsAttachedToCurrentUser() {
        assertActiveSessionExists();

        switchToUser(USER_2);
        assertNoActiveSession();

        switchToUser(USER_1);
        assertActiveSessionExists();
    }

    @Test
    @DirtiesContext(methodMode = MethodMode.AFTER_METHOD)
    public void abortSessionDoesNotAffectSessionsOfOtherUsers() {
        switchToUser(USER_2);
        sessionRepositoryHelper.startSession(repository);

        switchToUser(USER_1);
        abortActiveSession();
        assertNoActiveSession();

        switchToUser(USER_2);
        assertActiveSessionExists();
    }

    @Test
    @DirtiesContext(methodMode = MethodMode.AFTER_METHOD)
    public void completingTheSessionDoesNotAffectSessionsOfOtherUsers() {
        switchToUser(USER_2);
        sessionRepositoryHelper.startSession(repository);

        switchToUser(USER_1);
        completeSession();
        assertNoActiveSession();

        switchToUser(USER_2);
        assertActiveSessionExists();
    }
}

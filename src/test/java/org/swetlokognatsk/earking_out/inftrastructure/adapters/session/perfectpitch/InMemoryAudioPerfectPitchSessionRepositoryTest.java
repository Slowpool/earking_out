package org.swetlokognatsk.earking_out.inftrastructure.adapters.session.perfectpitch;

import static org.junit.Assert.*;
import static org.swetlokognatsk.earking_out.core.domain.model.music.Constants.*;
import static org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber.*;
import org.junit.Before;
import org.junit.Test;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardId;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionId;
import org.swetlokognatsk.earking_out.core.domain.model.session.factories.SessionAggregatesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.session.perfectpitch.AudioPerfectPitchSessionAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.solutions.perfectpitch.AudioPerfectPitchSolution;
import org.swetlokognatsk.earking_out.core.ports.DI;
import org.swetlokognatsk.earking_out.core.ports.piano.PianoKeyboardStorageAdapter;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.InMemoryRepositoryTest;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.puzzles.generators.perfectpitch.FakeAudioPerfectPitchSolutionGenerator;

public class InMemoryAudioPerfectPitchSessionRepositoryTest extends InMemoryRepositoryTest<SessionId, AudioPerfectPitchSessionAggregate, InMemoryAudioPerfectPitchSessionRepository> {
    protected static final AudioPerfectPitchSolution SOLUTION = new AudioPerfectPitchSolution(FIRST_NOTE_NUMBER);
    protected static final AudioPerfectPitchSolution WRONG_SOLUTION = new AudioPerfectPitchSolution(SOLUTION.keyNumber.increment());

    protected InMemoryAudioPerfectPitchSessionRepository repository;
    protected SessionId seededSessionId;

    @Before
    public void setup() {
        DI.deleteSingletons();
        repository = DI.get(InMemoryAudioPerfectPitchSessionRepository.class);

        seedTestSession();
    }

    protected void seedTestSession() {
        var sessionAggregatesFactory = DI.get(SessionAggregatesFactory.class);
        AudioPerfectPitchSessionAggregate someSession = sessionAggregatesFactory.create(new AudioPerfectPitchExercise());
        seededSessionId = someSession.getId();
        repository.save(someSession);
    }

    protected AudioPerfectPitchSessionAggregate getSomeAggregate() {
        return getAggregate();
    }

    protected AudioPerfectPitchSessionAggregate getAggregate() {
        FakeAudioPerfectPitchSolutionGenerator.fakeSolution = SOLUTION;
        return repository.get(seededSessionId);
    }

    protected InMemoryAudioPerfectPitchSessionRepository getRepository() {
        return repository;
    }

    protected void makeMinorChange(final AudioPerfectPitchSessionAggregate aggregate) {
        aggregate.guess(WRONG_SOLUTION);
    }

    protected void assertAreDifferentByMinorChange(final AudioPerfectPitchSessionAggregate freshman, final AudioPerfectPitchSessionAggregate suspect) {
        assertEquals(1, suspect.getNumberOfGuessesOfCurrentPuzzle());
        assertEquals(0, freshman.getNumberOfGuessesOfCurrentPuzzle());
    }

    @Test
    public void ensureGetMethodGivesCopyWithoutSaveProxy() {
        ensureGetMethodGivesCopyWithoutSave();
    }

    @Test
    public void ensureGetMethodGivesCopyAfterSaveProxy() {
        ensureGetMethodGivesCopyAfterSave();
    }

    @Test
    public void ensureSaveMethodPersistsCopyProxy() {
        ensureSaveMethodPersistsCopy();
    }

    // // TODO i'm not sure whether should it be tested at all? anyway pianoKeyboardRepository won't be accessed from any another place, so it does not make sense to refresh pianoKeyboards for each get/save sessionRepository access
    // @Test
    // public void getMethodLoadsPianoKeyboardFromRepository() {
    //     var aggregate = getAggregate();
    //     var aggregatePianoKeyboard = aggregate.getGuessingPianoKeyboard();
    //     var sourcePianoKeyboard = getPianoKeyboardRepository().get(PianoKeyboardId.PERFECT_PITCH_NOTES_PICKER);

    //     assertArrayEquals(aggregatePianoKeyboard.getSelectedKeyNumbers(), sourcePianoKeyboard.getSelectedKeyNumbers());

    //     sourcePianoKeyboard.

    // }

    // @Test
    // public void saveMethodSavesPianoKeyboardToRepository() {

    // }

}

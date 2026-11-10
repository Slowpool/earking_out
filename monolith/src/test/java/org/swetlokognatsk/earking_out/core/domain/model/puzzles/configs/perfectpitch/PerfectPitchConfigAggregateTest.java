package org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch;

import static org.junit.jupiter.api.Assertions.*;
import static org.swetlokognatsk.earking_out.core.domain.model.music.Constants.*;
import static org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber.*;
import static org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardTestHelper.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.swetlokognatsk.earking_out.EOSpringBootTest;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfigTestHelper;
import org.swetlokognatsk.earking_out.core.ports.config.PuzzleConfigRepository;
import org.swetlokognatsk.earking_out.core.ports.di.DI;
import static org.swetlokognatsk.earking_out.core.domain.model.exercises.ExercisesFactory.*;

@EOSpringBootTest
public final class PerfectPitchConfigAggregateTest {

    private PuzzleConfigRepository puzzleConfigRepository;
    private PuzzleConfigTestHelper puzzleConfigHelper;

    @BeforeEach
    public void setup() {
        puzzleConfigRepository = DI.get(PuzzleConfigRepository.class);
        puzzleConfigHelper = DI.get(PuzzleConfigTestHelper.class);

        puzzleConfigHelper.configureSomeValidPuzzleConfig();
    }

    @Test
    public void ensureRootNoteIsUpdated() {
        var aggregate = getPerfectPitchAggregate();
        assertEquals(null, aggregate.getNormalizedRootNote());

        PianoKeyNumber newRootNote = FIRST_NOTE_NUMBER;
        aggregate.updateProperty(PerfectPitchConfigAggregate.NORMALIZED_ROOT_NOTE_PROP, newRootNote);

        assertEquals(newRootNote, aggregate.getNormalizedRootNote());
    }

    @Test
    public void ensureRootNoteUpdatingAlsoCausesPianoKeyboardUpdate() {
        // // TODO this is irrelevant. should domain event subscirptions be tested?
        // var pianoKeyboardId = PianoKeyboardId.AUDIO_PERFECT_PITCH_ROOT_NOTE_PICKER;
        // var configAggregate = getPerfectPitchAggregate();
        // var pianoKeyboard = configAggregate.getPianoKeyboard(pianoKeyboardId);
        // assertNoSelectedKeys(pianoKeyboard);

        // PianoKeyNumber newRootNote = FIRST_NOTE_NUMBER;
        // configAggregate.updateViaPianoKeyPressing(pianoKeyboardId, newRootNote);
        // pianoKeyboard = configAggregate.getPianoKeyboard(pianoKeyboardId);

        // assertOnlyThisKeyIsSelected(newRootNote, pianoKeyboard);
        // assertEquals(1, pianoKeyboard.selectedKeys().length);
    }

    private PerfectPitchConfigAggregate<?> getPerfectPitchAggregate() {
        return (PerfectPitchConfigAggregate<?>) puzzleConfigRepository.getPuzzleConfig(AUDIO_PERFECT_PITCH_EXERCISE);
    }
}

package org.swetlokognatsk.earking_out.core.domain.services.domain.puzzles.configs.perfectpitch;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import static org.swetlokognatsk.earking_out.core.domain.model.exercises.ExercisesFactory.*;
import static org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber.FIRST_NOTE_NUMBER;
import static org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber.LAST_NOTE_NUMBER;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.factories.AbstractPuzzleConfigAggregatesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.factories.perfectpitch.AudioPerfectPitchConfigAggregatesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.AudioPerfectPitchConfigAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.PerfectPitchConfigAggregate;
import org.swetlokognatsk.earking_out.core.domain.services.domain.puzzles.configs.PuzzleConfigValidatorTest;
import org.swetlokognatsk.earking_out.core.ports.di.DI;
import static org.swetlokognatsk.earking_out.core.domain.services.domain.puzzles.configs.TestPuzzleConfigValidatorHelper.*;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;
import static org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.PerfectPitchConfigAggregate.*;

public final class EditableAudioPerfectPitchConfigValidatorTest extends PuzzleConfigValidatorTest<AudioPerfectPitchExercise, AudioPerfectPitchConfigAggregate, AudioPerfectPitchConfigAggregatesFactory, EditableAudioPerfectPitchConfigValidator> {

    protected AudioPerfectPitchExercise getExercise() {
        return AUDIO_PERFECT_PITCH_EXERCISE;
    }
    
    protected Class<EditableAudioPerfectPitchConfigValidator> getValidatorClass() {
        return EditableAudioPerfectPitchConfigValidator.class;
    }

    @Test
    public void noNormalizedNotesForPuzzleIsOk() {
        var puzzleConfigAggregate = createPuzzleConfig();
        // just ensuring there's no normalized notes after creating
        assertEquals(0, puzzleConfigAggregate.getNormalizedNotesForPuzzle().length);

        assertNoValidationErrors(puzzleConfigAggregate);
    }

    @Test
    // TODO if inputMode is not `KEYBOARD_AS_PIANO`
    public void someNormalizedNotesForPuzzleIsOk() {
        var puzzleConfigAggregate = createPuzzleConfig();
        // TODO use PianoKeyboardTest.SOME_PIANO_KEYS somehow, probably via extra helper
        var somePianoKeys = new PianoKeyNumber[] { FIRST_NOTE_NUMBER, LAST_NOTE_NUMBER };
        puzzleConfigAggregate.updateProperty(NORMALIZED_NOTES_FOR_PUZZLE_PROP, somePianoKeys);

        assertNoValidationErrors(puzzleConfigAggregate);
    }

    @Test
    public void noNormalizedRootNoteIsOk() {
        var puzzleConfigAggregate = createPuzzleConfig();
        assertNull(puzzleConfigAggregate.getNormalizedRootNote());

        assertNoValidationErrors(puzzleConfigAggregate);
    }

    @Test
    // TODO if inputMode is not `KEYBOARD_AS_PIANO`
    public void someNormalizedRootNoteIsOk() {
        var puzzleConfigAggregate = createPuzzleConfig();

        puzzleConfigAggregate.updateProperty(NORMALIZED_ROOT_NOTE_PROP, FIRST_NOTE_NUMBER);

        assertNoValidationErrors(puzzleConfigAggregate);
    }

    // TODO validation for `KEYBOARD_AS_PIANO` mode
}

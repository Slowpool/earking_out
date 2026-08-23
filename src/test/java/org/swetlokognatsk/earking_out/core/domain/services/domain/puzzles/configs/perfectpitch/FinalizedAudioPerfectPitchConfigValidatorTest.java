package org.swetlokognatsk.earking_out.core.domain.services.domain.puzzles.configs.perfectpitch;

import static org.junit.Assert.assertEquals;
import static org.swetlokognatsk.earking_out.core.domain.model.exercises.ExercisesFactory.AUDIO_PERFECT_PITCH_EXERCISE;
import static org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber.FIRST_NOTE_NUMBER;
import static org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber.LAST_NOTE_NUMBER;
import static org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.PerfectPitchConfigAggregate.NORMALIZED_NOTES_FOR_PUZZLE_PROP;
import static org.swetlokognatsk.earking_out.core.domain.services.domain.puzzles.configs.TestPuzzleConfigValidatorHelper.assertThesePropertiesLedToErrors;

import java.util.List;

import org.junit.Test;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.factories.perfectpitch.AudioPerfectPitchConfigAggregatesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.AudioPerfectPitchConfigAggregate;
import org.swetlokognatsk.earking_out.core.domain.services.domain.puzzles.configs.PuzzleConfigValidatorTest;

public final class FinalizedAudioPerfectPitchConfigValidatorTest extends PuzzleConfigValidatorTest<AudioPerfectPitchExercise, AudioPerfectPitchConfigAggregate, AudioPerfectPitchConfigAggregatesFactory, FinalizedAudioPerfectPitchConfigValidator> {

    protected AudioPerfectPitchExercise getExercise() {
        return AUDIO_PERFECT_PITCH_EXERCISE;
    }
    
    protected Class<FinalizedAudioPerfectPitchConfigValidator> getValidatorClass() {
        return FinalizedAudioPerfectPitchConfigValidator.class;
    }

    @Test
    public void noNormalizedNotesForPuzzleFails() {
        var puzzleConfigAggregate = createPuzzleConfig();
        assertEquals(0, puzzleConfigAggregate.getNormalizedNotesForPuzzle().length);

        assertThesePropertiesLedToErrors(puzzleConfigAggregate, List.of(NORMALIZED_NOTES_FOR_PUZZLE_PROP));
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
}

package org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.perfectpitch;

import static org.junit.Assert.*;
import static org.swetlokognatsk.earking_out.core.domain.model.exercises.ExercisesFactory.AUDIO_PERFECT_PITCH_EXERCISE;
import org.junit.Test;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.PerfectPitchInputMode;
import static org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber.*;

public class AudioPerfectPitchConfigDTOTest {

    // TODO DRY violation
    protected static final PianoKeyNumber[] SOME_PIANO_KEYS = new PianoKeyNumber[] { FIRST_NOTE_NUMBER, LAST_NOTE_NUMBER };

    // TODO make it more readable (for fun)
    @Test
    public void positiveEquals() {
        var firstConfig = new AudioPerfectPitchConfigDTO(AUDIO_PERFECT_PITCH_EXERCISE, 0, true, SOME_PIANO_KEYS, FIRST_NOTE_NUMBER, PerfectPitchInputMode.NOTES_AS_CHARACTERS, true);
        var secondConfig = new AudioPerfectPitchConfigDTO(firstConfig.exercise, firstConfig.targetNumberOfPuzzles, firstConfig.statsRecording, new PianoKeyNumber[] { FIRST_NOTE_NUMBER, LAST_NOTE_NUMBER }, firstConfig.normalizedRootNote, firstConfig.inputMode, firstConfig.soundlessGuessingPiano);

        assertTrue(firstConfig.equals(secondConfig));
    }

    @Test
    public void negativeEquals() {
        var firstConfig = new AudioPerfectPitchConfigDTO(AUDIO_PERFECT_PITCH_EXERCISE, 0, true, SOME_PIANO_KEYS, FIRST_NOTE_NUMBER, PerfectPitchInputMode.NOTES_AS_CHARACTERS, true);
        var secondConfig = new AudioPerfectPitchConfigDTO(firstConfig.exercise, 1, firstConfig.statsRecording, firstConfig.normalizedNotesForPuzzle, firstConfig.normalizedRootNote, firstConfig.inputMode, firstConfig.soundlessGuessingPiano);

        assertFalse(firstConfig.equals(secondConfig));
    }

    @Test
    public void theSameHashCodes() {
        var firstConfig = new AudioPerfectPitchConfigDTO(AUDIO_PERFECT_PITCH_EXERCISE, 0, true, SOME_PIANO_KEYS, FIRST_NOTE_NUMBER, PerfectPitchInputMode.NOTES_AS_CHARACTERS, true);
        var secondConfig = new AudioPerfectPitchConfigDTO(firstConfig.exercise, firstConfig.targetNumberOfPuzzles, firstConfig.statsRecording, firstConfig.normalizedNotesForPuzzle, firstConfig.normalizedRootNote, firstConfig.inputMode, firstConfig.soundlessGuessingPiano);

        assertEquals(firstConfig.hashCode(), secondConfig.hashCode());
    }

    @Test
    @Deprecated
    public void differentHashCodes() {
        // var firstConfig = new AudioPerfectPitchConfigDTO(AUDIO_PERFECT_PITCH_EXERCISE, 0, true, SOME_PIANO_KEYS, FIRST_NOTE_NUMBER, PerfectPitchInputMode.NOTES_AS_CHARACTERS, true);
        // var secondConfig = new AudioPerfectPitchConfigDTO(firstConfig.exercise, 1, firstConfig.statsRecording, firstConfig.normalizedNotesForPuzzle, firstConfig.normalizedRootNote, firstConfig.inputMode, firstConfig.soundlessGuessingPiano);

        // // TODO theoretically they can be the same. that's why i marked this @Deprecated
        // assertNotEquals(firstConfig.hashCode(), secondConfig.hashCode());
    }
}

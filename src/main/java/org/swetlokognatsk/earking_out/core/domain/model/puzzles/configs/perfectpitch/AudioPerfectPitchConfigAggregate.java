package org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.identity.UserId;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class AudioPerfectPitchConfigAggregate extends PerfectPitchConfigAggregate<AudioPerfectPitchExercise> {
    private static final long serialVersionUID = 1L;

    // TODO reconsider passing exercise. it's always `AUDIO_PERFECT_PITCH_EXERCISE`
    public AudioPerfectPitchConfigAggregate(final UserId userId, @JsonProperty ("id") final AudioPerfectPitchExercise exercise, final int targetNumberOfPuzzles, final boolean statsRecording, final PianoKeyNumber[] normalizedNotesForPuzzle, final PianoKeyNumber normalizedRootNote, final PerfectPitchInputMode inputMode, final boolean soundlessGuessingPiano) {
        super(userId, exercise, targetNumberOfPuzzles, statsRecording, normalizedNotesForPuzzle, normalizedRootNote, inputMode, soundlessGuessingPiano);
    }
}

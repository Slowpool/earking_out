package org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.perfectpitch;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.PerfectPitchInputMode;

// TODO DOMAIN IS A WRONG PLACE FOR DTOS!
public final class AudioPerfectPitchConfigDTO extends PerfectPitchConfigDTO<AudioPerfectPitchExercise> {

    public AudioPerfectPitchConfigDTO(final AudioPerfectPitchExercise exercise, final int targetNumberOfPuzzles, final boolean statsRecording, PianoKeyNumber[] normalizedNotesForPuzzle, final PianoKeyNumber normalizedRootNote, final PerfectPitchInputMode inputMode, final boolean soundlessGuessingPiano) {
        super(exercise, targetNumberOfPuzzles, statsRecording, normalizedNotesForPuzzle, normalizedRootNote, inputMode, soundlessGuessingPiano);
    }

    public int hashCode() {
        return exercise.hashCode() + targetNumberOfPuzzles + (statsRecording ? 1 : 0) + normalizedNotesForPuzzle.hashCode() + normalizedRootNote.hashCode() + inputMode.hashCode() + (soundlessGuessingPiano ? 1 : 0);
    }

    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AudioPerfectPitchConfigDTO)) {
            return false;
        }
        var other = (AudioPerfectPitchConfigDTO) obj;
        return exercise.equals(other.exercise)
                && targetNumberOfPuzzles == other.targetNumberOfPuzzles
                && statsRecording == other.statsRecording
                && PianoKeyNumber.equal(normalizedNotesForPuzzle, other.normalizedNotesForPuzzle)
                && normalizedRootNote.equals(other.normalizedRootNote)
                && inputMode.equals(other.inputMode)
                && soundlessGuessingPiano == other.soundlessGuessingPiano;
    }
}

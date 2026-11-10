package org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.perfectpitch;

import java.util.Arrays;
import java.util.Objects;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.PerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.PerfectPitchInputMode;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.PuzzleConfigDTO;

public abstract class PerfectPitchConfigDTO<E extends PerfectPitchExercise> extends PuzzleConfigDTO<E> {
    public final PianoKeyNumber[] normalizedNotesForPuzzle;
    public final PianoKeyNumber normalizedRootNote;
    public final PerfectPitchInputMode inputMode;
    public final boolean soundlessGuessingPiano;

    public PerfectPitchConfigDTO(final E exercise, final int targetNumberOfPuzzles, final boolean statsRecording, PianoKeyNumber[] normalizedNotesForPuzzle, final PianoKeyNumber normalizedRootNote, final PerfectPitchInputMode inputMode, final boolean soundlessGuessingPiano) {
        super(exercise, targetNumberOfPuzzles, statsRecording);
        this.normalizedNotesForPuzzle = normalizedNotesForPuzzle;
        this.normalizedRootNote = normalizedRootNote;
        this.inputMode = inputMode;
        this.soundlessGuessingPiano = soundlessGuessingPiano;
    }

    public int hashCode() {
        return exercise.hashCode()
                + targetNumberOfPuzzles
                + (statsRecording ? 1 : 0)
                + normalizedNotesForPuzzle.hashCode()
                + normalizedRootNote.hashCode()
                + inputMode.hashCode()
                + (soundlessGuessingPiano ? 2 : 0);
    }

    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof PerfectPitchConfigDTO)) {
            return false;
        }
        var other = (PerfectPitchConfigDTO<?>) obj;
        return exercise.equals(other.exercise)
                && targetNumberOfPuzzles == other.targetNumberOfPuzzles
                && statsRecording == other.statsRecording
                && Arrays.equals(normalizedNotesForPuzzle, other.normalizedNotesForPuzzle)
                && Objects.equals(normalizedRootNote, other.normalizedRootNote)
                && inputMode.equals(other.inputMode)
                && soundlessGuessingPiano == other.soundlessGuessingPiano;
    }
}

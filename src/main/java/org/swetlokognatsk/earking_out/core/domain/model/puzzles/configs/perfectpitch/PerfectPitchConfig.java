package org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch;

import java.util.ArrayList;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.ExerciseNames;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.PerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfig;

public abstract class PerfectPitchConfig<E extends PerfectPitchExercise> extends PuzzleConfig<E> {
    public static final String NORMALIZED_NOTES_FOR_PUZZLE_PROP = "normalizedNotesForPuzzle";

    public final byte[] normalizedNotesForPuzzle;
    public final Byte normalizedRootNote;
    public final PerfectPitchInputMode inputMode;

    public PerfectPitchConfig(final int targetNumberOfPuzzles, final boolean statsRecording, final byte[] normalizedNotesForPuzzle, final Byte normalizedRootNote, final PerfectPitchInputMode inputMode) {
        super(targetNumberOfPuzzles, statsRecording);

        this.normalizedNotesForPuzzle = normalizedNotesForPuzzle;
        this.normalizedRootNote = normalizedRootNote;
        this.inputMode = inputMode;
    }

    protected final ExerciseNames getExerciseName() {
        return ExerciseNames.PERFECT_PITCH;
    }

    public String[] getErrors() {
        var errors = new ArrayList<String>();
        // TODO apply tdd for that first
        // if (targetNumberOfPuzzles <= 0) {
        //     errors.add("Number of puzzles cannot be negative or zero");
        // }
        // errors.addAll(validateSelectedNotes);
        return errors.toArray(new String[] {});

    }
}

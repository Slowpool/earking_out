package org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch;

import java.util.ArrayList;
import java.util.List;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.ExerciseNames;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.PerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.music.NoteWithAccidental;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfig;

public abstract class PerfectPitchConfig<E extends PerfectPitchExercise> extends PuzzleConfig<E> {
    public final NoteWithAccidental[] notesForPuzzle;
    public final NoteWithAccidental rootNote;
    public final PerfectPitchInputMode inputMode;

    public PerfectPitchConfig(final int targetNumberOfPuzzles, final boolean statsRecording, final NoteWithAccidental[] notesForPuzzle, final NoteWithAccidental rootNote, final PerfectPitchInputMode inputMode) {
        super(targetNumberOfPuzzles, statsRecording);

        this.notesForPuzzle = notesForPuzzle;
        this.rootNote = rootNote;
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

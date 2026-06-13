package org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch;

import java.util.ArrayList;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.ExerciseNames;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.PerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfigAggregate;
import org.swetlokognatsk.earking_out.core.ports.piano.PianoKeyboardRepository;

public abstract class PerfectPitchConfigAggregate<E extends PerfectPitchExercise> extends PuzzleConfigAggregate<E> {
    public static final String NORMALIZED_NOTES_FOR_PUZZLE_PROP = "normalizedNotesForPuzzle";
    public static final String NORMALIZED_ROOT_NOTE_PROP = "normalizedRootNote";
    public static final String INPUT_MODE_PROP = "inputMode";

    // TODO replace getters with read-only types
    protected byte[] normalizedNotesForPuzzle;
    protected Byte normalizedRootNote;
    protected PerfectPitchInputMode inputMode;

    public byte[] getNormalizedNotesForPuzzle() {
        return normalizedNotesForPuzzle;
    }

    public Byte getNormalizedRootNote() {
        return normalizedRootNote;
    }

    public PerfectPitchInputMode getInputMode() {
        return inputMode;
    }

    public PerfectPitchConfigAggregate(final int targetNumberOfPuzzles, final boolean statsRecording, final byte[] normalizedNotesForPuzzle, final Byte normalizedRootNote, final PerfectPitchInputMode inputMode, final PianoKeyboardRepository pianoKeyboardRepository) {
        super(targetNumberOfPuzzles, statsRecording, pianoKeyboardRepository);

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

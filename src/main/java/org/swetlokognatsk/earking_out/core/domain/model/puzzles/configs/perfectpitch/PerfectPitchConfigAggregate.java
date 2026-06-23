package org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch;

import java.util.ArrayList;
import java.util.Arrays;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.PerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfigAggregate;

public abstract class PerfectPitchConfigAggregate<E extends PerfectPitchExercise> extends PuzzleConfigAggregate<E> {
    public static final String NORMALIZED_NOTES_FOR_PUZZLE_PROP = "normalizedNotesForPuzzle";
    public static final String NORMALIZED_ROOT_NOTE_PROP = "normalizedRootNote";
    public static final String INPUT_MODE_PROP = "inputMode";

    // TODO replace getters with read-only types
    protected PianoKeyNumber[] normalizedNotesForPuzzle;
    protected PianoKeyNumber normalizedRootNote;
    protected PerfectPitchInputMode inputMode;

    public PianoKeyNumber[] getNormalizedNotesForPuzzle() {
        return normalizedNotesForPuzzle;
    }

    public PianoKeyNumber getNormalizedRootNote() {
        return normalizedRootNote;
    }

    public PerfectPitchInputMode getInputMode() {
        return inputMode;
    }

    public PerfectPitchConfigAggregate(final E exercise, final int targetNumberOfPuzzles, final boolean statsRecording, final PianoKeyNumber[] normalizedNotesForPuzzle, final PianoKeyNumber normalizedRootNote, final PerfectPitchInputMode inputMode, final PianoKeyboardAggregate[] pianoKeyboardAggregates) {
        super(exercise, targetNumberOfPuzzles, statsRecording, pianoKeyboardAggregates);

        this.normalizedNotesForPuzzle = normalizedNotesForPuzzle;
        this.normalizedRootNote = normalizedRootNote;
        this.inputMode = inputMode;
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

    @Override
    protected void updatePropertyViaPianoKeyboard(final String propertyName, final PianoKeyboardAggregate pianoKeyboard) {
        switch (propertyName) {
        case PerfectPitchConfigAggregate.NORMALIZED_ROOT_NOTE_PROP:
            normalizedRootNote = pianoKeyboard.getSelectedKeyNumbers()[0];
            break;
        case PerfectPitchConfigAggregate.NORMALIZED_NOTES_FOR_PUZZLE_PROP:
            var selectedKeyNumbers = pianoKeyboard.getSelectedKeyNumbers();
            var selectedKeyNumbersCopy = Arrays.copyOf(selectedKeyNumbers, selectedKeyNumbers.length);
            normalizedNotesForPuzzle = selectedKeyNumbersCopy;
            break;
        default:
            throw new RuntimeException();
        }
    }

    protected void updateConfigSpecificProperty(final String propertyName, final Object propertyValue) {
        switch (propertyName) {
        // TODO how 'bout reflection?
        case NORMALIZED_ROOT_NOTE_PROP: {
            normalizedRootNote = (PianoKeyNumber) propertyValue;
            break;
        }
        case NORMALIZED_NOTES_FOR_PUZZLE_PROP: {
            normalizedNotesForPuzzle = (PianoKeyNumber[]) propertyValue;
            break;
        }
        default:
            throw new IllegalArgumentException("unknown puzzle config property: " + propertyName);
        }
    }
}

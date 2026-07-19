package org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.PerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfigAggregate;

public abstract class PerfectPitchConfigAggregate<E extends PerfectPitchExercise> extends PuzzleConfigAggregate<E> {
    private static final long serialVersionUID = 1L;

    public static final String NORMALIZED_NOTES_FOR_PUZZLE_PROP = "normalizedNotesForPuzzle";
    public static final String NORMALIZED_ROOT_NOTE_PROP = "normalizedRootNote";
    public static final String INPUT_MODE_PROP = "inputMode";
    public static final String SOUNDLESS_GUESSING_PIANO_PROP = "soundlessGuessingPiano";

    protected PianoKeyNumber[] normalizedNotesForPuzzle;
    protected PianoKeyNumber normalizedRootNote;
    protected PerfectPitchInputMode inputMode;
    protected boolean soundlessGuessingPiano;

    public PianoKeyNumber[] getNormalizedNotesForPuzzle() {
        return Arrays.copyOf(normalizedNotesForPuzzle, normalizedNotesForPuzzle.length);
    }

    protected void setNormalizedNotesForPuzzle(final PianoKeyNumber[] normalizedNotesForPuzzle) {
        this.normalizedNotesForPuzzle = normalizedNotesForPuzzle;
    }

    public PianoKeyNumber getNormalizedRootNote() {
        return normalizedRootNote;
    }

    protected void setNormalizedRootNote(final PianoKeyNumber normalizedRootNote) {
        this.normalizedRootNote = normalizedRootNote;
    }

    public PerfectPitchInputMode getInputMode() {
        return inputMode;
    }

    protected void setInputMode(final PerfectPitchInputMode inputMode) {
        this.inputMode = inputMode;
    }

    public boolean getSoundlessGuessingPiano() {
        return soundlessGuessingPiano;
    }

    protected void setSoundlessGuessingPiano(final boolean soundlessGuessingPiano) {
        this.soundlessGuessingPiano = soundlessGuessingPiano;
    }

    public PerfectPitchConfigAggregate(final E exercise, final int targetNumberOfPuzzles, final boolean statsRecording, final PianoKeyNumber[] normalizedNotesForPuzzle, final PianoKeyNumber normalizedRootNote, final PerfectPitchInputMode inputMode, final boolean soundlessGuessingPiano, final PianoKeyboardAggregate[] pianoKeyboardAggregates) {
        super(exercise, targetNumberOfPuzzles, statsRecording, pianoKeyboardAggregates);

        setNormalizedNotesForPuzzle(Objects.requireNonNull(normalizedNotesForPuzzle));
        setNormalizedRootNote(normalizedRootNote);
        setInputMode(Objects.requireNonNull(inputMode));
        setSoundlessGuessingPiano(soundlessGuessingPiano);
    }

    public List<String> getErrors() {
        var errors = new ArrayList<String>();
        // TODO move it to PuzzleConfig, then create getChildErrors() via polymorphism
        // TODO apply tdd for that first
        if (targetNumberOfPuzzles <= 0) {
            errors.add("targetNumberOfPuzzles cannot be negative or zero");
        }
        return errors;
    }

    @Override
    protected void updatePropertyViaPianoKeyboard(final String propertyName, final PianoKeyboardAggregate pianoKeyboard) {
        switch (propertyName) {
        case NORMALIZED_ROOT_NOTE_PROP:
            var normalizedRootNote = pianoKeyboard.getSelectedKeyNumbers()[0];
            updateConfigSpecificProperty(NORMALIZED_ROOT_NOTE_PROP, normalizedRootNote);
            break;
        case NORMALIZED_NOTES_FOR_PUZZLE_PROP:
            var selectedKeyNumbers = pianoKeyboard.getSelectedKeyNumbers();
            updateConfigSpecificProperty(NORMALIZED_NOTES_FOR_PUZZLE_PROP, selectedKeyNumbers);
            break;
        default:
            throw new RuntimeException();
        }
    }

    protected void updateConfigSpecificProperty(final String propertyName, final Object propertyValue) {
        switch (propertyName) {
        case NORMALIZED_ROOT_NOTE_PROP:
            var normalizedRootNote = (PianoKeyNumber) propertyValue;
            setNormalizedRootNote(normalizedRootNote);
            break;
        case NORMALIZED_NOTES_FOR_PUZZLE_PROP:
            var normalizedNotesForPuzzle = (PianoKeyNumber[]) propertyValue;
            var normalizedNotesForPuzzleCopy = Arrays.copyOf(normalizedNotesForPuzzle, normalizedNotesForPuzzle.length);
            setNormalizedNotesForPuzzle(normalizedNotesForPuzzleCopy);
            break;
        case INPUT_MODE_PROP:
            var inputMode = (PerfectPitchInputMode) propertyValue;
            setInputMode(inputMode);
            break;
        case SOUNDLESS_GUESSING_PIANO_PROP:
            setSoundlessGuessingPiano((boolean) propertyValue);
            break;
        default:
            throw new IllegalArgumentException("unknown puzzle config property: " + propertyName);
        }
    }
}

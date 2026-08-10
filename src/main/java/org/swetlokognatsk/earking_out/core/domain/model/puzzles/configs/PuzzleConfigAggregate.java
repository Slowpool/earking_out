package org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs;

import org.swetlokognatsk.earking_out.core.domain.model.base.AggregateRoot;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfigAggregate;

// TODO clear the pianoKeyboardAggregates
public abstract class PuzzleConfigAggregate<E extends Exercise> extends AggregateRoot<E> {
    private static final long serialVersionUID = 1L;

    public static final String TARGET_NUMBER_OF_PUZZLES_PROP = "targetNumberOfPuzzles";
    public static final String STATS_RECORDING_PROP = "statsRecording";

    protected int targetNumberOfPuzzles;
    protected boolean statsRecording;

    protected abstract void updateConfigSpecificProperty(final String propertyName, final Object propertyValue);

    public int getTargetNumberOfPuzzles() {
        return targetNumberOfPuzzles;
    }

    protected final void setTargetNumberOfPuzzles(final int targetNumberOfPuzzles) {
        this.targetNumberOfPuzzles = targetNumberOfPuzzles;
    }

    public boolean getStatsRecording() {
        return statsRecording;
    }

    protected final void setStatsRecording(final boolean statsRecording) {
        this.statsRecording = statsRecording;
    }

    public PuzzleConfigAggregate(final E exercise, final int targetNumberOfPuzzles, final boolean statsRecording) {
        super(exercise);

        setTargetNumberOfPuzzles(targetNumberOfPuzzles);
        setStatsRecording(statsRecording);
        // TODO ddd violation (is it???): aggregate root must never contain links to other aggregates
        // this.pianoKeyboardAggregates = createPianoKeyboardsMap(pianoKeyboardAggregates);
    }

    // public final void updateViaPianoKeyPressing(final PianoKeyboardId pianoKeyboardId, final PianoKeyNumber keyNumber) {
    //     var pianoKeyboard = getPianoKeyboardAggregate(pianoKeyboardId);
    //     pianoKeyboard.pressKey(keyNumber);
    //     var propertyName = getPropertyName(pianoKeyboardId);
    //     // TODO add validation for root note on-change (not on exercise starting, though add the whole config validation should be added before exercise starting)
    //     updatePropertyViaPianoKeyboard(propertyName, pianoKeyboard);
    // }

    // // abstract-though-not-mandatory-to-implement-like behavior
    // protected void updatePropertyViaPianoKeyboard(final String propertyName, final PianoKeyboardAggregate pianoKeyboard) {
    //     throw new IllegalStateException("updatePropertyViaPianoKeyboard is not implemented");
    // }

    // protected final PianoKeyboardAggregate getPianoKeyboardAggregate(final PianoKeyboardId pianoKeyboardId) {
    //     var pianoKeyboard = pianoKeyboardAggregates.get(pianoKeyboardId);
    //     if (pianoKeyboard == null) {
    //         throw new IllegalArgumentException("pianoKeyboard is not found. pianoKeyboardId: " + pianoKeyboardId);
    //     }
    //     return pianoKeyboard;
    // }

    // public final PianoKeyboardDTO getPianoKeyboard(final PianoKeyboardId pianoKeyboardId) {
    //     var pianoKeyboardAggregate = getPianoKeyboardAggregate(pianoKeyboardId);
    //     var pianoKeyboardAggregateDto = PianoKeyboardDtoAssembler.assemble(pianoKeyboardAggregate);
    //     return pianoKeyboardAggregateDto;
    // }

    // protected String getPropertyName(final PianoKeyboardId pianoKeyboardId) {
    //     return switch (pianoKeyboardId) {
    //     case AUDIO_PERFECT_PITCH_ROOT_NOTE_PICKER -> NORMALIZED_ROOT_NOTE_PROP;
    //     case AUDIO_PERFECT_PITCH_NOTES_PICKER -> NORMALIZED_NOTES_FOR_PUZZLE_PROP;
    //     default -> throw new IllegalArgumentException("this piano keyboard is not for config: " + pianoKeyboardId);
    //     };
    // }

    public final void updateProperty(final String propertyName, final Object propertyValue) {
        switch (propertyName) {
        case TARGET_NUMBER_OF_PUZZLES_PROP:
            setTargetNumberOfPuzzles((int) propertyValue);
            break;
        case STATS_RECORDING_PROP:
            setStatsRecording((boolean) propertyValue);
            break;
        default:
            updateConfigSpecificProperty(propertyName, propertyValue);
            break;
        }
    }

    // public final void releasePianoKey(final PianoKeyboardId pianoKeyboardId) {
    //     var pianoKeyboard = getPianoKeyboardAggregate(pianoKeyboardId);
    //     pianoKeyboard.releaseKey();
    // }
}

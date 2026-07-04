package org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs;

import org.swetlokognatsk.earking_out.core.domain.model.base.Aggregate;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardId;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfigAggregate;
import static org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.PerfectPitchConfigAggregate.*;
import java.util.Map;
import static org.swetlokognatsk.earking_out.core.domain.helpers.PianoKeyboardHelper.*;

// TODO review all aggregates: do they follow transactional consistency (in-memory)?
// TODO store it in database as json
public abstract class PuzzleConfigAggregate<E extends Exercise> extends Aggregate<E> {
    private static final long serialVersionUID = 1L;
    public static final String TARGET_NUMBER_OF_PUZZLES_PROP = "targetNumberOfPuzzles";
    public static final String STATS_RECORDING_PROP = "statsRecording";

    // TODO make getters read-only
    protected int targetNumberOfPuzzles;
    protected boolean statsRecording;

    // TODO dirty workaround
    public final Map<PianoKeyboardId, PianoKeyboardAggregate> pianoKeyboardAggregates;

    // TODO how this pattern is called?
    protected abstract void updateConfigSpecificProperty(final String propertyName, final Object propertyValue);

    public int getTargetNumberOfPuzzles() {
        return targetNumberOfPuzzles;
    }

    public boolean getStatsRecording() {
        return statsRecording;
    }

    public PuzzleConfigAggregate(final E exercise, final int targetNumberOfPuzzles, final boolean statsRecording, final PianoKeyboardAggregate[] pianoKeyboardAggregates) {
        super(exercise);

        this.targetNumberOfPuzzles = targetNumberOfPuzzles;
        this.statsRecording = statsRecording;
        // TODO ddd violation (is it???): aggregate root must never contain links to other aggregates
        this.pianoKeyboardAggregates = createPianoKeyboardsMap(pianoKeyboardAggregates);
    }

    // TODO delete
    // private final Map<PianoKeyboardId, PianoKeyboardAggregate> createPianoKeyboardsMap(final PianoKeyboardAggregate[] pianoKeyboardAggregates) {
    //     Map<PianoKeyboardId, PianoKeyboardAggregate> pianoKeyboardsMap = new HashMap<>();
    //     for (var pianoKeyboardAggregate : pianoKeyboardAggregates) {
    //         pianoKeyboardsMap.put(pianoKeyboardAggregate.getId(), pianoKeyboardAggregate);
    //     }
    //     return pianoKeyboardsMap;
    // }

    public final void updateViaPianoKeyPressing(final PianoKeyboardId pianoKeyboardId, final PianoKeyNumber keyNumber) {
        var pianoKeyboard = getPianoKeyboardAggregate(pianoKeyboardId);
        pianoKeyboard.pressKey(keyNumber);
        // TODO if further code fails, the aggregate state would be inconsistent
        var propertyName = getPropertyName(pianoKeyboardId);
        // TODO where is validation?
        updatePropertyViaPianoKeyboard(propertyName, pianoKeyboard);
    }

    // abstract-though-not-mandatory-to-implement-like behavior
    protected void updatePropertyViaPianoKeyboard(final String propertyName, final PianoKeyboardAggregate pianoKeyboard) {
        throw new IllegalStateException("updatePropertyViaPianoKeyboard is not implemented");
    }

    public final PianoKeyboardAggregate getPianoKeyboardAggregate(final PianoKeyboardId pianoKeyboardId) {
        var pianoKeyboard = pianoKeyboardAggregates.get(pianoKeyboardId);
        if (pianoKeyboard == null) {
            throw new IllegalArgumentException("pianoKeyboard is not found. pianoKeyboardId: " + pianoKeyboardId);
        }
        return pianoKeyboard;
    }

    // TODO return read-only object
    public final PianoKeyboardAggregate getPianoKeyboard(final PianoKeyboardId pianoKeyboardId) {
        return getPianoKeyboardAggregate(pianoKeyboardId);
    }

    // TODO it must not be here
    protected String getPropertyName(final PianoKeyboardId pianoKeyboardId) {
        return switch (pianoKeyboardId) {
        case ROOT_NOTE_PICKER -> NORMALIZED_ROOT_NOTE_PROP;
        case PERFECT_PITCH_NOTES_PICKER -> NORMALIZED_NOTES_FOR_PUZZLE_PROP;
        default -> throw new IllegalArgumentException("this piano keyboard is not for config: " + pianoKeyboardId);
        };
    }

    public final void updateProperty(final String propertyName, final Object propertyValue) {
        switch (propertyName) {
        case TARGET_NUMBER_OF_PUZZLES_PROP:
            targetNumberOfPuzzles = (int) propertyValue;
            break;
        case STATS_RECORDING_PROP:
            statsRecording = (boolean) propertyValue;
            break;
        default:
            updateConfigSpecificProperty(propertyName, propertyValue);
            break;
        }
    }

    public void releasePianoKey(final PianoKeyboardId pianoKeyboardId) {
        var pianoKeyboard = getPianoKeyboardAggregate(pianoKeyboardId);
        pianoKeyboard.releaseKey();
    }
}

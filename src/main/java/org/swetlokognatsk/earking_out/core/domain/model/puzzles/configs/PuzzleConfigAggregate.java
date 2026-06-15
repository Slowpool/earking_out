package org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs;

import org.swetlokognatsk.earking_out.core.domain.model.base.Aggregate;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.VisualPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardId;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfigAggregate;
import org.swetlokognatsk.earking_out.core.ports.piano.PianoKeyboardRepository;
import static org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.PerfectPitchConfigAggregate.*;

// TODO review all aggregates: do they follow transactional consistency (in-memory)?
// TODO store it in database as json
// TODO it must extend Model
public abstract class PuzzleConfigAggregate<E extends Exercise> extends Aggregate<E> {
    public static final String TARGET_NUMBER_OF_PUZZLES_PROP = "targetNumberOfPuzzles";
    public static final String STATS_RECORDING_PROP = "statsRecording";

    // TODO make getters read-only
    protected final PianoKeyboardId[] pianoKeyboardIds;
    protected final PianoKeyboardRepository pianoKeyboardRepository;
    protected int targetNumberOfPuzzles;
    protected boolean statsRecording;

    protected abstract void updateConfigSpecificProperty(final String propertyName, final Object propertyValue);

    public int getTargetNumberOfPuzzles() {
        return targetNumberOfPuzzles;
    }

    public boolean getStatsRecording() {
        return statsRecording;
    }

    public PuzzleConfigAggregate(final E exercise, final int targetNumberOfPuzzles, final boolean statsRecording, final PianoKeyboardRepository pianoKeyboardRepository) {
        super(exercise);
        pianoKeyboardIds = findPianoKeyboardIds((Exercise) exercise);

        this.targetNumberOfPuzzles = targetNumberOfPuzzles;
        this.statsRecording = statsRecording;
        this.pianoKeyboardRepository = pianoKeyboardRepository;
    }

    // TODO it must not be here. probably inside PianoKeyboardId?
    protected static PianoKeyboardId[] findPianoKeyboardIds(final Exercise exercise) {
        var pianoKeyboardIds = switch (exercise) {
        case AudioPerfectPitchExercise e -> new PianoKeyboardId[] { PianoKeyboardId.PERFECT_PITCH_NOTES_PICKER, PianoKeyboardId.ROOT_NOTE_PICKER };
        case VisualPerfectPitchExercise e -> new PianoKeyboardId[] { PianoKeyboardId.PERFECT_PITCH_NOTES_PICKER, PianoKeyboardId.ROOT_NOTE_PICKER };
        default -> throw new IllegalArgumentException("unknown exercise: " + exercise);
        };
        return pianoKeyboardIds;
    }

    public void updateViaPianoKeyPressing(final PianoKeyboardId pianoKeyboardId, final byte keyNumber) {
        var pianoKeyboard = getPianoKeyboard(pianoKeyboardId);
        pianoKeyboard.pressKey(keyNumber);

        var propertyName = getPropertyName(pianoKeyboardId);
        // TODO where is validation?
        var newNormalizedRootNote = pianoKeyboard.getSelectedKeyNumbers()[0];
        updateProperty(propertyName, newNormalizedRootNote);
    }

    // TODO there should be two methods: one that returns read-only object (public method) and another one that returns original pianoKeyboard (protected method)
    public PianoKeyboardAggregate getPianoKeyboard(final PianoKeyboardId pianoKeyboardId) {
        var pianoKeyboard = pianoKeyboardRepository.get(pianoKeyboardId);
        return pianoKeyboard;
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

}

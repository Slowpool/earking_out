package org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs;

import java.util.HashMap;
import java.util.Map;
import org.swetlokognatsk.earking_out.core.domain.model.base.Aggregate;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.ExerciseNames;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.ExerciseTypes;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.ExercisesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardId;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfigAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.AudioPerfectPitchConfigAggregate;
import org.swetlokognatsk.earking_out.core.ports.piano.PianoKeyboardRepository;
import static org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.PerfectPitchConfigAggregate.*;

// TODO store it in database as json
// TODO it must extend Model
public abstract class PuzzleConfigAggregate<E extends Exercise> extends Aggregate {
    public static final String TARGET_NUMBER_OF_PUZZLES_PROP = "targetNumberOfPuzzles";
    public static final String STATS_RECORDING_PROP = "statsRecording";

    public final E exercise = assembleExercise();
    // TODO make getters read-only
    protected int targetNumberOfPuzzles;
    protected boolean statsRecording;

    public int getTargetNumberOfPuzzles() {
        return targetNumberOfPuzzles;
    }

    public boolean getStatsRecording() {
        return statsRecording;
    }

    protected final Map<PianoKeyboardId, PianoKeyboardAggregate> pianoKeyboardAggregates = new HashMap<>();
    protected final PianoKeyboardRepository pianoKeyboardRepository;

    protected abstract ExerciseNames getExerciseName();

    protected abstract ExerciseTypes getExerciseType();

    public final String getId() {
        return exercise.toString();
    }

    private final E assembleExercise() {
        return (E) ExercisesFactory.create(getExerciseName(), getExerciseType());
    }

    public PuzzleConfigAggregate(final int targetNumberOfPuzzles, final boolean statsRecording, final PianoKeyboardRepository pianoKeyboardRepository) {
        this.targetNumberOfPuzzles = targetNumberOfPuzzles;
        this.statsRecording = statsRecording;
        this.pianoKeyboardRepository = pianoKeyboardRepository;
    }

    public void updateViaPianoKeyPressing(final PianoKeyboardId pianoKeyboardId, final byte keyNumber) {
        var pianoKeyboard = getPianoKeyboard(pianoKeyboardId);
        pianoKeyboard.pressKey(keyNumber);

        var propertyName = getPropertyName(pianoKeyboardId);
        updateProperty(propertyName, pianoKeyboard.getSelectedKeyNumbers());
    }

    // minor optimization. using create-if-not-exists strategy to avoid redundant writes of unchanged pianoKeyboards on `repository.save(this)`
    protected PianoKeyboardAggregate getPianoKeyboard(final PianoKeyboardId pianoKeyboardId) {
        if (!pianoKeyboardAggregates.containsKey(pianoKeyboardId)) {
            var pianoKeyboard = pianoKeyboardRepository.get(pianoKeyboardId);
            pianoKeyboardAggregates.put(pianoKeyboardId, pianoKeyboard);
        }
        return pianoKeyboardAggregates.get(pianoKeyboardId);
    }

    protected String getPropertyName(final PianoKeyboardId pianoKeyboardId) {
        return switch (pianoKeyboardId) {
        case ROOT_NOTE_PICKER -> NORMALIZED_ROOT_NOTE_PROP;
        case PERFECT_PITCH_NOTES_PICKER -> NORMALIZED_NOTES_FOR_PUZZLE_PROP;
        default -> throw new IllegalArgumentException("this piano keyboard is not for config: " + pianoKeyboardId);
        };
    }

    // TODO IT SHOULD BE DELEGATED TO POLYMORPHIC DESCENDANTS
    public void updateProperty(final String propertyName, final Object propertyValue) {
        switch (propertyName) {
        // TODO how 'bout reflection?
        case NORMALIZED_ROOT_NOTE_PROP: {
            var puzzleConfig = (AudioPerfectPitchConfigAggregate) this;
            puzzleConfig.normalizedNotesForPuzzle = (byte[]) propertyValue;
            break;
        }
        case NORMALIZED_NOTES_FOR_PUZZLE_PROP: {
            var puzzleConfig = (AudioPerfectPitchConfigAggregate) this;
            puzzleConfig.normalizedRootNote = ((byte[]) propertyValue)[0];
            // TODO is there any difference between `break; }` and `} break;` here?
            break;
        }
        default:
            throw new IllegalArgumentException("unknown puzzle config property: " + propertyName);
        }
    }

}

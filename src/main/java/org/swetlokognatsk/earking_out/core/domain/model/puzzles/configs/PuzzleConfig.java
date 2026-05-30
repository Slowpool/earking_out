package org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs;

import org.swetlokognatsk.earking_out.core.domain.model.Model;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.ExerciseNames;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.ExerciseTypes;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.ExercisesFactory;

// TODO store it in database as json
public abstract class PuzzleConfig<E extends Exercise> extends Model {
    public static final String TARGET_NUMBER_OF_PUZZLES_PROP = "targetNumberOfPuzzles";
    public static final String STATS_RECORDING_PROP = "statsRecording";

    public final E exercise = getExercise();
    public final int targetNumberOfPuzzles;
    public final boolean statsRecording;

    public PuzzleConfig(final int targetNumberOfPuzzles, final boolean statsRecording) {
        this.targetNumberOfPuzzles = targetNumberOfPuzzles;
        this.statsRecording = statsRecording;
    }

    protected E getExercise() {
        return (E) ExercisesFactory.create(getExerciseName(), getExerciseType());
    }

    protected abstract ExerciseNames getExerciseName();

    protected abstract ExerciseTypes getExerciseType();
}

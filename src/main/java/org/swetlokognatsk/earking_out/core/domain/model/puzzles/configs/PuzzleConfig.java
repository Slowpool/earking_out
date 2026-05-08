package org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.ExerciseNames;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.ExerciseTypes;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.ExercisesFactory;

// TODO store it in database as json
public abstract class PuzzleConfig<E extends Exercise> {
    public final E exercise = getExercise();

    protected E getExercise() {
        return (E)ExercisesFactory.create(getExerciseName(), getExerciseType());
    }

    protected abstract ExerciseNames getExerciseName();
    protected abstract ExerciseTypes getExerciseType();
}

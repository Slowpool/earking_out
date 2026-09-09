package org.swetlokognatsk.earking_out.core.domain.model.exercises;

import static org.junit.Assert.*;
import org.junit.*;

public final class ExerciseTest {

    @Test
    public void exerciseToString() {
        var exercise = ExercisesFactory.AUDIO_PERFECT_PITCH_EXERCISE;

        var stringedExercise = exercise.toString();

        var expectedStringedExercise = buildExpectedStringedExercise(exercise);
        assertEquals(expectedStringedExercise, stringedExercise);
    }

    @Test
    public void valueOfAudioPerfectPitchExercise() {
        var exercise = ExercisesFactory.AUDIO_PERFECT_PITCH_EXERCISE;

        var stringedExercise = buildExpectedStringedExercise(exercise);
        var parsedExercise = Exercise.valueOf(stringedExercise);

        assertEquals(exercise, parsedExercise);
    }

    private String buildExpectedStringedExercise(final Exercise exercise) {
        var stringedType = exercise.type.toString();
        var stringedName = exercise.name.toString();
        // yep, a bit awkward to do the same thing as in Exercise.toString(), but technically it makes sense because the implementation (interpolation vs concatenation) is different
        var expectedStringedExercise = stringedType + "/" + stringedName;
        return expectedStringedExercise;
    }
}

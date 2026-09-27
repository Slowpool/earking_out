package org.swetlokognatsk.earking_out.infrastructure.adapters.serialization;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.ExerciseNames;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.ExerciseTypes;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.ExercisesFactory;
import tools.jackson.databind.JsonNode;

public final class JacksonDeserializationHelper {

    private JacksonDeserializationHelper() {
    }

    public static Exercise deserializeExercise(final JsonNode node) {
        var exerciseNameToken = node.get("name").asString();
        var exerciseName = ExerciseNames.valueOf(exerciseNameToken);

        var exerciseTypeToken = node.get("type").asString();
        var exerciseType = ExerciseTypes.valueOf(exerciseTypeToken);

        return ExercisesFactory.create(exerciseName, exerciseType);
    }
}

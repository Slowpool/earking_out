package org.swetlokognatsk.earking_out.infrastructure.adapters.serialization;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.VisualPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.Puzzle;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.perfectpitch.AudioPerfectPitchConfigDTO;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.perfectpitch.VisualPerfectPitchConfigDTO;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.deser.std.StdDeserializer;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.AudioPerfectPitchConfigAggregate;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.PuzzleConfigDTO;
import tools.jackson.core.JsonParser;

abstract class ExerciseBasedPolymorphicDeserializer<T> extends StdDeserializer<T> {

    protected abstract Class<? extends T> getTargetType(final Exercise deserializedExercise);

    public ExerciseBasedPolymorphicDeserializer(final Class<?> vc) {
        super(vc);
    }

    @Override
    public T deserialize(final JsonParser parser, final DeserializationContext ctxt) {
        JsonNode tree = parser.readValueAsTree();

        var exerciseNode = tree.get("exercise");
        var deserializedExercise = JacksonDeserializationHelper.deserializeExercise(exerciseNode);
        var targetType = getTargetType(deserializedExercise);

        return ctxt.readTreeAsValue(tree, targetType);
    }
}

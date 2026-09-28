package org.swetlokognatsk.earking_out.infrastructure.adapters.serialization;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.AudioPerfectPitchConfigAggregate;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.PuzzleConfigDTO;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.perfectpitch.AudioPerfectPitchConfigDTO;
import tools.jackson.core.JsonParser;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.deser.std.StdDeserializer;

public class PuzzleConfigDTODeserializer extends StdDeserializer<PuzzleConfigDTO<?>> {

    public PuzzleConfigDTODeserializer(final Class<?> vc) {
        super(vc);
    }

    @Override
    public PuzzleConfigDTO<?> deserialize(final JsonParser parser, final DeserializationContext ctxt) {
        JsonNode tree = parser.readValueAsTree();

        var exerciseNode = tree.get("exercise");
        var deserializedExercise = JacksonDeserializationHelper.deserializeExercise(exerciseNode);

        var puzzleConfigDtoClass = switch (deserializedExercise) {
        case AudioPerfectPitchExercise appe -> AudioPerfectPitchConfigDTO.class;
        default -> throw new RuntimeException("unknown exercise: %s".formatted(deserializedExercise.toString()));
        };

        return ctxt.readTreeAsValue(tree, puzzleConfigDtoClass);
    }
}

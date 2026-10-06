package org.swetlokognatsk.earking_out.infrastructure.adapters.serialization;

import org.springframework.stereotype.Component;
import org.swetlokognatsk.earking_out.core.domain.events.DomainEvent;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.identity.UserId;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.Puzzle;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfigAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.AudioPerfectPitchConfigAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.solutions.Solution;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.PuzzleConfigDTO;
import org.swetlokognatsk.earking_out.core.ports.config.PuzzleConfigJsonSerializer;
import org.swetlokognatsk.earking_out.core.ports.events.DomainEventJsonSerializer;
import tools.jackson.databind.MapperFeature;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.databind.module.SimpleModule;

@Component
public final class JacksonJsonSerializer implements DomainEventJsonSerializer, PuzzleConfigJsonSerializer {

    private final ObjectMapper objectMapper;

    public JacksonJsonSerializer() {
        var puzzleConfigsModule = new SimpleModule()
                // TODO why to add class here?
                .addDeserializer(PuzzleConfigDTO.class, new PuzzleConfigDTODeserializer(PuzzleConfigDTO.class));

        var miscModule = new SimpleModule()
                .addSerializer(new PianoKeyNumberSerializer())
                .addSerializer(new UserIdSerializer())
                .addDeserializer(UserId.class, new UserIdDeserializer())
                .addDeserializer(Puzzle.class, new PuzzleDeserializer(Puzzle.class))
                .addDeserializer(Solution.class, new SolutionDeserializer(Solution.class));

        var jsonMapper = JsonMapper.builder()
                .configure(MapperFeature.PROPAGATE_TRANSIENT_MARKER, true)
                .addModule(puzzleConfigsModule)
                .addModule(miscModule)
                .build();
        this.objectMapper = jsonMapper;
    }

    public String serializeDomainEvent(final DomainEvent event) {
        return objectMapper.writeValueAsString(event);
    }

    public DomainEvent deserializeDomainEvent(final String serializedEvent, final Class<? extends DomainEvent> eventClass) {
        return objectMapper.readValue(serializedEvent, eventClass);
    }

    public String serializePuzzleConfig(final PuzzleConfigAggregate<?> puzzleConfig) {
        return objectMapper.writeValueAsString(puzzleConfig);
    }

    public <E extends Exercise> PuzzleConfigAggregate<?> deserializePuzzleConfig(final E exercise, final String serializedPuzzleConfig) {
        var puzzleConfig = switch (exercise) {
        case AudioPerfectPitchExercise appe -> objectMapper.readValue(serializedPuzzleConfig, AudioPerfectPitchConfigAggregate.class);
        default -> throw new IllegalArgumentException("Unknown exercise: %s".formatted(exercise.toString()));
        };
        if (!exercise.equals(puzzleConfig.getId())) {
            throw new IllegalArgumentException("exercise arg and actual exercise in puzzle config are different");
        }
        return puzzleConfig;
    }
}

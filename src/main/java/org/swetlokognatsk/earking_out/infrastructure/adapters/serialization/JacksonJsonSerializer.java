package org.swetlokognatsk.earking_out.infrastructure.adapters.serialization;

import org.swetlokognatsk.earking_out.core.domain.events.DomainEvent;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.ExerciseNames;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.ExerciseTypes;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.ExercisesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.Puzzle;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfigAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.AudioPerfectPitchConfigAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.PerfectPitchInputMode;
import org.swetlokognatsk.earking_out.core.domain.model.solutions.Solution;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.PuzzleConfigDTO;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.perfectpitch.AudioPerfectPitchConfigDTO;
import org.swetlokognatsk.earking_out.core.ports.config.PuzzleConfigJsonSerializer;
import org.swetlokognatsk.earking_out.core.ports.events.DomainEventJsonSerializer;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import tools.jackson.core.JsonGenerator;
import tools.jackson.core.JsonParser;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.JacksonModule;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.MapperFeature;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.PropertyNamingStrategy;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.SerializationFeature;
import tools.jackson.databind.deser.std.StdDeserializer;
import tools.jackson.databind.introspect.ClassIntrospector;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.databind.module.SimpleModule;
import tools.jackson.databind.ser.std.StdSerializer;

public final class JacksonJsonSerializer implements DomainEventJsonSerializer, PuzzleConfigJsonSerializer {

    private final ObjectMapper objectMapper;

    public JacksonJsonSerializer() {
        var puzzleConfigsModule = new SimpleModule()
                // TODO why to add class here?
                .addDeserializer(AudioPerfectPitchConfigAggregate.class, new AudioPerfectPitchConfigAggregateDeserializer())
                .addDeserializer(PuzzleConfigDTO.class, new PuzzleConfigDTODeserializer(PuzzleConfigDTO.class));

        var miscModule = new SimpleModule()
                .addSerializer(new PianoKeyNumberSerializer())
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

package org.swetlokognatsk.earking_out.inftrastructure.adapters.events;

import org.swetlokognatsk.earking_out.core.domain.events.DomainEvent;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfigAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.AudioPerfectPitchConfigAggregate;
import org.swetlokognatsk.earking_out.core.ports.config.PuzzleConfigJsonSerializer;
import org.swetlokognatsk.earking_out.core.ports.events.DomainEventJsonSerializer;
import tools.jackson.databind.ObjectMapper;

public final class JacksonJsonSerializer implements DomainEventJsonSerializer, PuzzleConfigJsonSerializer {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public JacksonJsonSerializer() {
    }

    public String serializeDomainEvent(final DomainEvent event) {
        return objectMapper.writeValueAsString(event);
    }

    public String serializePuzzleConfig(final PuzzleConfigAggregate<?> puzzleConfig) {
        return objectMapper.writeValueAsString(puzzleConfig);
    }

    public <E extends Exercise> PuzzleConfigAggregate<?> deserializePuzzleConfig(final E exercise, final String serializedPuzzleConfig) {
        // TODO WILD CRATCH
        return objectMapper.readValue(serializedPuzzleConfig, AudioPerfectPitchConfigAggregate.class);
    }

}

package org.swetlokognatsk.earking_out.core.domain.events.handlers;

import org.swetlokognatsk.earking_out.core.domain.events.DomainEvent;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.PuzzleConfigDTO;
import org.swetlokognatsk.earking_out.core.ports.config.PuzzleConfigRepository;
import org.swetlokognatsk.earking_out.core.ports.eventsourcing.EventStore;

abstract class LoggingToEventStoreHandler<DE extends DomainEvent> extends DomainEventHandler<DE> {
    protected final EventStore eventStore;
    protected final PuzzleConfigRepository puzzleConfigRepository;

    LoggingToEventStoreHandler(final EventStore eventStore, final PuzzleConfigRepository puzzleConfigRepository) {
        this.eventStore = eventStore;
        this.puzzleConfigRepository = puzzleConfigRepository;
    }

    protected final boolean loggingIsEnabled(final Exercise exercise) {
        var puzzleConfigDto = puzzleConfigRepository.getPuzzleConfigDTO(exercise);
        return loggingIsEnabled(puzzleConfigDto);
    }

    // optimization. to not fetch puzzleConfig from database each guess or piano key pressing, the same puzzle config dto is taken from memory. according to domain rules, config cannot be modified during guessing
    protected final boolean loggingIsEnabled(final PuzzleConfigDTO<?> puzzleConfigDto) {
        return puzzleConfigDto.statsRecording;
    }
}

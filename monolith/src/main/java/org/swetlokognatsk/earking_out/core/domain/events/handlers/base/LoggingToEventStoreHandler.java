package org.swetlokognatsk.earking_out.core.domain.events.handlers.base;

import org.swetlokognatsk.earking_out.core.domain.events.DomainEvent;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.identity.UserId;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.PuzzleConfigDTO;
import org.swetlokognatsk.earking_out.core.ports.config.PersistentPuzzleConfigRepository;
import org.swetlokognatsk.earking_out.core.ports.eventsourcing.EventStore;

// TODO make it working via tasks, in special thread or asynchronously
public abstract class LoggingToEventStoreHandler<DE extends DomainEvent> implements DomainEventHandler<DE> {
    protected final EventStore eventStore;
    private final PersistentPuzzleConfigRepository puzzleConfigRepository;

    public LoggingToEventStoreHandler(final EventStore eventStore, final PersistentPuzzleConfigRepository puzzleConfigRepository) {
        this.eventStore = eventStore;
        this.puzzleConfigRepository = puzzleConfigRepository;
    }

    // there's `loggingIsEnabled` overloading because depending on event type, sometimes they already have all required data. in other words, `loggingIsEnabled` overloading is for optimization
    protected final boolean loggingIsEnabled(final Exercise exercise) {
        var puzzleConfigDto = puzzleConfigRepository.getPuzzleConfigDTO(exercise);
        return loggingIsEnabled(puzzleConfigDto);
    }

    protected final boolean loggingIsEnabled(final Exercise exercise, final UserId userId) {
        var puzzleConfigDto = puzzleConfigRepository.getPuzzleConfigDTO(exercise, userId);
        return loggingIsEnabled(puzzleConfigDto);
    }

    protected final boolean loggingIsEnabled(final PuzzleConfigDTO<?> puzzleConfigDto) {
        return puzzleConfigDto.statsRecording;
    }

}

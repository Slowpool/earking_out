package org.swetlokognatsk.earking_out.core.domain.events.exercises;

import java.time.LocalDateTime;
import java.util.UUID;

import org.swetlokognatsk.earking_out.core.domain.events.DomainEvent;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfig;

public abstract class ExerciseStateChanged<E extends Exercise, PC extends PuzzleConfig<E>> extends DomainEvent {
    public final UUID sessionId;

    public ExerciseStateChanged(final LocalDateTime timestamp, final UUID sessionId) {
        super(timestamp);

        this.sessionId = sessionId;
    }
}

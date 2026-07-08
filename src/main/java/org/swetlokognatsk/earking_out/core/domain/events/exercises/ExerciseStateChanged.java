package org.swetlokognatsk.earking_out.core.domain.events.exercises;

import java.time.LocalDateTime;

import org.swetlokognatsk.earking_out.core.domain.events.DomainEvent;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfigAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionId;

public abstract class ExerciseStateChanged<E extends Exercise, PC extends PuzzleConfigAggregate<E>> extends DomainEvent {
    public final SessionId sessionId;

    public ExerciseStateChanged(final LocalDateTime timestamp, final SessionId sessionId) {
        super(timestamp);

        this.sessionId = sessionId;
    }
}

package org.swetlokognatsk.earking_out.core.domain.events.exercises;

import java.time.LocalDateTime;
import org.swetlokognatsk.earking_out.core.domain.events.DomainEvent;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.identity.UserId;

abstract class ExercisePickedEvent<E extends Exercise> extends DomainEvent {
    private static final long serialVersionUID = 1L;

    protected final E exercise;

    ExercisePickedEvent(final UserId userId, final LocalDateTime timestamp, final E exercise) {
        super(userId, timestamp);

        this.exercise = exercise;
    }

}

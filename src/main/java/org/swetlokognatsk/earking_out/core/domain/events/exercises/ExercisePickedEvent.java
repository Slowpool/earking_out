package org.swetlokognatsk.earking_out.core.domain.events.exercises;

import java.time.LocalDateTime;
import org.swetlokognatsk.earking_out.core.domain.events.DomainEvent;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;

abstract class ExercisePickedEvent<E extends Exercise> extends DomainEvent {
    private static final long serialVersionUID = 1L;

    protected final E exercise;

    ExercisePickedEvent(final LocalDateTime timestamp, final E exercise) {
        super(timestamp);

        this.exercise = exercise;
    }

}

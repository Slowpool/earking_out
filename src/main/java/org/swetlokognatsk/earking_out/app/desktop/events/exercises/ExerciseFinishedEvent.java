package org.swetlokognatsk.earking_out.app.desktop.events.exercises;

import java.util.UUID;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import javafx.event.*;

// TODO generics are awkward here
public final class ExerciseFinishedEvent<E extends Exercise> extends Event {
    public static final EventType<ExerciseFinishedEvent<?>> EXERCISE_FINISHED = new EventType<>("EXERCISE_FINISHED");

    public final UUID sessionId;
    public final E exercise;

    public ExerciseFinishedEvent(final EventType<?> eventType, final UUID sessionId, final E exercise) {
        super(eventType);

        this.sessionId = sessionId;
        this.exercise = exercise;
    }
}

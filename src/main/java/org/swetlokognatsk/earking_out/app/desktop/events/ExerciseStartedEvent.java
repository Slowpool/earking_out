package org.swetlokognatsk.earking_out.app.desktop.events;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import javafx.event.*;

public class ExerciseStartedEvent<E extends Exercise> extends Event {
    public static final EventType<ExerciseStartedEvent<?>> EXERCISE_STARTED = new EventType<ExerciseStartedEvent<?>>("EXERCISE_STARTED");

    public final E exercise;

    public ExerciseStartedEvent(final EventType<?> eventType, final E exercise) {
        super(eventType);
        this.exercise = exercise;
    }
}

package org.swetlokognatsk.earking_out.app.desktop.events.exercises;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import javafx.event.*;

// TODO rename to SessionStarted. also rename ExerciseFinished and ExerciseStartedOver
public final class ExerciseStartedEvent<E extends Exercise> extends Event {
    public static final EventType<ExerciseStartedEvent<?>> EXERCISE_STARTED = new EventType<ExerciseStartedEvent<?>>("EXERCISE_STARTED");

    public final E exercise;

    public ExerciseStartedEvent(final EventType<?> eventType, final E exercise) {
        super(eventType);

        this.exercise = exercise;
    }
}

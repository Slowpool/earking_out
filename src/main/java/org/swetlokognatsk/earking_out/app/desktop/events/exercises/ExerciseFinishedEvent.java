package org.swetlokognatsk.earking_out.app.desktop.events.exercises;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionId;

import javafx.event.*;

// TODO generics are awkward here
public final class ExerciseFinishedEvent<E extends Exercise> extends Event {
    public static final EventType<ExerciseFinishedEvent<?>> EXERCISE_FINISHED = new EventType<>("EXERCISE_FINISHED");

    public final SessionId sessionId;
    public final E exercise;

    public ExerciseFinishedEvent(final EventType<?> eventType, final SessionId sessionId, final E exercise) {
        super(eventType);

        this.sessionId = sessionId;
        this.exercise = exercise;
    }
}

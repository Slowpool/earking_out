package org.swetlokognatsk.earking_out.app.desktop.events.exercises;

import org.swetlokognatsk.earking_out.core.domain.model.Session;

import javafx.event.*;

public final class ExerciseFinishedEvent extends Event {
    public static final EventType<ExerciseFinishedEvent> EXERCISE_FINISHED = new EventType<>("EXERCISE_FINISHED");

    public final Session<?> session;

    public ExerciseFinishedEvent(final EventType<?> eventType, Session<?> session) {
        super(eventType);
        this.session = session;

    }
}

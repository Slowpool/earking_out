package org.swetlokognatsk.earking_out.app.desktop.events.exercises;

import java.util.UUID;
import javafx.event.*;

public final class ExerciseFinishedEvent extends Event {
    public static final EventType<ExerciseFinishedEvent> EXERCISE_FINISHED = new EventType<>("EXERCISE_FINISHED");

    public final UUID sessionId;

    public ExerciseFinishedEvent(final EventType<?> eventType, final UUID sessionId) {
        super(eventType);
        this.sessionId = sessionId;

    }
}

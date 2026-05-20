package org.swetlokognatsk.earking_out.app.desktop.events.exercises;

import org.swetlokognatsk.earking_out.core.domain.model.Session;

import javafx.event.*;

// TODO generic?
public class ExerciseFinishedEvent extends Event {
    public final Session<?> session;

    public ExerciseFinishedEvent(final EventType<?> eventType, Session<?> session) {
        super(eventType);
        this.session = session;

    }
}

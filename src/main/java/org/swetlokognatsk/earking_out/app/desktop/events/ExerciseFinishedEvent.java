package org.swetlokognatsk.earking_out.app.desktop.events;

import org.swetlokognatsk.earking_out.core.domain.model.Session;

import javafx.beans.NamedArg;
import javafx.event.*;

// TODO generic?
public class ExerciseFinishedEvent extends Event {
    public final Session<?> session;

    public ExerciseFinishedEvent(final @NamedArg("eventType") EventType<? extends Event> eventType, Session<?> session) {
        super(eventType);
        this.session = session;

    }
}

package org.swetlokognatsk.earking_out.app.desktop.events;

import javafx.beans.NamedArg;
import javafx.event.*;

public class ExerciseFinishedEvent extends Event {

    public ExerciseFinishedEvent(final @NamedArg("eventType") EventType<? extends Event> eventType) {
        super(eventType);

    }
}

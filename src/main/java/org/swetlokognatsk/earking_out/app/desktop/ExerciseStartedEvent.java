package org.swetlokognatsk.earking_out.app.desktop;

import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfig;
import javafx.beans.NamedArg;
import javafx.event.*;

class ExerciseStartedEvent extends Event {
    // TODO use generics
    public final PuzzleConfig config;

    // TODO why to NamedArg and how to use it?
    // TODO why to have eventType if there're no of the in examples in internet
    public ExerciseStartedEvent(final @NamedArg("eventType") EventType<? extends Event> eventType, final PuzzleConfig config) {
        super(eventType);
        this.config = config;
    }
}

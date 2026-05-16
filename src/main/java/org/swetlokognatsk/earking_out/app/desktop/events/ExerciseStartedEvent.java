package org.swetlokognatsk.earking_out.app.desktop.events;

import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfig;
import javafx.beans.NamedArg;
import javafx.event.*;

// TODO use generics
public class ExerciseStartedEvent<PC extends PuzzleConfig<?>> extends Event {
    public final PC puzzleConfig;

    // TODO why to NamedArg and how to use it?
    // TODO why to have eventType if there're no of the in examples in internet
    public ExerciseStartedEvent(final @NamedArg("eventType") EventType<? extends Event> eventType, final PC puzzleConfig) {
        super(eventType);
        this.puzzleConfig = puzzleConfig;
    }
}

package org.swetlokognatsk.earking_out.app.desktop.events;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfig;
import javafx.beans.NamedArg;
import javafx.event.*;

// TODO use generics
public class ExerciseStartedEvent extends Event {
    public final PuzzleConfig config;
    public final Exercise exercise;

    // TODO why to NamedArg and how to use it?
    // TODO why to have eventType if there're no of the in examples in internet
    public ExerciseStartedEvent(final @NamedArg("eventType") EventType<? extends Event> eventType, final PuzzleConfig config, final Exercise exercise) {
        super(eventType);
        this.config = config;
        this.exercise = exercise;
    }
}

package org.swetlokognatsk.earking_out.app.desktop.events;

import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfig;

import javafx.beans.NamedArg;
import javafx.event.Event;
import javafx.event.EventType;

public class ExerciseStartedOverEvent<PC extends PuzzleConfig<?>> extends Event {
    public final PC puzzleConfig;

    public ExerciseStartedOverEvent(final @NamedArg("eventType") EventType<? extends Event> eventType, PC puzzleConfig) {
        super(eventType);
        this.puzzleConfig = puzzleConfig;
    }
}

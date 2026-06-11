package org.swetlokognatsk.earking_out.app.desktop.events.exercises;

import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfigAggregate;
import javafx.event.Event;
import javafx.event.EventType;

public final class ExerciseStartedOverEvent<PC extends PuzzleConfigAggregate<?>> extends Event {
    // TODO how 'bout inheritance from EXERCISE_STARTED?
    public static EventType<ExerciseStartedOverEvent<?>> EXERCISE_STARTED_OVER = new EventType<>("EXERCISE_STARTED_OVER");

    public final PC puzzleConfig;

    public ExerciseStartedOverEvent(final EventType<?> eventType, PC puzzleConfig) {
        super(eventType);
        this.puzzleConfig = puzzleConfig;
    }
}

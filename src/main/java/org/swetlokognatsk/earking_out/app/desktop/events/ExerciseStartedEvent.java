package org.swetlokognatsk.earking_out.app.desktop.events;

import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfig;
import javafx.event.*;

public class ExerciseStartedEvent<PC extends PuzzleConfig<?>> extends Event {
    public static final EventType<ExerciseStartedEvent<?>> EXERCISE_STARTED = new EventType<ExerciseStartedEvent<?>>("EXERCISE_STARTED");

    public final PC puzzleConfig;

    public ExerciseStartedEvent(final EventType<?> eventType, final PC puzzleConfig) {
        super(eventType);
        this.puzzleConfig = puzzleConfig;
    }
}

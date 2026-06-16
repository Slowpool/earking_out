package org.swetlokognatsk.earking_out.app.desktop.events.exercises;

import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.PuzzleConfigDTO;

import javafx.event.Event;
import javafx.event.EventType;

public final class ExerciseStartedOverEvent<PCDTO extends PuzzleConfigDTO<?>> extends Event {
    // TODO how 'bout inheritance from EXERCISE_STARTED?
    public static EventType<ExerciseStartedOverEvent<?>> EXERCISE_STARTED_OVER = new EventType<>("EXERCISE_STARTED_OVER");

    public final PCDTO puzzleConfigDto;

    public ExerciseStartedOverEvent(final EventType<?> eventType, PCDTO puzzleConfigDto) {
        super(eventType);
        this.puzzleConfigDto = puzzleConfigDto;
    }
}

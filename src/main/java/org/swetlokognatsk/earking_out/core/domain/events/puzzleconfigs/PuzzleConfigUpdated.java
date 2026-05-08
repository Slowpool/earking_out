package org.swetlokognatsk.earking_out.core.domain.events.puzzleconfigs;

import java.time.LocalDateTime;
import java.util.Objects;

import org.swetlokognatsk.earking_out.core.domain.events.DomainEvent;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfig;

public abstract class PuzzleConfigUpdated<E extends Exercise, PC extends PuzzleConfig<E>> extends DomainEvent {
    public E exercise;
    public PC newConfig;

    public PuzzleConfigUpdated(LocalDateTime timestamp, E exercise, PC newConfig) {
        super(timestamp);
        Objects.requireNonNull(exercise, "Exercise cannot be null");

        this.exercise = exercise;
        this.newConfig = newConfig;
    }

}

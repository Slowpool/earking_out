package org.swetlokognatsk.earking_out.core.domain.events.puzzles.configs;

import java.time.LocalDateTime;
import java.util.Objects;
import org.swetlokognatsk.earking_out.core.domain.events.DomainEvent;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfigAggregate;

public abstract class PuzzleConfigUpdated<E extends Exercise, PC extends PuzzleConfigAggregate<E>> extends DomainEvent {
    public final E exercise;
    public final PC newConfig;

    public PuzzleConfigUpdated(final LocalDateTime timestamp, final E exercise, final PC newConfig) {
        super(timestamp);

        this.exercise = Objects.requireNonNull(exercise, "Exercise cannot be null");
        this.newConfig = newConfig;
    }

}

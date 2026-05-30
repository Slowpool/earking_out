package org.swetlokognatsk.earking_out.core.domain.events.exercises;

import java.time.LocalDateTime;
import java.util.UUID;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfig;

public final class ExerciseStarted<E extends Exercise, PC extends PuzzleConfig<E>> extends ExerciseStateChanged<E, PC> {
    public final E exercise;
    public final PC puzzleConfig;

    public ExerciseStarted(final LocalDateTime timestamp, final UUID sessionId, final E exercise, final PC puzzleConfig) {
        super(timestamp, sessionId);

        this.exercise = exercise;
        this.puzzleConfig = puzzleConfig;
    }
}

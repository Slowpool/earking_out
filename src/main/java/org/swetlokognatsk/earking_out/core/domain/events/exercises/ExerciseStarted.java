package org.swetlokognatsk.earking_out.core.domain.events.exercises;

import java.time.LocalDateTime;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfigAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionId;

public final class ExerciseStarted<E extends Exercise, PC extends PuzzleConfigAggregate<E>> extends ExerciseStateChanged<E, PC> {
    public final E exercise;
    public final PC puzzleConfig;

    public ExerciseStarted(final LocalDateTime timestamp, final SessionId sessionId, final E exercise, final PC puzzleConfig) {
        super(timestamp, sessionId);

        this.exercise = exercise;
        this.puzzleConfig = puzzleConfig;
    }
}

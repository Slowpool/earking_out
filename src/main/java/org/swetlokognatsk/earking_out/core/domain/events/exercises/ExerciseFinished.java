package org.swetlokognatsk.earking_out.core.domain.events.exercises;

import java.time.LocalDateTime;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfigAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionId;

/**
 * @param aborted - whether user stopped the exercise session before solving all of the puzzles
 */
public final class ExerciseFinished<E extends Exercise, PC extends PuzzleConfigAggregate<E>> extends ExerciseStateChanged<E, PC> {
    public final boolean aborted;

    public ExerciseFinished(final LocalDateTime timestamp, final SessionId sessionId, final boolean aborted) {
        super(timestamp, sessionId);

        this.aborted = aborted;
    }
}

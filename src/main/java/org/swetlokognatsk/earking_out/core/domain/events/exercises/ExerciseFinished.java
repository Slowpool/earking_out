package org.swetlokognatsk.earking_out.core.domain.events.exercises;

import java.time.LocalDateTime;
import java.util.UUID;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfig;

/**
 * @param aborted - whether user stopped the exercise session before solving all of the puzzles
 */
public final class ExerciseFinished<E extends Exercise, PC extends PuzzleConfig<E>> extends ExerciseStateChanged<E, PC> {
    public final boolean aborted;

    public ExerciseFinished(final LocalDateTime timestamp, final UUID sessionId, final boolean aborted) {
        super(timestamp, sessionId);

        this.aborted = aborted;
    }
}

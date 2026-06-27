package org.swetlokognatsk.earking_out.core.domain.model.session.perfectpitch;

import java.util.UUID;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.PerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.perfectpitch.PerfectPitchPuzzle;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionStats;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.PuzzleConfigDTO;

public abstract class PerfectPitchSessionAggregate<E extends PerfectPitchExercise, P extends PerfectPitchPuzzle<E, ?>, PCDTO extends PuzzleConfigDTO<E>> extends SessionAggregate<E, P, PCDTO> {

    public PerfectPitchSessionAggregate(final UUID id, final PCDTO puzzleConfigDto, final SessionStats stats) {
        super(id, puzzleConfigDto, stats);
    }
}

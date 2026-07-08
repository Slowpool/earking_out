package org.swetlokognatsk.earking_out.core.domain.model.session.perfectpitch;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.PerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.perfectpitch.PerfectPitchPuzzle;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionId;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionStats;
import org.swetlokognatsk.earking_out.core.domain.model.solutions.perfectpitch.PerfectPitchSolution;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.PuzzleConfigDTO;

public abstract class PerfectPitchSessionAggregate<E extends PerfectPitchExercise, S extends PerfectPitchSolution, P extends PerfectPitchPuzzle<E, S>, PCDTO extends PuzzleConfigDTO<E>> extends SessionAggregate<E, S, P, PCDTO> {

    public PerfectPitchSessionAggregate(final SessionId id, final PCDTO puzzleConfigDto, final SessionStats stats) {
        super(id, puzzleConfigDto, stats);
    }
}

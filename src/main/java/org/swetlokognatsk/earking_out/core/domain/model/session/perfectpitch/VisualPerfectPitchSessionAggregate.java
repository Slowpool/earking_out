package org.swetlokognatsk.earking_out.core.domain.model.session.perfectpitch;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.PerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.VisualPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.PuzzlesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.perfectpitch.VisualPerfectPitchPuzzle;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionId;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionStats;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.perfectpitch.VisualPerfectPitchConfigDTO;

public final class VisualPerfectPitchSessionAggregate extends PerfectPitchSessionAggregate<VisualPerfectPitchExercise, VisualPerfectPitchPuzzle, VisualPerfectPitchConfigDTO> {
    public VisualPerfectPitchSessionAggregate(final PuzzlesFactory puzzlesFactory, final SessionId id, final VisualPerfectPitchConfigDTO puzzleConfigDto, final SessionStats stats) {
        super(puzzlesFactory, id, puzzleConfigDto, stats);
    }
    

}

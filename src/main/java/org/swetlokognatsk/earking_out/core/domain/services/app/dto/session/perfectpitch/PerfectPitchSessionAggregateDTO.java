package org.swetlokognatsk.earking_out.core.domain.services.app.dto.session.perfectpitch;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.PerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.perfectpitch.PerfectPitchPuzzle;
import org.swetlokognatsk.earking_out.core.domain.model.session.GeneralSessionStats;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionId;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionStates;
import org.swetlokognatsk.earking_out.core.domain.model.session.perfectpitch.PerfectPitchSessionAggregate;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.perfectpitch.PerfectPitchConfigDTO;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.session.SessionAggregateDTO;

public abstract class PerfectPitchSessionAggregateDTO<E extends PerfectPitchExercise, P extends PerfectPitchPuzzle<E, ?>, PCDTO extends PerfectPitchConfigDTO<E>, SA extends PerfectPitchSessionAggregate<E, ?, P, PCDTO>> extends SessionAggregateDTO<E, P, PCDTO, SA> {

    public PerfectPitchSessionAggregateDTO(final SessionId sessionId, final PCDTO puzzleConfigDto, final GeneralSessionStats stats, final SessionStates state, final P puzzle, final Boolean prevGuessIsSuccessful, final Integer numberOfGuessesOfCurrentPuzzle) {
        super(sessionId, puzzleConfigDto, stats, state, puzzle, prevGuessIsSuccessful, numberOfGuessesOfCurrentPuzzle);
    }
}

package org.swetlokognatsk.earking_out.core.domain.services.app.dto.session.perfectpitch;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.perfectpitch.AudioPerfectPitchPuzzle;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionStates;
import org.swetlokognatsk.earking_out.core.domain.model.session.GeneralSessionStats;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionId;
import org.swetlokognatsk.earking_out.core.domain.model.session.perfectpitch.AudioPerfectPitchSessionAggregate;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.perfectpitch.AudioPerfectPitchConfigDTO;

public final class AudioPerfectPitchSessionAggregateDTO extends PerfectPitchSessionAggregateDTO<AudioPerfectPitchExercise, AudioPerfectPitchPuzzle, AudioPerfectPitchConfigDTO, AudioPerfectPitchSessionAggregate> {

    public AudioPerfectPitchSessionAggregateDTO(final SessionId sessionId, final AudioPerfectPitchConfigDTO puzzleConfigDto, final GeneralSessionStats stats, final SessionStates state, final AudioPerfectPitchPuzzle puzzle, final Boolean prevGuessIsSuccessful, final Integer numberOfGuessesOfCurrentPuzzle) {
        super(sessionId, puzzleConfigDto, stats, state, puzzle, prevGuessIsSuccessful, numberOfGuessesOfCurrentPuzzle);
    }
}

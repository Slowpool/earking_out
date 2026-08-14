package org.swetlokognatsk.earking_out.core.domain.services.app.dto.session.perfectpitch;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.perfectpitch.AudioPerfectPitchPuzzle;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionId;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionStates;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionStats;
import org.swetlokognatsk.earking_out.core.domain.model.session.perfectpitch.AudioPerfectPitchSessionAggregate;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.perfectpitch.AudioPerfectPitchConfigDTO;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.session.SessionDTO;

public final class AudioPerfectPitchSessionDTO extends SessionDTO<AudioPerfectPitchExercise, AudioPerfectPitchPuzzle, AudioPerfectPitchConfigDTO, AudioPerfectPitchSessionAggregate> {

    public AudioPerfectPitchSessionDTO(final SessionId id, final AudioPerfectPitchConfigDTO puzzleConfigDto, final SessionStats stats, final SessionStates state, final AudioPerfectPitchPuzzle puzzle, final Boolean prevGuessIsSuccessful, final Integer numberOfGuessesOfCurrentPuzzle) {
        super(id, puzzleConfigDto, stats, state, puzzle, prevGuessIsSuccessful, numberOfGuessesOfCurrentPuzzle);
    }
}

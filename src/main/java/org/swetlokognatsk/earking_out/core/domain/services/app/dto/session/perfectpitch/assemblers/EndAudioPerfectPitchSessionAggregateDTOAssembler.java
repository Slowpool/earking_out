package org.swetlokognatsk.earking_out.core.domain.services.app.dto.session.perfectpitch.assemblers;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.Puzzle;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.perfectpitch.AudioPerfectPitchPuzzle;
import org.swetlokognatsk.earking_out.core.domain.model.session.perfectpitch.AudioPerfectPitchSessionAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.solutions.perfectpitch.AudioPerfectPitchSolution;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.perfectpitch.AudioPerfectPitchConfigDTO;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.session.EndSessionAggregateDTOAssembler;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.session.perfectpitch.AudioPerfectPitchSessionAggregateDTO;

public final class EndAudioPerfectPitchSessionAggregateDTOAssembler extends EndSessionAggregateDTOAssembler<AudioPerfectPitchExercise, AudioPerfectPitchSolution, AudioPerfectPitchPuzzle, AudioPerfectPitchConfigDTO, AudioPerfectPitchSessionAggregate, AudioPerfectPitchSessionAggregateDTO> {

    public AudioPerfectPitchSessionAggregateDTO assemble(final AudioPerfectPitchSessionAggregate sessionAggregate) {
        AudioPerfectPitchPuzzle puzzle;
        try {
            puzzle = sessionAggregate.getPuzzle();
        } catch (IllegalStateException e) {
            puzzle = null;
        }

        Boolean prevGuessIsSuccessful;
        try {
            prevGuessIsSuccessful = sessionAggregate.getPrevGuessIsSuccessful();
        } catch (IllegalStateException e) {
            prevGuessIsSuccessful = null;
        }

        var dto = new AudioPerfectPitchSessionAggregateDTO(sessionAggregate.getPuzzleConfig(), sessionAggregate.getStats(), sessionAggregate.getState(), puzzle, prevGuessIsSuccessful, sessionAggregate.getNumberOfGuessesOfCurrentPuzzle());
        return dto;
    }
}

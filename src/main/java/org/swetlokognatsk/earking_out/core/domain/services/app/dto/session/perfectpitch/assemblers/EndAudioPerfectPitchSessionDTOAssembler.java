package org.swetlokognatsk.earking_out.core.domain.services.app.dto.session.perfectpitch.assemblers;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.Puzzle;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.perfectpitch.AudioPerfectPitchPuzzle;
import org.swetlokognatsk.earking_out.core.domain.model.session.perfectpitch.AudioPerfectPitchSessionAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.solutions.perfectpitch.AudioPerfectPitchSolution;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.perfectpitch.AudioPerfectPitchConfigDTO;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.session.EndSessionDTOAssembler;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.session.perfectpitch.AudioPerfectPitchSessionDTO;

public final class EndAudioPerfectPitchSessionDTOAssembler extends EndSessionDTOAssembler<AudioPerfectPitchExercise, AudioPerfectPitchSolution, AudioPerfectPitchPuzzle, AudioPerfectPitchConfigDTO, AudioPerfectPitchSessionAggregate, AudioPerfectPitchSessionDTO> {

    public AudioPerfectPitchSessionDTO assemble(final AudioPerfectPitchSessionAggregate sessionAggregate) {
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

        Integer numberOfGuessesOfCurrentPuzzle;
        try {
            numberOfGuessesOfCurrentPuzzle = sessionAggregate.getNumberOfGuessesOfCurrentPuzzle();
        } catch (IllegalStateException e) {
            numberOfGuessesOfCurrentPuzzle = null;
        }

        var dto = new AudioPerfectPitchSessionDTO(sessionAggregate.getId(), sessionAggregate.getPuzzleConfig(), sessionAggregate.getStats(), sessionAggregate.getState(), puzzle, prevGuessIsSuccessful, numberOfGuessesOfCurrentPuzzle);
        return dto;
    }
}

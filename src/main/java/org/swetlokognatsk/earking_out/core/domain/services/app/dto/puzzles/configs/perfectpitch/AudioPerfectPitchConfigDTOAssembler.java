package org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.perfectpitch;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.AudioPerfectPitchConfigAggregate;

public class AudioPerfectPitchConfigDTOAssembler extends PerfectPitchConfigDTOAssembler<AudioPerfectPitchExercise, AudioPerfectPitchConfigAggregate, AudioPerfectPitchConfigDTO> {

    public AudioPerfectPitchConfigDTO assemble(final AudioPerfectPitchConfigAggregate aggregate) {
        var dto = new AudioPerfectPitchConfigDTO(aggregate.getId(), aggregate.getTargetNumberOfPuzzles(), aggregate.getStatsRecording(), aggregate.getNormalizedNotesForPuzzle(), aggregate.getNormalizedRootNote(), aggregate.getInputMode());
        return dto;
    }
}

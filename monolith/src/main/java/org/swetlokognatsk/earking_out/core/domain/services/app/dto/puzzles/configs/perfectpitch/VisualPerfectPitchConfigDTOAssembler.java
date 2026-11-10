package org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.perfectpitch;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.VisualPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.VisualPerfectPitchConfigAggregate;

public class VisualPerfectPitchConfigDTOAssembler extends PerfectPitchConfigDTOAssembler<VisualPerfectPitchExercise, VisualPerfectPitchConfigAggregate, VisualPerfectPitchConfigDTO> {

    public VisualPerfectPitchConfigDTO assemble(final VisualPerfectPitchConfigAggregate aggregate) {
        var dto = new VisualPerfectPitchConfigDTO(aggregate.getId(), aggregate.getTargetNumberOfPuzzles(), aggregate.getStatsRecording(), aggregate.getNormalizedNotesForPuzzle(), aggregate.getNormalizedRootNote(), aggregate.getInputMode());
        return dto;
    }
}
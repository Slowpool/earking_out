package org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.perfectpitch;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.PerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.PerfectPitchConfigAggregate;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.EndPuzzleConfigDTOAssembler;

public abstract class PerfectPitchConfigDTOAssembler<E extends PerfectPitchExercise, PCA extends PerfectPitchConfigAggregate<E>, PCDTO extends PerfectPitchConfigDTO<E>> extends EndPuzzleConfigDTOAssembler<E, PCA, PCDTO> {
}

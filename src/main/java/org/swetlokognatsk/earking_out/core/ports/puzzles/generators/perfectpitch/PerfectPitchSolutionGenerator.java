package org.swetlokognatsk.earking_out.core.ports.puzzles.generators.perfectpitch;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.PerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.solutions.Solution;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.perfectpitch.PerfectPitchConfigDTO;
import org.swetlokognatsk.earking_out.core.ports.puzzles.SolutionGenerator;

public interface PerfectPitchSolutionGenerator<S extends Solution, PC extends PerfectPitchConfigDTO<? extends PerfectPitchExercise>> extends SolutionGenerator<S> {

}

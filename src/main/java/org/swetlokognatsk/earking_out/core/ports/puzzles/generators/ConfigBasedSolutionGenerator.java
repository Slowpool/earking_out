package org.swetlokognatsk.earking_out.core.ports.puzzles.generators;

import org.swetlokognatsk.earking_out.core.domain.model.solutions.Solution;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.PuzzleConfigDTO;
import org.swetlokognatsk.earking_out.core.ports.puzzles.SolutionGenerator;

public abstract class ConfigBasedSolutionGenerator<S extends Solution, PCDTO extends PuzzleConfigDTO<?>> implements SolutionGenerator<S> {
    protected final PCDTO puzzleConfigDto;

    public ConfigBasedSolutionGenerator(final PCDTO puzzleConfigDto) {
        this.puzzleConfigDto = puzzleConfigDto;
    }
};

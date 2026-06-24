package org.swetlokognatsk.earking_out.core.domain.services.domain.puzzles.generators;

import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.PuzzleConfigDTO;
import org.swetlokognatsk.earking_out.core.ports.puzzles.SolutionGenerator;

public abstract class ConfigBasedSolutionGenerator<PCDTO extends PuzzleConfigDTO<?>> implements SolutionGenerator {
    protected PCDTO puzzleConfigDto;

    public ConfigBasedSolutionGenerator(final PCDTO puzzleConfigDto) {
        this.puzzleConfigDto = puzzleConfigDto;

    }
};

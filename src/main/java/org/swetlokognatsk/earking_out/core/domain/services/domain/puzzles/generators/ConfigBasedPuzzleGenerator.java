package org.swetlokognatsk.earking_out.core.domain.services.domain.puzzles.generators;

import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.PuzzleConfigDTO;
import org.swetlokognatsk.earking_out.core.ports.puzzles.PuzzleGenerator;

public abstract class ConfigBasedPuzzleGenerator<PCDTO extends PuzzleConfigDTO<?>> implements PuzzleGenerator {
    protected PCDTO puzzleConfigDto;

    public ConfigBasedPuzzleGenerator(final PCDTO puzzleConfigDto) {
        this.puzzleConfigDto = puzzleConfigDto;

    }
};

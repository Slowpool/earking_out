package org.swetlokognatsk.earking_out.core.domain.model.puzzles.generators;

import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfig;
import org.swetlokognatsk.earking_out.core.ports.puzzles.PuzzleGenerator;

public abstract class ConfigBasedPuzzleGenerator<PC extends PuzzleConfig<?>> implements PuzzleGenerator {
    protected PC puzzleConfig;

    public ConfigBasedPuzzleGenerator(PC puzzleConfig) {
        this.puzzleConfig = puzzleConfig;
    }
};

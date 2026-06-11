package org.swetlokognatsk.earking_out.core.domain.services.domain.puzzles.generators;

import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfigAggregate;
import org.swetlokognatsk.earking_out.core.ports.puzzles.PuzzleGenerator;

public abstract class ConfigBasedPuzzleGenerator<PC extends PuzzleConfigAggregate<?>> implements PuzzleGenerator {
    protected PC puzzleConfig;

    public ConfigBasedPuzzleGenerator(PC puzzleConfig) {
        this.puzzleConfig = puzzleConfig;

    }
};

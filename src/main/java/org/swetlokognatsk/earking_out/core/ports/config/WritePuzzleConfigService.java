package org.swetlokognatsk.earking_out.core.ports.config;

import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfig;

public interface WritePuzzleConfigService {
    void save(final PuzzleConfig<?> puzzleConfig);
}

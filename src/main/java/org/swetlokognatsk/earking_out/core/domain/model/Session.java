package org.swetlokognatsk.earking_out.core.domain.model;

import java.util.UUID;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfig;

public record Session<PC extends PuzzleConfig<?>>(UUID id, PC puzzleConfig, SessionStats stats) {

}

package org.swetlokognatsk.earking_out.core.domain.model;

import java.util.UUID;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfig;

// TODO ubiquitous language for `targetNumberOfPuzzles` and `completedPuzzles`
// TODO only `completedPuzzles` can change
public record Session<PC extends PuzzleConfig<?>>(UUID id, PC puzzleConfig, SessionStats stats) {

}

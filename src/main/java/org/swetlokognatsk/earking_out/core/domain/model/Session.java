package org.swetlokognatsk.earking_out.core.domain.model;

import java.util.UUID;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfig;

// TODO ubiquitous language for `targetNumberOfPuzzles` and `numberOfCompletedPuzzles`
// TODO only `numberOfCompletedPuzzles` can change
public record Session<E extends Exercise, PC extends PuzzleConfig<E>>(UUID id, E exercise, PC config, int targetNumberOfPuzzles, int numberOfCompletedPuzzles) {

}

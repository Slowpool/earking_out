package org.swetlokognatsk.earking_out.core.ports.config;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfigAggregate;

public interface PuzzleConfigJsonSerializer {
    String serializePuzzleConfig(final PuzzleConfigAggregate<?> puzzleConfig);

    <E extends Exercise> PuzzleConfigAggregate<?> deserializePuzzleConfig(final E exercise, final String serializedPuzzleConfig);

}

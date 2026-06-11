package org.swetlokognatsk.earking_out.core.ports.config;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfig;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.aggregates.PuzzleConfigAggregate;

public interface PuzzleConfigRepository {
    <E extends Exercise, PCA extends PuzzleConfigAggregate<? extends PuzzleConfig<E>>> PCA get(E exercise);
    void save(PuzzleConfigAggregate<?> puzzleConfigAggregate);
}

package org.swetlokognatsk.earking_out.core.ports.config;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfigAggregate;

public interface PuzzleConfigRepository {
    <E extends Exercise, PCA extends PuzzleConfigAggregate<E>> PCA get(final E exercise);

    void save(final PuzzleConfigAggregate<?> puzzleConfigAggregate);
}

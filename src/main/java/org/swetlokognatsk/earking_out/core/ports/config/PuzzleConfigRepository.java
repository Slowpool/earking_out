package org.swetlokognatsk.earking_out.core.ports.config;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfigAggregate;
import org.swetlokognatsk.earking_out.core.ports.base.AggregateRootRepository;

public interface PuzzleConfigRepository extends AggregateRootRepository<Exercise, PuzzleConfigAggregate<Exercise>> {
    <E extends Exercise, PCA extends PuzzleConfigAggregate<E>> PCA genericGet(final E exercise);

    void genericSave(final PuzzleConfigAggregate<?> puzzleConfigAggregate);
}

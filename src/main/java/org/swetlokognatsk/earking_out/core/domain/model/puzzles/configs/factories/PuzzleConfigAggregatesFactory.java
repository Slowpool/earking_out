package org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.factories;

import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfigAggregate;

public abstract class PuzzleConfigAggregatesFactory<PCA extends PuzzleConfigAggregate<?>> {
    public abstract PCA createDefault();

    public abstract PCA createShallowCopy(final PCA puzzleConfigAggregate);
}

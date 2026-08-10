package org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.factories;

import org.swetlokognatsk.earking_out.core.domain.model.base.AggregatesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfigAggregate;
import org.swetlokognatsk.earking_out.core.ports.base.ObjectCloner;

public abstract class PuzzleConfigAggregatesFactory<PCA extends PuzzleConfigAggregate<?>> extends AggregatesFactory<PCA> {

    public PuzzleConfigAggregatesFactory(final ObjectCloner cloner) {
        super(cloner);
    }
    
    public abstract PCA createDefault();

}

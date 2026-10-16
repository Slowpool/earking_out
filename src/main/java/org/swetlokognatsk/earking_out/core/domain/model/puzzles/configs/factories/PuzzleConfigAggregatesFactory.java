package org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.factories;

import org.swetlokognatsk.earking_out.core.domain.model.base.AggregatesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.identity.UserId;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfigAggregate;
import org.swetlokognatsk.earking_out.core.ports.base.ObjectCloner;
import org.swetlokognatsk.earking_out.core.ports.identity.UserResolver;

public abstract class PuzzleConfigAggregatesFactory<PCA extends PuzzleConfigAggregate<?>> extends AggregatesFactory<PCA> {

    protected final UserResolver userResolver;

    public PuzzleConfigAggregatesFactory(final ObjectCloner cloner, final UserResolver userResolver) {
        super(cloner);

        this.userResolver = userResolver;
    }

    public abstract PCA createDefault();

}

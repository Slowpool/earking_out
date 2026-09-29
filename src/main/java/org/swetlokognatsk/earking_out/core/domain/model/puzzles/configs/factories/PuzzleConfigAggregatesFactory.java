package org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.factories;

import org.swetlokognatsk.earking_out.core.domain.model.base.AggregatesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.identity.User;
import org.swetlokognatsk.earking_out.core.domain.model.identity.UserId;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfigAggregate;
import org.swetlokognatsk.earking_out.core.ports.base.ObjectCloner;

public abstract class PuzzleConfigAggregatesFactory<PCA extends PuzzleConfigAggregate<?>> extends AggregatesFactory<PCA> {

    protected final UserId userId;

    public PuzzleConfigAggregatesFactory(final ObjectCloner cloner, final User user) {
        super(cloner);

        this.userId = user.id;
    }

    public abstract PCA createDefault();

}

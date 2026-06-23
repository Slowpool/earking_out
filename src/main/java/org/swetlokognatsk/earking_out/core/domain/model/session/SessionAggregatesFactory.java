package org.swetlokognatsk.earking_out.core.domain.model.session;

import java.util.UUID;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.PuzzleConfigDTO;

public final class SessionAggregatesFactory {

    public SessionAggregatesFactory() {

    }

    public <E extends Exercise, PCDTO extends PuzzleConfigDTO<E>> SessionAggregate<PCDTO> create(final E exercise) {
        return new SessionAggregate(UUID.randomUUID(), null, null);
    }
}

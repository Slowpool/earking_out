package org.swetlokognatsk.earking_out.inftrastructure.adapters.session;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionAggregatesFactory;
import org.swetlokognatsk.earking_out.core.ports.session.services.SessionRepository;

public final class InMemorySessionRepository implements SessionRepository {
    protected final Map<UUID, SessionAggregate<?, ?, ?>> sessionAggregates = new HashMap<>();;

    protected final SessionAggregatesFactory sessionAggregatesFactory;

    public InMemorySessionRepository(final SessionAggregatesFactory sessionAggregatesFactory) {
        this.sessionAggregatesFactory = sessionAggregatesFactory;
    }

    public SessionAggregate<?, ?, ?> get(final UUID id) {
        var sessionAggregate = sessionAggregates.get(id);
        if (sessionAggregate == null) {
            throw new IllegalArgumentException("session not found. id: " + id);
        }
        var sessionAggregateCopy = sessionAggregatesFactory.createDeepCopy(sessionAggregate);
        return sessionAggregateCopy;
    }

    public void save(final SessionAggregate<?, ?, ?> aggregate) {
        var aggregateCopy = sessionAggregatesFactory.createDeepCopy(aggregate);
        sessionAggregates.put(aggregateCopy.getId(), aggregateCopy);
    }
}

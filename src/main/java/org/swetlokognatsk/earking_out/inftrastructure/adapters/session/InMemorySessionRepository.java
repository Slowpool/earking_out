package org.swetlokognatsk.earking_out.inftrastructure.adapters.session;

import java.util.HashMap;
import java.util.Map;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionId;
import org.swetlokognatsk.earking_out.core.domain.model.session.factories.SessionAggregatesFactory;
import org.swetlokognatsk.earking_out.core.ports.session.SessionRepository;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.base.AggregateRepository;

public abstract class InMemorySessionRepository<SA extends SessionAggregate<?, ?, ?, ?>> extends AggregateRepository implements SessionRepository<SA> {
    private final Map<SessionId, SessionAggregate<?, ?, ?, ?>> sessionAggregates = new HashMap<>();

    private final SessionAggregatesFactory sessionAggregatesFactory;

    public InMemorySessionRepository(final SessionAggregatesFactory sessionAggregatesFactory) {
        this.sessionAggregatesFactory = sessionAggregatesFactory;
    }

    public final SA get(final SessionId id) {
        var sessionAggregate = (SA) sessionAggregates.get(id);
        if (sessionAggregate == null) {
            throw new IllegalArgumentException("session not found. id: " + id);
        }
        var sessionAggregateCopy = sessionAggregatesFactory.createDeepCopy(sessionAggregate);
        return (SA) sessionAggregateCopy;
    }

    public void save(SA sessionAggregate) {
        var events = sessionAggregate.flushEvents();

        sessionAggregate = (SA) sessionAggregatesFactory.createDeepCopy(sessionAggregate);
        sessionAggregates.put(sessionAggregate.getId(), sessionAggregate);

        publishEvents(events);
    }
}

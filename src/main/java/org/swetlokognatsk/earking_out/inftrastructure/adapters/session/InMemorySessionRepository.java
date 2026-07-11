package org.swetlokognatsk.earking_out.inftrastructure.adapters.session;

import java.util.HashMap;
import java.util.Map;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionId;
import org.swetlokognatsk.earking_out.core.domain.model.session.factories.SessionAggregatesFactory;
import org.swetlokognatsk.earking_out.core.ports.session.SessionRepository;

public abstract class InMemorySessionRepository<SA extends SessionAggregate<?, ?, ?, ?>> implements SessionRepository<SA> {
    protected final Map<SessionId, SessionAggregate<?, ?, ?, ?>> sessionAggregates = new HashMap<>();

    protected final SessionAggregatesFactory sessionAggregatesFactory;

    protected abstract void loadDependentAggregates(final SA sessionAggregate);
    protected abstract void saveDependentAggregates(final SA sessionAggregate);

    public InMemorySessionRepository(final SessionAggregatesFactory sessionAggregatesFactory) {
        this.sessionAggregatesFactory = sessionAggregatesFactory;
    }

    public SA get(final SessionId id) {
        var sessionAggregate = (SA) sessionAggregates.get(id);
        if (sessionAggregate == null) {
            throw new IllegalArgumentException("session not found. id: " + id);
        }
        loadDependentAggregates(sessionAggregate);
        var sessionAggregateCopy = sessionAggregatesFactory.createDeepCopy(sessionAggregate);
        return (SA) sessionAggregateCopy;
    }

    public void save(SA sessionAggregate) {
        sessionAggregate = (SA) sessionAggregatesFactory.createDeepCopy(sessionAggregate);
        saveDependentAggregates(sessionAggregate);
        sessionAggregates.put(sessionAggregate.getId(), sessionAggregate);
    }
}

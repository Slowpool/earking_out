package org.swetlokognatsk.earking_out.infrastructure.adapters.session;

import java.util.HashMap;
import java.util.Map;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionId;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionStates;
import org.swetlokognatsk.earking_out.core.domain.model.session.factories.SessionAggregatesFactory;
import org.swetlokognatsk.earking_out.core.ports.session.SessionRepository;
import org.swetlokognatsk.earking_out.infrastructure.adapters.base.AggregateRepository;

public abstract class InMemorySessionRepository<SA extends SessionAggregate<?, ?, ?, ?>> extends AggregateRepository implements SessionRepository<SA> {
    private final Map<SessionId, SessionAggregate<?, ?, ?, ?>> sessionAggregates = new HashMap<>();
    private SessionId activeSessionId;

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

        updateActiveSession(sessionAggregate);

        publishEvents(events);
    }

    private void updateActiveSession(final SA sessionAggregate) {
        // last saved session is active
        activeSessionId = sessionAggregate.getState().equals(SessionStates.IN_PROGRESS) ? sessionAggregate.getId() : null;
    }

    public final SA getActiveSession() {
        if (activeSessionId == null) {
            throw new IllegalStateException("there's no active session");
        }
        var activeSessionCopy = (SA) get(activeSessionId);
        return activeSessionCopy;
    }

}

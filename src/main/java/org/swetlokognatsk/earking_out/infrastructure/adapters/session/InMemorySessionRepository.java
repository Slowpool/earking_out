package org.swetlokognatsk.earking_out.infrastructure.adapters.session;

import java.util.HashMap;
import java.util.Map;

import org.swetlokognatsk.earking_out.core.domain.model.base.AggregateNotFoundException;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionId;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionStates;
import org.swetlokognatsk.earking_out.core.domain.model.session.factories.SessionAggregatesFactory;
import org.swetlokognatsk.earking_out.core.ports.session.SessionRepository;
import org.swetlokognatsk.earking_out.infrastructure.adapters.base.AggregateRepository;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter(AccessLevel.PRIVATE)
public abstract class InMemorySessionRepository<SA extends SessionAggregate<?, ?, ?, ?>> extends AggregateRepository implements SessionRepository<SA> {
    private final Map<SessionId, SessionAggregate<?, ?, ?, ?>> sessionAggregates = new HashMap<>();

    @Setter(AccessLevel.PRIVATE)
    private SessionId activeSessionId;

    private final SessionAggregatesFactory sessionAggregatesFactory;

    public InMemorySessionRepository(final SessionAggregatesFactory sessionAggregatesFactory) {
        this.sessionAggregatesFactory = sessionAggregatesFactory;
    }

    public SA get(final SessionId id) throws AggregateNotFoundException {
        var sessionAggregate = (SA) getSessionAggregates()
                .get(id);
        if (sessionAggregate == null) {
            throw new AggregateNotFoundException("session not found. id: " + id);
        }
        var sessionAggregateCopy = getSessionAggregatesFactory()
                .createDeepCopy(sessionAggregate);
        return (SA) sessionAggregateCopy;
    }

    public void save(SA sessionAggregate) {
        var events = sessionAggregate.flushEvents();

        sessionAggregate = (SA) getSessionAggregatesFactory()
                .createDeepCopy(sessionAggregate);
        getSessionAggregates()
                .put(sessionAggregate.getId(), sessionAggregate);

        rememberAsActiveSession(sessionAggregate);

        publishEvents(events);
    }

    private void rememberAsActiveSession(final SA sessionAggregate) {
        // last saved session is active
        var activeSessionId = sessionAggregate.getState()
                .equals(SessionStates.IN_PROGRESS)
                        ? sessionAggregate.getId()
                        : null;
        setActiveSessionId(activeSessionId);
    }

    public SA getActiveSession() {
        if (getActiveSessionId() == null) {
            throw new IllegalStateException("there's no active session");
        }
        var activeSessionCopy = (SA) get(activeSessionId);
        return activeSessionCopy;
    }
}

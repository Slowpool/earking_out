package org.swetlokognatsk.earking_out.infrastructure.adapters.session;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.swetlokognatsk.earking_out.core.domain.model.base.AggregateNotFoundException;
import org.swetlokognatsk.earking_out.core.domain.model.identity.UserId;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionId;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionStates;
import org.swetlokognatsk.earking_out.core.domain.model.session.exceptions.NoActiveSessionException;
import org.swetlokognatsk.earking_out.core.domain.model.session.factories.SessionAggregatesFactory;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.session.SessionAggregateDTO;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.session.SessionAggregateDTOAssembler;
import org.swetlokognatsk.earking_out.core.ports.identity.UserResolver;
import org.swetlokognatsk.earking_out.core.ports.session.SessionRepository;
import org.swetlokognatsk.earking_out.infrastructure.adapters.base.AggregateRepository;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

public abstract class InMemorySessionRepository<SA extends SessionAggregate<?, ?, ?, ?>> extends AggregateRepository implements SessionRepository<SA> {
    private final Map<UserId, Map<SessionId, SessionAggregate<?, ?, ?, ?>>> sessionAggregates = new HashMap<>();

    private final Map<UserId, SessionId> activeSessionIds = new HashMap<>();

    @Getter(AccessLevel.PRIVATE)
    private final SessionAggregatesFactory sessionAggregatesFactory;
    @Getter(AccessLevel.PRIVATE)
    private final UserResolver userResolver;
    @Getter(AccessLevel.PRIVATE)
    private final SessionAggregateDTOAssembler dtoAssembler;

    private SessionId getActiveSessionId() {
        return activeSessionIds.get(getCurrentUserId());
    }

    private void setActiveSessionId(final SessionId sessionId) {
        activeSessionIds.put(getCurrentUserId(), sessionId);
    }

    private UserId getCurrentUserId() {
        return userResolver.getCurrentUserId();
    }

    private Map<SessionId, SessionAggregate<?, ?, ?, ?>> getSessionAggregates() {
        var ownedSessionAggregates = sessionAggregates.get(getCurrentUserId());
        if (ownedSessionAggregates == null) {
            ownedSessionAggregates = new HashMap<SessionId, SessionAggregate<?, ?, ?, ?>>();
            sessionAggregates.put(getCurrentUserId(), ownedSessionAggregates);
        }
        return ownedSessionAggregates;
    }

    public InMemorySessionRepository(final SessionAggregatesFactory sessionAggregatesFactory, final Optional<UserResolver> userResolver, final SessionAggregateDTOAssembler dtoAssembler) {
        this.sessionAggregatesFactory = sessionAggregatesFactory;
        this.userResolver = userResolver.orElse(null);
        this.dtoAssembler = dtoAssembler;
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

    public <SADTO extends SessionAggregateDTO<?, ?, ?, SA>> SADTO getDto(final SessionId id, final UserId userId) {
        var sessionAggregate = sessionAggregates.get(userId)
                .get(id);
        return (SADTO) getDtoAssembler()
                .assemble(sessionAggregate);
    }

    public void save(SA sessionAggregate) {
        var events = sessionAggregate.flushEvents();

        innerSave(sessionAggregate);
        rememberAsActiveSession(sessionAggregate);

        publishEvents(events);
    }

    private void innerSave(final SA sessionAggregate) {
        var sessionAggregateCopy = (SA) getSessionAggregatesFactory()
                .createDeepCopy(sessionAggregate);
        getSessionAggregates()
                .put(sessionAggregateCopy.getId(), sessionAggregateCopy);
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
        var activeSessionId = getActiveSessionId();
        if (activeSessionId == null) {
            throw new NoActiveSessionException("there's no active session");
        }
        var activeSessionCopy = (SA) get(activeSessionId);
        return activeSessionCopy;
    }
}

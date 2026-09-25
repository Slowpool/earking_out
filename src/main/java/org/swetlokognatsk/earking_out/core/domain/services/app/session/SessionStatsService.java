package org.swetlokognatsk.earking_out.core.domain.services.app.session;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionId;

public abstract class SessionStatsService<E extends Exercise, ESS extends ExtendedSessionStats<E>, A extends SessionStatsAggregator> {
    public abstract ESS aggregate(SessionId sessionId);
}

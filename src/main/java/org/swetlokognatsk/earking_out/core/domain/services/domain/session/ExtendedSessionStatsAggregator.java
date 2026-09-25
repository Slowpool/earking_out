package org.swetlokognatsk.earking_out.core.domain.services.domain.session;

import org.swetlokognatsk.earking_out.core.domain.events.EventStream;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionId;
import org.swetlokognatsk.earking_out.core.domain.model.session.perfectpitch.ExtendedSessionStats;

// business logic for
public abstract class ExtendedSessionStatsAggregator<E extends Exercise, ESS extends ExtendedSessionStats<E>> {
    // yet passing the whole eventStream in-memory is fine, though for global stats it should be passed in batch mode
    public abstract ESS aggregate(EventStream<SessionId> eventStream);
}

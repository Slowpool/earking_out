package org.swetlokognatsk.earking_out.core.domain.services.app.session;

import org.swetlokognatsk.earking_out.core.domain.events.DomainEvent;
import org.swetlokognatsk.earking_out.core.domain.events.EventStream;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionId;
import org.swetlokognatsk.earking_out.core.domain.model.session.perfectpitch.ExtendedSessionStats;
import org.swetlokognatsk.earking_out.core.domain.services.domain.session.ExtendedSessionStatsAggregator;
import org.swetlokognatsk.earking_out.core.ports.eventsourcing.EventStore;

// TODO denote this semantics somewhere:
// SessionStats - common puzzle stats (number of perfectly completed puzzles, number of completed puzzles)
// ExtendedSessionStats - exercise specific session stats, for instance if it's PERFECT_PITCH exercise, then extended session stats may be the list of notes and statistics for each specific note
public abstract class ExtendedSessionStatsService<E extends Exercise, ESS extends ExtendedSessionStats<E>, ESSA extends ExtendedSessionStatsAggregator<E, ESS>> {

    protected final ESSA statsAggregator;
    protected final EventStore eventStore;

    public ExtendedSessionStatsService(final ESSA statsAggregator, final EventStore eventStore) {
        this.statsAggregator = statsAggregator;
        this.eventStore = eventStore;
    }

    public final ESS aggregate(final SessionId sessionId) {
        var domainEvents = eventStore.getAllEvents(sessionId);;
        return statsAggregator.aggregate(domainEvents);
    }
}

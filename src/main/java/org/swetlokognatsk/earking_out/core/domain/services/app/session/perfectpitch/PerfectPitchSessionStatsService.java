package org.swetlokognatsk.earking_out.core.domain.services.app.session.perfectpitch;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.PerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.session.perfectpitch.PerfectPitchSessionStats;
import org.swetlokognatsk.earking_out.core.domain.services.app.session.ExtendedSessionStatsService;
import org.swetlokognatsk.earking_out.core.domain.services.domain.session.perfectpitch.PerfectPitchSessionStatsAggregator;
import org.swetlokognatsk.earking_out.core.ports.eventsourcing.EventStore;

public final class PerfectPitchSessionStatsService<E extends PerfectPitchExercise> extends ExtendedSessionStatsService<E, PerfectPitchSessionStats<E>, PerfectPitchSessionStatsAggregator<E>> {

    public PerfectPitchSessionStatsService(final PerfectPitchSessionStatsAggregator<E> statsAggregator, final EventStore eventStore) {
        super(statsAggregator, eventStore);
    }
}

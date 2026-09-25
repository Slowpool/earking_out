package org.swetlokognatsk.earking_out.core.domain.services.domain.session.perfectpitch;

import org.swetlokognatsk.earking_out.core.domain.events.EventStream;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.PerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionId;
import org.swetlokognatsk.earking_out.core.domain.model.session.perfectpitch.PerfectPitchSessionStats;
import org.swetlokognatsk.earking_out.core.domain.services.domain.session.ExtendedSessionStatsAggregator;

public final class PerfectPitchSessionStatsAggregator<E extends PerfectPitchExercise> extends ExtendedSessionStatsAggregator<E, PerfectPitchSessionStats<E>> {

    public PerfectPitchSessionStats<E> aggregate(final EventStream<SessionId> eventStream) {

    }

}

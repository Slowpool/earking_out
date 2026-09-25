package org.swetlokognatsk.earking_out.core.domain.services.app.session.perfectpitch;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.PerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionId;
import org.swetlokognatsk.earking_out.core.domain.model.session.perfectpitch.PerfectPitchSessionStats;
import org.swetlokognatsk.earking_out.core.domain.services.app.session.SessionStatsService;

public final class PerfectPitchSessionStatsService<E extends PerfectPitchExercise> extends SessionStatsService<E, PerfectPitchSessionStats<E>> {

    public PerfectPitchSessionStats<E> aggregate(final SessionId sessionId) {
        // TODO
        return null;
    }
}

package org.swetlokognatsk.earking_out.core.domain.model.session.perfectpitch;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.PerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionId;

import java.util.Objects;

// TODO yet it's rather dto than value object
public final class PerfectPitchSessionStats<E extends PerfectPitchExercise> extends ExtendedSessionStats<E> {

    public final PerfectPitchNoteStats[] notesStats;

    public PerfectPitchSessionStats(final SessionId sessionId, final PerfectPitchNoteStats[] notesStats) {
        super(sessionId);
        this.notesStats = Objects.requireNonNull(notesStats);
    }
}

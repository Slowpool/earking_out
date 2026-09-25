package org.swetlokognatsk.earking_out.core.domain.services.app.session;

import java.util.Objects;
import org.swetlokognatsk.earking_out.core.domain.model.base.ValueObject;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionId;

public abstract class ExtendedSessionStats<E extends Exercise> extends ValueObject {

    public final SessionId sessionId;

    public ExtendedSessionStats(final SessionId sessionId) {
        this.sessionId = Objects.requireNonNull(sessionId);
    }
}

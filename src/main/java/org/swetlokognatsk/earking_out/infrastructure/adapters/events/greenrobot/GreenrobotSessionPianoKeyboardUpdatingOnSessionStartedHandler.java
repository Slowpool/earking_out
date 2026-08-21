package org.swetlokognatsk.earking_out.infrastructure.adapters.events.greenrobot;

import org.swetlokognatsk.earking_out.core.domain.events.handlers.SessionPianoKeyboardUpdatingOnSessionStartedHandler;
import org.swetlokognatsk.earking_out.core.domain.events.session.SessionStartedEvent;

public final class GreenrobotSessionPianoKeyboardUpdatingOnSessionStartedHandler extends GreenrobotEventHandler<SessionStartedEvent, SessionPianoKeyboardUpdatingOnSessionStartedHandler> {

    public GreenrobotSessionPianoKeyboardUpdatingOnSessionStartedHandler(final SessionPianoKeyboardUpdatingOnSessionStartedHandler domainHandler) {
        super(domainHandler);
    }
}

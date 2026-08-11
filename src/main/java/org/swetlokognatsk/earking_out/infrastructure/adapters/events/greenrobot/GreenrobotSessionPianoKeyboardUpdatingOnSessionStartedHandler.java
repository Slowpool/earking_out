package org.swetlokognatsk.earking_out.infrastructure.adapters.events.greenrobot;

import org.greenrobot.eventbus.Subscribe;
import org.swetlokognatsk.earking_out.core.domain.events.handlers.SessionPianoKeyboardUpdatingOnSessionStartedHandler;
import org.swetlokognatsk.earking_out.core.domain.events.session.HintRepeatingRequestedEvent;
import org.swetlokognatsk.earking_out.core.domain.events.session.SessionStartedEvent;

public class GreenrobotSessionPianoKeyboardUpdatingOnSessionStartedHandler extends GreenrobotEventHandler<SessionPianoKeyboardUpdatingOnSessionStartedHandler> {

    public GreenrobotSessionPianoKeyboardUpdatingOnSessionStartedHandler(final SessionPianoKeyboardUpdatingOnSessionStartedHandler domainHandler) {
        super(domainHandler);
    }

    @Subscribe
    public void handle(final SessionStartedEvent e) {
        domainHandler.handle(e);
    }
}

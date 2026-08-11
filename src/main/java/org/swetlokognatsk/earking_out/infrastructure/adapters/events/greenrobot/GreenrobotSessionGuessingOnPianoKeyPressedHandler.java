package org.swetlokognatsk.earking_out.infrastructure.adapters.events.greenrobot;

import org.greenrobot.eventbus.Subscribe;
import org.swetlokognatsk.earking_out.core.domain.events.handlers.SessionGuessingOnPianoKeyPressedHandler;
import org.swetlokognatsk.earking_out.core.domain.events.pianokeyboard.PianoKeyPressedEvent;

public class GreenrobotSessionGuessingOnPianoKeyPressedHandler extends GreenrobotEventHandler<SessionGuessingOnPianoKeyPressedHandler> {

    public GreenrobotSessionGuessingOnPianoKeyPressedHandler(final SessionGuessingOnPianoKeyPressedHandler domainHandler) {
        super(domainHandler);
    }

    @Subscribe
    public void handle(final PianoKeyPressedEvent e) {
        domainHandler.handle(e);
    }

}

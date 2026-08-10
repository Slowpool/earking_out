package org.swetlokognatsk.earking_out.core.domain.events.handlers;

import org.swetlokognatsk.earking_out.core.domain.events.pianokeyboard.PianoKeyPressedEvent;
import org.swetlokognatsk.earking_out.core.domain.services.app.SessionService;

public final class SessionGuessingOnPianoKeyPressedHandler {

    private final SessionService sessionService;

    public SessionGuessingOnPianoKeyPressedHandler(final SessionService sessionService) {
        this.sessionService = sessionService;
    }

    public void handlePianoKeyPressedEvent(final PianoKeyPressedEvent event) {
        // // TODO SessionGuessingOnPianoKeyPressedHandler
        // if (...) {
        //     sessionService.
        // }
    }
}

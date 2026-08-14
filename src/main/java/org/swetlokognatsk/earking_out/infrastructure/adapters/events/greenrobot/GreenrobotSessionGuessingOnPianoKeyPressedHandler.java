package org.swetlokognatsk.earking_out.infrastructure.adapters.events.greenrobot;

import org.swetlokognatsk.earking_out.core.domain.events.handlers.AudioPerfectPitchGuessingOnPianoKeyPressedHandler;
import org.swetlokognatsk.earking_out.core.domain.events.pianokeyboard.PianoKeyPressedEvent;

public class GreenrobotSessionGuessingOnPianoKeyPressedHandler extends GreenrobotEventHandler<PianoKeyPressedEvent, AudioPerfectPitchGuessingOnPianoKeyPressedHandler> {

    public GreenrobotSessionGuessingOnPianoKeyPressedHandler(final AudioPerfectPitchGuessingOnPianoKeyPressedHandler domainHandler) {
        super(domainHandler);
    }

}

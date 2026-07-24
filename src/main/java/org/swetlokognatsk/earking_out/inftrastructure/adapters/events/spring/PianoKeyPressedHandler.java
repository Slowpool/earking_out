package org.swetlokognatsk.earking_out.inftrastructure.adapters.events.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.swetlokognatsk.earking_out.core.domain.events.pianokeyboard.PianoKeyPressedEvent;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;
import org.swetlokognatsk.earking_out.core.ports.piano.PianoKeySoundsPlayer;

@Component
public class PianoKeyPressedHandler {
    // TODO sort out these yellow lines
    @Autowired
    @Lazy
    private PianoKeySoundsPlayer pianoKeySoundsPlayer;

    @EventListener
    // TODO what's wrong
    public void handlePianoKeyPressedEvent(final PianoKeyPressedEvent event) {
        var pianoKeyNumber = event.pianoKeyNumber;
        if (shouldPlaySound(pianoKeyNumber)) {
            pianoKeySoundsPlayer.play(pianoKeyNumber);
        }
    }

    protected boolean shouldPlaySound(final PianoKeyNumber pianoKeyNumber)  {
        return false;
    }

}

package org.swetlokognatsk.earking_out.inftrastructure.adapters.events.spring;

import org.springframework.context.annotation.Lazy;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.swetlokognatsk.earking_out.core.domain.events.pianokeyboard.PianoKeyPressedEvent;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;
import org.swetlokognatsk.earking_out.core.ports.piano.PianoKeySoundsPlayer;

@Component
public final class PianoKeyPressedHandler {
    private final PianoKeySoundsPlayer pianoKeySoundsPlayer;

    @Lazy
    public PianoKeyPressedHandler(final PianoKeySoundsPlayer pianoKeySoundsPlayer) {
        this.pianoKeySoundsPlayer = pianoKeySoundsPlayer;
    }

    @EventListener
    public void handlePianoKeyPressedEvent(final PianoKeyPressedEvent event) {
        var pianoKeyNumber = event.pianoKeyNumber;
        if (shouldPlaySound(pianoKeyNumber)) {
            pianoKeySoundsPlayer.stopAndPlay(pianoKeyNumber);
        }
    }

    protected boolean shouldPlaySound(final PianoKeyNumber pianoKeyNumber) {
        // TODO pull from config
        return false;
    }

}

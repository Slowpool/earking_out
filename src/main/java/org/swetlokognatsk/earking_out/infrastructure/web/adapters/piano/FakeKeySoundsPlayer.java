package org.swetlokognatsk.earking_out.infrastructure.web.adapters.piano;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;
import org.swetlokognatsk.earking_out.core.ports.piano.PianoKeySoundsPlayer;
import org.swetlokognatsk.earking_out.infrastructure.annotations.WebService;

// TODO yet it's just a latch
@WebService
@Primary
public class FakeKeySoundsPlayer implements PianoKeySoundsPlayer {

    public void play(final PianoKeyNumber keyNumber) {
    }

    public void stop(final PianoKeyNumber keyNumber) {
    }

    public void stopAndPlay(final PianoKeyNumber keyNumber) {
    }

}

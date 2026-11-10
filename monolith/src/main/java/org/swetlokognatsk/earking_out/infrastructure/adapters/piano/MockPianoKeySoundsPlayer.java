package org.swetlokognatsk.earking_out.infrastructure.adapters.piano;

import java.io.Serializable;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import static org.swetlokognatsk.earking_out.SpringProfiles.*;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;
import org.swetlokognatsk.earking_out.core.ports.piano.PianoKeySoundsPlayer;

@Component
@Profile({ WEB, TEST })
public class MockPianoKeySoundsPlayer implements PianoKeySoundsPlayer, Serializable {
    public boolean playIsPressed = false;
    public boolean stopIsPressed = false;
    public boolean stopAndPlayIsPressed = false;

    public void play(final PianoKeyNumber keyNumber) {
        playIsPressed = true;
    }

    public void stop(final PianoKeyNumber keyNumber) {
        stopIsPressed = true;
    }

    public void stopAndPlay(final PianoKeyNumber keyNumber) {
        stopAndPlayIsPressed = true;
    }
}

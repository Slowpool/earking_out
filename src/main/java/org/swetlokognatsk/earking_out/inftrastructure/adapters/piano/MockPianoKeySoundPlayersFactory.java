package org.swetlokognatsk.earking_out.inftrastructure.adapters.piano;

import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;
import org.swetlokognatsk.earking_out.core.ports.piano.PianoKeySoundPlayersFactory;
import org.swetlokognatsk.earking_out.core.ports.sounds.SoundPlayer;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.sounds.MockSoundPlayer;

public class MockPianoKeySoundPlayersFactory implements PianoKeySoundPlayersFactory {

    public SoundPlayer create(final PianoKeyNumber keyNumber) {
        return new MockSoundPlayer();
    }

}

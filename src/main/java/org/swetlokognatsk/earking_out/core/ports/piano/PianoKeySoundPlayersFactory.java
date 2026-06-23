package org.swetlokognatsk.earking_out.core.ports.piano;

import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;
import org.swetlokognatsk.earking_out.core.ports.sounds.SoundPlayer;

public interface PianoKeySoundPlayersFactory {
    SoundPlayer create(final PianoKeyNumber keyNumber);
}

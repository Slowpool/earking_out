package org.swetlokognatsk.earking_out.core.ports.sounds;

import java.util.Map;

import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;

/** @param PKS - PianoKeySound */
public interface PianoKeySounds<PKS> {
    Map<PianoKeyNumber, PKS> getMap();
}

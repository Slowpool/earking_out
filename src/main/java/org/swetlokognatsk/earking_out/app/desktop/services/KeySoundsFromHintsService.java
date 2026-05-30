package org.swetlokognatsk.earking_out.app.desktop.services;

import java.util.HashMap;
import java.util.Map;
import org.swetlokognatsk.earking_out.app.desktop.helpers.PianoKeysHelper;
import org.swetlokognatsk.earking_out.core.domain.model.Solution;
import org.swetlokognatsk.earking_out.core.domain.model.music.Invariants;
import org.swetlokognatsk.earking_out.core.ports.DI;
import org.swetlokognatsk.earking_out.core.ports.hints.perfectPitch.AudioPerfectPitchHints;

public class KeySoundsFromHintsService implements KeySoundsService {
    public Map<Byte, String> getMap() {
        var map = new HashMap<Byte, String>();
        var audioHints = DI.get(AudioPerfectPitchHints.class);

        PianoKeysHelper.forEachKey((Byte keyNumber) -> {
            // yes, a bit awkward, but that's how the cookie crumbles
            String key = String.valueOf(keyNumber);
            Solution solution = new Solution(key);
            String hintValue = audioHints.find(solution).getValue();
            map.put(keyNumber, hintValue);
        });

        return map;
    }

}

package org.swetlokognatsk.earking_out.app.desktop.services;

import java.util.HashMap;
import java.util.Map;
import org.swetlokognatsk.earking_out.core.domain.model.Solution;
import org.swetlokognatsk.earking_out.core.domain.model.music.Invariants;
import org.swetlokognatsk.earking_out.core.ports.DI;
import org.swetlokognatsk.earking_out.core.ports.hints.perfectPitch.AudioPerfectPitchHints;

public class KeySoundsFromHintsService implements KeySoundsService {
    public Map<Byte, String> getMap() {
        var map = new HashMap<Byte, String>();
        var audioHints = DI.get(AudioPerfectPitchHints.class);

        String hintValue;
        Solution solution;
        String key;
        // TODO this cycle is used like third-fourth time, how to refactor it to follow DRY?
        for (Byte keyNumber = Invariants.FIRST_NOTE_NUMBER; keyNumber < Invariants.PIANO_KEYS_NUMBER + Invariants.FIRST_NOTE_NUMBER; keyNumber++) {
            // yes, a bit awkward, but that's how the cookie crumbles
            key = String.valueOf(keyNumber);
            solution = new Solution(key);
            hintValue = audioHints.find(solution).getValue();
            map.put(keyNumber, hintValue);
        }

        return map;
    }

}

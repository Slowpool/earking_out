package org.swetlokognatsk.earking_out.inftrastructure.adapters.sounds;

import java.util.HashMap;
import java.util.Map;
import org.swetlokognatsk.earking_out.app.desktop.helpers.PianoKeysHelper;
import org.swetlokognatsk.earking_out.core.domain.model.Solution;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;
import org.swetlokognatsk.earking_out.core.ports.DI;
import org.swetlokognatsk.earking_out.core.ports.hints.finders.perfectpitch.PerfectPitchHints;
import org.swetlokognatsk.earking_out.core.ports.sounds.PianoKeySounds;

public final class PianoKeySoundsFromHints implements PianoKeySounds<String> {
    public Map<PianoKeyNumber, String> getMap() {
        var map = new HashMap<PianoKeyNumber, String>();
        var audioHints = DI.get(PerfectPitchHints.class);

        PianoKeysHelper.forEachKey((PianoKeyNumber keyNumber) -> {
            // yes, a bit awkward, but that's how the cookie crumbles
            String key = String.valueOf(keyNumber.value);
            Solution solution = new Solution(key);
            String hintValue = audioHints.find(solution).getValue();
            map.put(keyNumber, hintValue);
        });

        return map;
    }

}

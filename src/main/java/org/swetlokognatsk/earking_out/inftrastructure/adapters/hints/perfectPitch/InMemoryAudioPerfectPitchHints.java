package org.swetlokognatsk.earking_out.inftrastructure.adapters.hints.perfectPitch;

import java.util.HashMap;
import java.util.Map;
import org.swetlokognatsk.earking_out.core.domain.model.Solution;
import org.swetlokognatsk.earking_out.core.domain.model.hints.UsualHint;
import org.swetlokognatsk.earking_out.core.domain.model.music.Invariants;
import org.swetlokognatsk.earking_out.core.ports.hints.perfectPitch.AudioPerfectPitchHints;

public class InMemoryAudioPerfectPitchHints implements AudioPerfectPitchHints {
    protected Map<String, UsualHint> hints = new HashMap<>();

    // TODO Java resources - i think it solves the problem of direct access to file system
    public InMemoryAudioPerfectPitchHints() {
        String key;
        String filePath;
        UsualHint hint;
        for (Byte keyNumber = Invariants.FIRST_NOTE_NUMBER; keyNumber < Invariants.PIANO_KEYS_NUMBER + Invariants.FIRST_NOTE_NUMBER; keyNumber++) {
            key = String.valueOf(keyNumber);
            // TODO path should be taken from config?
            filePath = String.format("/piano_keys/key%s.wav", key);
            hint = new UsualHint(filePath);
            hints.put(key, hint);
        }
    }

    public UsualHint find(Solution solution) {
        return hints.get(solution.value);
    }
}

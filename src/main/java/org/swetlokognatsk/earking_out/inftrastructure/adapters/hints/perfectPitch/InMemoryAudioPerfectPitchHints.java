package org.swetlokognatsk.earking_out.inftrastructure.adapters.hints.perfectPitch;

import java.util.HashMap;
import java.util.Map;

import org.swetlokognatsk.earking_out.app.desktop.helpers.PianoKeysHelper;
import org.swetlokognatsk.earking_out.core.domain.model.Solution;
import org.swetlokognatsk.earking_out.core.domain.model.hints.UsualHint;
import org.swetlokognatsk.earking_out.core.domain.model.music.Invariants;
import org.swetlokognatsk.earking_out.core.ports.hints.perfectPitch.AudioPerfectPitchHints;

public class InMemoryAudioPerfectPitchHints implements AudioPerfectPitchHints {
    protected Map<String, UsualHint> hints = new HashMap<>();

    // TODO Java resources - i think it solves the problem of direct access to file system
    public InMemoryAudioPerfectPitchHints() {
        PianoKeysHelper.forEachKey((Byte keyNumber) -> {
            String key = String.valueOf(keyNumber);
            // TODO path should be taken from config?
            String filePath = String.format("/piano_keys/key%s.wav", key);
            UsualHint hint = new UsualHint(filePath);
            hints.put(key, hint);
        });
    }

    public UsualHint find(Solution solution) {
        return hints.get(solution.value);
    }
}

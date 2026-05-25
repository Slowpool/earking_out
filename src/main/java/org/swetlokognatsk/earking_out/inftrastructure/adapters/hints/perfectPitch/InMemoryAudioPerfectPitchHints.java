package org.swetlokognatsk.earking_out.inftrastructure.adapters.hints.perfectPitch;

import java.util.HashMap;
import java.util.Map;

import org.swetlokognatsk.earking_out.core.domain.model.Solution;
import org.swetlokognatsk.earking_out.core.domain.model.hints.UsualHint;
import org.swetlokognatsk.earking_out.core.ports.hints.perfectPitch.AudioPerfectPitchHints;

public class InMemoryAudioPerfectPitchHints implements AudioPerfectPitchHints {
    protected Map<String, UsualHint> hints = new HashMap<>();

    public InMemoryAudioPerfectPitchHints() {
        // TODO Java resources - i think it solves the problem of direct access to file system
        hints.put("4", new UsualHint("/key4.wav"));
        hints.put("5", new UsualHint("/key5.wav"));
    }

    public UsualHint find(Solution solution) {
        return hints.get(solution.value);
    }
}

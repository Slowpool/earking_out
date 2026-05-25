package org.swetlokognatsk.earking_out.inftrastructure.adapters.hints.perfectPitch;

import java.util.HashMap;
import java.util.Map;
import org.swetlokognatsk.earking_out.core.domain.model.Solution;
import org.swetlokognatsk.earking_out.core.domain.model.hints.UsualHint;
import org.swetlokognatsk.earking_out.core.ports.hints.perfectPitch.VisualPerfectPitchHints;

// TODO
public class InMemoryVisualPerfectPitchHints implements VisualPerfectPitchHints {
    protected Map<String, UsualHint> hints = new HashMap<>();

    public InMemoryVisualPerfectPitchHints() {
    }

    public UsualHint find(Solution solution) {
        return hints.get(solution.value);
    }
}

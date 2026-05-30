package org.swetlokognatsk.earking_out.inftrastructure.adapters.hints.perfectPitch;

import org.swetlokognatsk.earking_out.core.domain.model.Solution;
import org.swetlokognatsk.earking_out.core.domain.model.hints.UsualHint;
import org.swetlokognatsk.earking_out.core.ports.hints.perfectPitch.VisualPerfectPitchHints;

public final class FakeVisualPerfectPitchHints implements VisualPerfectPitchHints {
    public static final String EXERCISE = "perfect_pitch";
    public static final String TYPE = "visual";

    public UsualHint find(Solution solution) {
        var hint = EXERCISE + " " + TYPE + " hint for #" + solution.value;
        return new UsualHint(hint);
    }
}

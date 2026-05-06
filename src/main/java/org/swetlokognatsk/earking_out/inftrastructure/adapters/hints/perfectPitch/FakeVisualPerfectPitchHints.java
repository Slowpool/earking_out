package org.swetlokognatsk.earking_out.inftrastructure.adapters.hints.perfectPitch;

import org.swetlokognatsk.earking_out.core.domain.model.Hint;
import org.swetlokognatsk.earking_out.core.domain.model.Solution;
import org.swetlokognatsk.earking_out.core.domain.model.UsualHint;
import org.swetlokognatsk.earking_out.core.ports.hints.IFiniteHintFinder;

public class FakeVisualPerfectPitchHints implements IFiniteHintFinder {
    public static final String EXERCISE = "perfect_pitch";
    public static final String TYPE = "visual";

    // TODO fix this `<T extends Hint> T` and `return (T)anyObject;` pattern everywhere
    public <T extends Hint> T find(Solution solution) {
        var hint = EXERCISE + " " + TYPE + " hint for #" + solution.value;
        return (T)new UsualHint(hint);
    }

}

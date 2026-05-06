package org.swetlokognatsk.earking_out.inftrastructure.adapters.hints.perfectPitch;

import org.swetlokognatsk.earking_out.core.domain.model.Hint;
import org.swetlokognatsk.earking_out.core.domain.model.Solution;
import org.swetlokognatsk.earking_out.core.domain.model.UsualHint;
import org.swetlokognatsk.earking_out.core.ports.hints.IFiniteHintFinder;

// TODO move to tests project somehow. upd: that's DI issue, fix after setting up DI, setup Fake classes on tests setup stage
public class FakeAudioPerfectPitchHints implements IFiniteHintFinder {
    public static final String EXERCISE = "perfect_pitch";
    public static final String TYPE = "audio";

    public <T extends Hint> T find(Solution solution) {
        var hint = EXERCISE + " " + TYPE + " hint for #" + solution.value;
        return (T)new UsualHint(hint);
    }
}

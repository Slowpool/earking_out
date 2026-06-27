package org.swetlokognatsk.earking_out.inftrastructure.adapters.hints.demonstrators.perfectpitch;

import org.swetlokognatsk.earking_out.core.domain.model.hints.perfectpitch.PerfectPitchHint;
import org.swetlokognatsk.earking_out.core.domain.model.solutions.Solution;
import org.swetlokognatsk.earking_out.core.ports.hints.finders.perfectpitch.PerfectPitchHintFinder;

// TODO move to tests project somehow. upd: that's DI issue, fix after setting up DI, setup Fake classes on tests setup stage
public final class FakeAudioPerfectPitchHints implements PerfectPitchHintFinder {
    public static final String EXERCISE = "perfect_pitch";
    public static final String TYPE = "audio";

    public PerfectPitchHint find(Solution solution) {
        return new PerfectPitchHint(solution);
    }
}

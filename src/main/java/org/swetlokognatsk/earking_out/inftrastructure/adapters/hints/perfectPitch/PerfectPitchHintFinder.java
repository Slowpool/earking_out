package org.swetlokognatsk.earking_out.inftrastructure.adapters.hints.perfectPitch;

import org.swetlokognatsk.earking_out.core.ports.hints.FiniteHintFinder;
import org.swetlokognatsk.earking_out.core.ports.hints.perfectPitch.IPerfectPitchHintFinder;

public class PerfectPitchHintFinder extends HintFinderByType implements IPerfectPitchHintFinder {
    protected Class<? extends FiniteHintFinder> getVisualHintFinderClass() {
        return FakeVisualPerfectPitchHints.class;
    }

    protected Class<? extends FiniteHintFinder> getAudioHintFinderClass() {
        return FakeAudioPerfectPitchHints.class;
    }
}

package org.swetlokognatsk.earking_out.inftrastructure.adapters.hints.perfectPitch;

import org.swetlokognatsk.earking_out.core.ports.hints.IFiniteHintFinder;
import org.swetlokognatsk.earking_out.core.ports.hints.perfectPitch.IPerfectPitchHintFinder;
public class PerfectPitchHintFinder extends HintFinderByType implements IPerfectPitchHintFinder {
    protected Class<? extends IFiniteHintFinder> getVisualHintFinderClass() {
        return FakeVisualPerfectPitchHints.class;
    }

    protected Class<? extends IFiniteHintFinder> getAudioHintFinderClass() {
        return FakeAudioPerfectPitchHints.class;
    }
}

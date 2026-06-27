package org.swetlokognatsk.earking_out.inftrastructure.adapters.hints.demonstrators;

import org.swetlokognatsk.earking_out.core.domain.model.hints.Hint;
import org.swetlokognatsk.earking_out.core.domain.model.hints.perfectpitch.AudioPerfectPitchHint;
import org.swetlokognatsk.earking_out.core.domain.model.hints.perfectpitch.VisualPerfectPitchHint;
import org.swetlokognatsk.earking_out.core.ports.DI;
import org.swetlokognatsk.earking_out.core.ports.hints.demonstrators.EndHintDemonstrator;
import org.swetlokognatsk.earking_out.core.ports.hints.demonstrators.HintDemonstrator;
import org.swetlokognatsk.earking_out.core.ports.hints.demonstrators.perfectpitch.AudioPerfectPitchHintDemonstrator;
import org.swetlokognatsk.earking_out.core.ports.hints.demonstrators.perfectpitch.VisualPerfectPitchHintDemonstrator;

public final class HintDemonstratorDelegator implements HintDemonstrator<Hint> {

    public void demonstrate(final Hint hint) {
        var specificHintDemonstrator = createSpecificHintDemonstrator(hint);
        specificHintDemonstrator.demonstrate(hint);
    }

    protected <H extends Hint> EndHintDemonstrator<H> createSpecificHintDemonstrator(final H hint) {
        var specificHintDemonstrator = switch (hint) {
            case AudioPerfectPitchHint _h -> AudioPerfectPitchHintDemonstrator.class;
            case VisualPerfectPitchHint _h -> VisualPerfectPitchHintDemonstrator.class;
            default -> throw new IllegalArgumentException("unknown hint type");
        };
        return (EndHintDemonstrator<H>) DI.get(specificHintDemonstrator);
    }
}

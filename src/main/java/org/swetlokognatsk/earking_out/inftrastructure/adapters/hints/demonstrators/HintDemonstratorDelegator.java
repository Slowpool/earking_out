package org.swetlokognatsk.earking_out.inftrastructure.adapters.hints.demonstrators;

import org.swetlokognatsk.earking_out.core.domain.model.solutions.Solution;
import org.swetlokognatsk.earking_out.core.domain.model.solutions.sound.SoundHarmonicIntervalSolution;
import org.swetlokognatsk.earking_out.core.domain.model.solutions.sound.SingleSoundSolution;
import org.swetlokognatsk.earking_out.core.ports.DI;
import org.swetlokognatsk.earking_out.core.ports.hints.demonstrators.EndHintDemonstrator;
import org.swetlokognatsk.earking_out.core.ports.hints.demonstrators.HintDemonstrator;
import org.swetlokognatsk.earking_out.core.ports.hints.demonstrators.sound.SingleSoundHintDemonstrator;

public final class HintDemonstratorDelegator implements HintDemonstrator<Solution> {

    public void demonstrateHint(final Solution solution) {
        var specificHintDemonstrator = createSpecificHintDemonstrator(solution);
        specificHintDemonstrator.demonstrateHint(solution);
    }

    // TODO grasp why generics here are useless
    protected <S extends Solution> EndHintDemonstrator<S> createSpecificHintDemonstrator(final S solution) {
        var specificHintDemonstrator = switch (solution) {
        case SingleSoundSolution _s -> SingleSoundHintDemonstrator.class;
        // case SoundHarmonicIntervalSolution _s -> AudioClipSoundHarmonicIntervalHintDemonstrator.class;
        default -> throw new IllegalArgumentException("unknown solution type");
        };
        return (EndHintDemonstrator<S>) DI.get(specificHintDemonstrator);
    }
}

package org.swetlokognatsk.earking_out.inftrastructure.adapters.hints.perfectPitch;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.PerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.puzzles.PerfectPitchPuzzle;
import org.swetlokognatsk.earking_out.core.domain.model.hints.Hint;
import org.swetlokognatsk.earking_out.core.ports.hints.FiniteHintFinder;
import org.swetlokognatsk.earking_out.core.ports.hints.perfectPitch.PerfectPitchHintFinder;

public abstract class FakePerfectPitchHintFinder<H extends Hint, P extends PerfectPitchPuzzle<?, ?, H, ?>> extends HintFinderByType implements PerfectPitchHintFinder<H, P> {
    protected Class<? extends FiniteHintFinder> getVisualHintFinderClass() {
        return FakeVisualPerfectPitchHints.class;
    }

    protected Class<? extends FiniteHintFinder> getAudioHintFinderClass() {
        return FakeAudioPerfectPitchHints.class;
    }
}

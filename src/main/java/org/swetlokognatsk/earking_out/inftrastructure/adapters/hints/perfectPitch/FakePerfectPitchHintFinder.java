package org.swetlokognatsk.earking_out.inftrastructure.adapters.hints.perfectPitch;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.PerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.puzzles.PerfectPitchPuzzle;
import org.swetlokognatsk.earking_out.core.domain.model.hints.Hint;
import org.swetlokognatsk.earking_out.core.domain.model.hints.UsualHint;
import org.swetlokognatsk.earking_out.core.ports.hints.FiniteHintFinder;
import org.swetlokognatsk.earking_out.core.ports.hints.perfectPitch.PerfectPitchHintFinder;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.hints.HintFinderMediatorByType;

// TODO refactoring so that different Hint types could be used for visual and audio types
public abstract class FakePerfectPitchHintFinder<P extends PerfectPitchPuzzle<?, ?, UsualHint, ?>> extends HintFinderMediatorByType<UsualHint, P> implements PerfectPitchHintFinder<UsualHint, P> {
    protected Class<FiniteHintFinder<UsualHint>> getVisualHintFinderClass() {
        return (Class<FiniteHintFinder<UsualHint>>)FakeVisualPerfectPitchHints.class;
    }

    protected Class<FiniteHintFinder<UsualHint>> getAudioHintFinderClass() {
        return FakeAudioPerfectPitchHints.class;
    }
}

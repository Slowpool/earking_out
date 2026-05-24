package org.swetlokognatsk.earking_out.core.ports.hints.perfectPitch;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.puzzles.PerfectPitchPuzzle;
import org.swetlokognatsk.earking_out.core.domain.model.hints.Hint;
import org.swetlokognatsk.earking_out.core.ports.hints.HintFinder;

public interface PerfectPitchHintFinder<H extends Hint, P extends PerfectPitchPuzzle<?, ?, H, ?>> extends HintFinder<H, P> {
}

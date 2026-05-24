package org.swetlokognatsk.earking_out.core.ports.hints;

import org.swetlokognatsk.earking_out.core.domain.model.hints.Hint;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.Puzzle;

public interface HintFinder<H extends Hint, P extends Puzzle<?, ?, H, ?>> {
    H find(P puzzle);
}

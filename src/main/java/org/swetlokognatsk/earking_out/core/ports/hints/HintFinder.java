package org.swetlokognatsk.earking_out.core.ports.hints;

import org.swetlokognatsk.earking_out.core.domain.model.hints.Hint;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.Puzzle;

public interface HintFinder<H extends Hint, P extends Puzzle<?, ?, H, ?>> {
    // TODO consider using generic inside every IHintFinder for `Puzzle<?, ?, ?>` wherever possible
    H find(P puzzle);
}

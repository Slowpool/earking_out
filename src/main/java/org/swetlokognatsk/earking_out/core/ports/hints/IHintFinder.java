package org.swetlokognatsk.earking_out.core.ports.hints;

import org.swetlokognatsk.earking_out.core.domain.model.hints.Hint;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.Puzzle;

public interface IHintFinder {
    // TODO consider using generic inside every IHintFinder for `Puzzle<?, ?, ?>` wherever possible
    Hint find(Puzzle<?, ?, ?> puzzle);
}

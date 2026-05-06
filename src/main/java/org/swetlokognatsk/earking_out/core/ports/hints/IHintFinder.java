package org.swetlokognatsk.earking_out.core.ports.hints;

import org.swetlokognatsk.earking_out.core.domain.model.Hint;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.Puzzle;

public interface IHintFinder {
    // TODO using `Puzzle<?, ?, ?>` everywhere is awkward, isn't it?
    <T extends Hint> T find(Puzzle<?, ?, ?> puzzle);
}

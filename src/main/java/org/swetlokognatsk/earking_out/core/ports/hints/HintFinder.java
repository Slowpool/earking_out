package org.swetlokognatsk.earking_out.core.ports.hints;

import org.swetlokognatsk.earking_out.core.domain.model.Solution;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.hints.Hint;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.Puzzle;

public interface HintFinder {
    Hint find(final Exercise exercise, final Solution solution);
}

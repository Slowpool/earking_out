package org.swetlokognatsk.earking_out.core.ports.hints.finders;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.hints.Hint;
import org.swetlokognatsk.earking_out.core.domain.model.solutions.Solution;

public interface HintFinder<S extends Solution, H extends Hint> {
    H find(final S solution);
}

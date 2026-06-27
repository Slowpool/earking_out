package org.swetlokognatsk.earking_out.core.ports.hints.finders;

import org.swetlokognatsk.earking_out.core.domain.model.Solution;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.hints.Hint;

public interface HintFinder {
    Hint find(final Exercise exercise, final Solution solution);
}

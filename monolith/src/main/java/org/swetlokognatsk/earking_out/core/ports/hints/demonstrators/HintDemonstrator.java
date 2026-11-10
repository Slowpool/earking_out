package org.swetlokognatsk.earking_out.core.ports.hints.demonstrators;

import org.swetlokognatsk.earking_out.core.domain.model.solutions.Solution;

public abstract interface HintDemonstrator<S extends Solution> {
    void demonstrateHint(final S solution);
}

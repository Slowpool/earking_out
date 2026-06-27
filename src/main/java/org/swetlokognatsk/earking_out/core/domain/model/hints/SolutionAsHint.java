package org.swetlokognatsk.earking_out.core.domain.model.hints;

import org.swetlokognatsk.earking_out.core.domain.model.solutions.Solution;

public abstract class SolutionAsHint extends Hint {
    public final Solution hint;

    public SolutionAsHint(final Solution solution) {
        this.hint = solution;
    }
}

package org.swetlokognatsk.earking_out.core.ports.hints;

import org.swetlokognatsk.earking_out.core.domain.model.Hint;
import org.swetlokognatsk.earking_out.core.domain.model.Solution;

public interface IFiniteHintFinder {
    Hint find(Solution solution);
}

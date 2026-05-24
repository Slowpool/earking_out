package org.swetlokognatsk.earking_out.core.ports.hints;

import org.swetlokognatsk.earking_out.core.domain.model.Solution;
import org.swetlokognatsk.earking_out.core.domain.model.hints.Hint;

/**
 * It's called finite because that's the place where the finding logic itself happens, like skimming through files or accessing the in-memory HashMap.
 */
public interface FiniteHintFinder<H extends Hint> {
    H find(Solution solution);
}

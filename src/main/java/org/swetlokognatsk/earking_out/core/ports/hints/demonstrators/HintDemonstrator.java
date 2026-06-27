package org.swetlokognatsk.earking_out.core.ports.hints.demonstrators;

import org.swetlokognatsk.earking_out.core.domain.model.hints.Hint;

public abstract interface HintDemonstrator<H extends Hint> {
    void demonstrate(final H hint);
}

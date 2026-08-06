package org.swetlokognatsk.earking_out.core.domain.events.handlers;

import org.swetlokognatsk.earking_out.core.domain.events.session.HintRepeatingRequestedEvent;
import org.swetlokognatsk.earking_out.core.domain.model.solutions.Solution;
import org.swetlokognatsk.earking_out.core.ports.hints.demonstrators.HintDemonstrator;

// TODO handlers definitely must not be in core/domain
public final class HintDemonstratingOnHintRepeatingRequestedHandler {
    private final HintDemonstrator<Solution> hintDemonstrator;

    public HintDemonstratingOnHintRepeatingRequestedHandler(final HintDemonstrator<Solution> hintDemonstrator) {
        this.hintDemonstrator = hintDemonstrator;
    }

    public void handleHintRepeatingRequestedEvent(final HintRepeatingRequestedEvent event) {
        hintDemonstrator.demonstrateHint(event.puzzle.solution);
    }
}

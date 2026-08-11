package org.swetlokognatsk.earking_out.core.domain.events.handlers;

import org.swetlokognatsk.earking_out.core.domain.events.session.HintRepeatingRequestedEvent;
import org.swetlokognatsk.earking_out.core.domain.model.solutions.Solution;
import org.swetlokognatsk.earking_out.core.ports.hints.demonstrators.HintDemonstrator;

public final class HintDemonstratingOnHintRepeatingRequestedHandler implements DomainEventHandler<HintRepeatingRequestedEvent> {

    private final HintDemonstrator<Solution> hintDemonstrator;

    public HintDemonstratingOnHintRepeatingRequestedHandler(final HintDemonstrator<Solution> hintDemonstrator) {
        this.hintDemonstrator = hintDemonstrator;
    }

    public void handle(final HintRepeatingRequestedEvent event) {
        hintDemonstrator.demonstrateHint(event.puzzle.solution);
    }
}

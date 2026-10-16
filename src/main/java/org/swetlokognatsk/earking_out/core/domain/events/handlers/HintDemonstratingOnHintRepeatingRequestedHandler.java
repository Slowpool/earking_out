package org.swetlokognatsk.earking_out.core.domain.events.handlers;

import org.springframework.stereotype.Service;
import org.swetlokognatsk.earking_out.core.domain.events.session.HintRepeatingRequestedEvent;
import org.swetlokognatsk.earking_out.infrastructure.adapters.hints.demonstrators.HintDemonstratorDelegator;

@Service
public final class HintDemonstratingOnHintRepeatingRequestedHandler extends DomainEventHandler<HintRepeatingRequestedEvent> {

    private final HintDemonstratorDelegator hintDemonstrator;

    public HintDemonstratingOnHintRepeatingRequestedHandler(final HintDemonstratorDelegator hintDemonstrator) {
        this.hintDemonstrator = hintDemonstrator;
    }

    public void handle(final HintRepeatingRequestedEvent event) {
        hintDemonstrator.demonstrateHint(event.puzzle.solution);
    }
}

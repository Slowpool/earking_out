package org.swetlokognatsk.earking_out.core.domain.events.handlers;

import org.springframework.stereotype.Service;
import org.swetlokognatsk.earking_out.core.domain.events.session.NewPuzzleCreatedEvent;
import org.swetlokognatsk.earking_out.infrastructure.adapters.hints.demonstrators.HintDemonstratorDelegator;

@Service
public final class HintDemonstratingOnNewPuzzleCreatedHandler extends DomainEventHandler<NewPuzzleCreatedEvent> {

    private final HintDemonstratorDelegator hintDemonstrator;

    public HintDemonstratingOnNewPuzzleCreatedHandler(final HintDemonstratorDelegator hintDemonstrator) {
        this.hintDemonstrator = hintDemonstrator;
    }

    public void handle(final NewPuzzleCreatedEvent event) {
        hintDemonstrator.demonstrateHint(event.puzzle.solution);
    }

}

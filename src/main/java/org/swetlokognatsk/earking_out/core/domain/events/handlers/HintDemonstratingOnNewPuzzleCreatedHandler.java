package org.swetlokognatsk.earking_out.core.domain.events.handlers;

import org.swetlokognatsk.earking_out.core.domain.events.session.NewPuzzleCreatedEvent;
import org.swetlokognatsk.earking_out.core.domain.model.solutions.Solution;
import org.swetlokognatsk.earking_out.core.ports.hints.demonstrators.HintDemonstrator;

public final class HintDemonstratingOnNewPuzzleCreatedHandler {
    private final HintDemonstrator<Solution> hintDemonstrator;

    public HintDemonstratingOnNewPuzzleCreatedHandler(final HintDemonstrator<Solution> hintDemonstrator) {
        this.hintDemonstrator = hintDemonstrator;
    }

    public void handleNewPuzzleCreatedEvent(final NewPuzzleCreatedEvent event) {
        hintDemonstrator.demonstrateHint(event.puzzle.solution);
    }

}

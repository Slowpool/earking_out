package org.swetlokognatsk.earking_out.core.domain.events.handlers;

import org.swetlokognatsk.earking_out.core.domain.events.puzzles.NewPuzzleCreatedEvent;
import org.swetlokognatsk.earking_out.core.domain.model.solutions.Solution;
import org.swetlokognatsk.earking_out.core.ports.hints.demonstrators.HintDemonstrator;

public final class NewPuzzleDisplayedHandler {
    private final HintDemonstrator<Solution> hintDemonstrator;

    public NewPuzzleDisplayedHandler(final HintDemonstrator<Solution> hintDemonstrator) {
        this.hintDemonstrator = hintDemonstrator;
    }

    public void handleNewPuzzleCreatedEvent(final NewPuzzleCreatedEvent event) {
        hintDemonstrator.demonstrateHint(event.puzzle.solution);
    }

}

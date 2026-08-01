package org.swetlokognatsk.earking_out.core.domain.events.handlers;

import org.swetlokognatsk.earking_out.core.domain.events.puzzles.HintRepeatingRequestedEvent;
import org.swetlokognatsk.earking_out.core.domain.model.solutions.Solution;
import org.swetlokognatsk.earking_out.core.ports.hints.demonstrators.HintDemonstrator;

public final class HintRepeatingRequestedHandler {
    private final HintDemonstrator<Solution> hintDemonstrator;

    public HintRepeatingRequestedHandler(final HintDemonstrator<Solution> hintDemonstrator) {
        this.hintDemonstrator = hintDemonstrator;
    }

    public void handleHintRepeatingRequestedEvent(final HintRepeatingRequestedEvent event) {
        hintDemonstrator.demonstrateHint(event.puzzle.solution);
    }
}

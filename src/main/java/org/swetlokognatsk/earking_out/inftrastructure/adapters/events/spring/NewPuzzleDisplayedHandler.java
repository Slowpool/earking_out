package org.swetlokognatsk.earking_out.inftrastructure.adapters.events.spring;

import org.springframework.context.annotation.Lazy;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.swetlokognatsk.earking_out.core.domain.events.puzzles.NewPuzzleCreatedEvent;
import org.swetlokognatsk.earking_out.core.domain.model.solutions.Solution;
import org.swetlokognatsk.earking_out.core.ports.hints.demonstrators.HintDemonstrator;

@Component
public final class NewPuzzleDisplayedHandler {
    private final HintDemonstrator<Solution> hintDemonstrator;

    @Lazy
    public NewPuzzleDisplayedHandler(final HintDemonstrator<Solution> hintDemonstrator) {
        this.hintDemonstrator = hintDemonstrator;
    }

    @EventListener
    public void handleNewPuzzleDisplayedEvent(final NewPuzzleCreatedEvent event) {
        hintDemonstrator.demonstrateHint(event.puzzle.solution);
    }

}

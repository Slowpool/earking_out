package org.swetlokognatsk.earking_out.inftrastructure.adapters.events.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.swetlokognatsk.earking_out.core.domain.events.puzzles.NewPuzzleCreatedEvent;
import org.swetlokognatsk.earking_out.core.ports.hints.demonstrators.HintDemonstrator;

@Component
public class NewPuzzleDisplayedHandler {

    // TODO sort out these yellow lines
    @Autowired
    @Lazy
    private HintDemonstrator hintDemonstrator;

    @EventListener
    // TODO what to do with warning
    public void handleNewPuzzleDisplayedEvent(final NewPuzzleCreatedEvent event) {
        // TODO what to do with warning
        hintDemonstrator.demonstrateHint(event.puzzle.solution);
    }

}

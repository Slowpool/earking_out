package org.swetlokognatsk.earking_out.inftrastructure.adapters.events.spring;

import org.springframework.context.annotation.Lazy;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.swetlokognatsk.earking_out.core.domain.events.puzzles.HintRepeatingRequestedEvent;
import org.swetlokognatsk.earking_out.core.domain.model.solutions.Solution;
import org.swetlokognatsk.earking_out.core.ports.hints.demonstrators.HintDemonstrator;

@Component
public final class HintRepeatingRequestedHandler {
    protected final HintDemonstrator<Solution> hintDemonstrator;

    @Lazy
    public HintRepeatingRequestedHandler(final HintDemonstrator<Solution> hintDemonstrator) {
        this.hintDemonstrator = hintDemonstrator;
    }

    @EventListener
    public void handleHintRepeatingRequestedEvent(final HintRepeatingRequestedEvent event) {
        hintDemonstrator.demonstrateHint(event.puzzle.solution);
    }
}

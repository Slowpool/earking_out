package org.swetlokognatsk.earking_out.inftrastructure.adapters.events.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.swetlokognatsk.earking_out.core.domain.events.puzzles.NewPuzzleDisplayedEvent;

@Component
public class NewPuzzleDisplayedHandler {

    // TODO sort out these yellow lines
    // @Autowired
    // @Lazy

    @EventListener
    public void handleNewPuzzleDisplayedEvent(final NewPuzzleDisplayedEvent event) {

    }

}

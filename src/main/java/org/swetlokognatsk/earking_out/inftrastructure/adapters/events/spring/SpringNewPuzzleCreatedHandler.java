package org.swetlokognatsk.earking_out.inftrastructure.adapters.events.spring;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.swetlokognatsk.earking_out.core.domain.events.session.NewPuzzleCreatedEvent;

@Component
public final class SpringNewPuzzleCreatedHandler extends SpringEventHandler<NewPuzzleCreatedEvent> {

    @EventListener
    public void handleNewpuzzleCreatedEvent(final NewPuzzleCreatedEvent event) {
        traverseCallbacks(event);
    }

}

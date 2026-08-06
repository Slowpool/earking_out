package org.swetlokognatsk.earking_out.inftrastructure.adapters.events.spring;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.swetlokognatsk.earking_out.core.domain.events.session.UserTriedToGuessPuzzleEvent;

@Component
public class SpringUserTriedToGuessPuzzleHandler extends SpringEventHandler<UserTriedToGuessPuzzleEvent> {

    @EventListener
    public void handleUserTriedToGuessPuzzleEvent(final UserTriedToGuessPuzzleEvent event) {
        traverseCallbacks(event);
    }
}

package org.swetlokognatsk.earking_out.inftrastructure.adapters.events.spring;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.swetlokognatsk.earking_out.core.domain.events.session.SessionStartedEvent;

@Component
public class SpringSessionStartedHandler extends SpringEventHandler<SessionStartedEvent> {

    @EventListener
    public void handleSessionStartedEvent(final SessionStartedEvent event) {
        traverseCallbacks(event);
    }

}

package org.swetlokognatsk.earking_out.inftrastructure.adapters.events.spring;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.swetlokognatsk.earking_out.core.domain.events.session.SessionFinishedEvent;

@Component
public class SpringSessionFinishedHandler extends SpringEventHandler<SessionFinishedEvent> {

    @EventListener
    public void handleSessionFinishedEvent(final SessionFinishedEvent event) {
        traverseCallbacks(event);
    }

}

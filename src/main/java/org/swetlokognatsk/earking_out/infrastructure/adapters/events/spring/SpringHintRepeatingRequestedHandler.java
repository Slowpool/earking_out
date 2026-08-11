package org.swetlokognatsk.earking_out.infrastructure.adapters.events.spring;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.swetlokognatsk.earking_out.core.domain.events.session.HintRepeatingRequestedEvent;

@Component
public final class SpringHintRepeatingRequestedHandler extends SpringEventHandler<HintRepeatingRequestedEvent> {

    @EventListener
    public void handleHintRepeatingRequestedEvent(final HintRepeatingRequestedEvent event) {
        traverseCallbacks(event);
    }
}

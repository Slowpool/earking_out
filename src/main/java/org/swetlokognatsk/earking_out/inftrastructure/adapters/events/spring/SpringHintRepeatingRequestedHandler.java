package org.swetlokognatsk.earking_out.inftrastructure.adapters.events.spring;

import java.util.function.Consumer;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.swetlokognatsk.earking_out.core.domain.events.puzzles.HintRepeatingRequestedEvent;

@Component
public final class SpringHintRepeatingRequestedHandler extends SpringEventHandler<HintRepeatingRequestedEvent> {

    @EventListener
    public void handleHintRepeatingRequestedEvent(final HintRepeatingRequestedEvent event) {
        if (callback != null) {
            callback.accept(event);
        }
    }
}

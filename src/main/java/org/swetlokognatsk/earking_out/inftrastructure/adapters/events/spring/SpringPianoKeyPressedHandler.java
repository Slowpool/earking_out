package org.swetlokognatsk.earking_out.inftrastructure.adapters.events.spring;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.swetlokognatsk.earking_out.core.domain.events.pianokeyboard.PianoKeyPressedEvent;

@Component
public final class SpringPianoKeyPressedHandler extends SpringEventHandler<PianoKeyPressedEvent> {

    @EventListener
    public void handlePianoKeyPressedEvent(final PianoKeyPressedEvent event) {
        if (callback != null) {
            callback.accept(event);
        }
    }
}

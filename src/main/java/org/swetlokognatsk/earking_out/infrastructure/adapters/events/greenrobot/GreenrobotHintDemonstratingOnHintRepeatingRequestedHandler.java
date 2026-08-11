package org.swetlokognatsk.earking_out.infrastructure.adapters.events.greenrobot;

import org.greenrobot.eventbus.Subscribe;
import org.swetlokognatsk.earking_out.core.domain.events.handlers.HintDemonstratingOnHintRepeatingRequestedHandler;
import org.swetlokognatsk.earking_out.core.domain.events.session.HintRepeatingRequestedEvent;

public final class GreenrobotHintDemonstratingOnHintRepeatingRequestedHandler extends GreenrobotEventHandler<HintDemonstratingOnHintRepeatingRequestedHandler> {

    public GreenrobotHintDemonstratingOnHintRepeatingRequestedHandler(final HintDemonstratingOnHintRepeatingRequestedHandler domainHandler) {
        super(domainHandler);
    }

    // TODO can it be defined once in DomainEventHandler?
    @Subscribe
    public void handle(final HintRepeatingRequestedEvent e) {
        domainHandler.handle(e);
    }
}

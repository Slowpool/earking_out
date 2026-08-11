package org.swetlokognatsk.earking_out.infrastructure.adapters.events.greenrobot;

import org.swetlokognatsk.earking_out.core.domain.events.handlers.DomainEventHandler;
import org.swetlokognatsk.earking_out.core.domain.events.handlers.HintDemonstratingOnHintRepeatingRequestedHandler;

public abstract class GreenrobotEventHandler<DH extends DomainEventHandler<?>> {
    protected final DH domainHandler;

    public GreenrobotEventHandler(final DH domainHandler) {
        this.domainHandler = domainHandler;
    }
}

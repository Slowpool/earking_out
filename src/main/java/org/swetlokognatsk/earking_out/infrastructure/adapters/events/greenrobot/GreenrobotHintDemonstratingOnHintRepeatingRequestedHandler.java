package org.swetlokognatsk.earking_out.infrastructure.adapters.events.greenrobot;

import org.swetlokognatsk.earking_out.core.domain.events.handlers.HintDemonstratingOnHintRepeatingRequestedHandler;
import org.swetlokognatsk.earking_out.core.domain.events.session.HintRepeatingRequestedEvent;

public final class GreenrobotHintDemonstratingOnHintRepeatingRequestedHandler extends GreenrobotEventHandler<HintRepeatingRequestedEvent, HintDemonstratingOnHintRepeatingRequestedHandler> {

    public GreenrobotHintDemonstratingOnHintRepeatingRequestedHandler(final HintDemonstratingOnHintRepeatingRequestedHandler domainHandler) {
        super(domainHandler);
    }
}

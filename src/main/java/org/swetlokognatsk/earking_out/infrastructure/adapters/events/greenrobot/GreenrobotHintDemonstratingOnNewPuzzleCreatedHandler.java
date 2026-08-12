package org.swetlokognatsk.earking_out.infrastructure.adapters.events.greenrobot;

import org.swetlokognatsk.earking_out.core.domain.events.handlers.HintDemonstratingOnNewPuzzleCreatedHandler;
import org.swetlokognatsk.earking_out.core.domain.events.session.NewPuzzleCreatedEvent;

public final class GreenrobotHintDemonstratingOnNewPuzzleCreatedHandler extends GreenrobotEventHandler<NewPuzzleCreatedEvent, HintDemonstratingOnNewPuzzleCreatedHandler> {

    public GreenrobotHintDemonstratingOnNewPuzzleCreatedHandler(final HintDemonstratingOnNewPuzzleCreatedHandler domainHandler) {
        super(domainHandler);
    }
}

package org.swetlokognatsk.earking_out.inftrastructure.adapters.session.perfectpitch;

import org.swetlokognatsk.earking_out.core.domain.model.session.factories.SessionAggregatesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.session.perfectpitch.AudioPerfectPitchSessionAggregate;
import org.swetlokognatsk.earking_out.core.ports.session.perfectpitch.AudioPerfectPitchSessionRepository;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.session.InMemorySessionRepository;

public final class InMemoryAudioPerfectPitchSessionRepository extends InMemorySessionRepository<AudioPerfectPitchSessionAggregate> implements AudioPerfectPitchSessionRepository {

    public InMemoryAudioPerfectPitchSessionRepository(final SessionAggregatesFactory sessionAggregatesFactory) {
        super(sessionAggregatesFactory);

    }
}

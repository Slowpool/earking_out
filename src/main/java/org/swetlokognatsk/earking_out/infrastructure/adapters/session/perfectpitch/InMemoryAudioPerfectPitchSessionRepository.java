package org.swetlokognatsk.earking_out.infrastructure.adapters.session.perfectpitch;

import java.util.Optional;
import org.springframework.stereotype.Repository;
import org.swetlokognatsk.earking_out.core.domain.model.session.factories.SessionAggregatesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.session.perfectpitch.AudioPerfectPitchSessionAggregate;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.session.SessionAggregateDTOAssembler;
import org.swetlokognatsk.earking_out.core.ports.identity.UserResolver;
import org.swetlokognatsk.earking_out.core.ports.session.perfectpitch.AudioPerfectPitchSessionRepository;
import org.swetlokognatsk.earking_out.infrastructure.adapters.session.InMemorySessionRepository;

@Repository
public class InMemoryAudioPerfectPitchSessionRepository extends InMemorySessionRepository<AudioPerfectPitchSessionAggregate> implements AudioPerfectPitchSessionRepository {

    public InMemoryAudioPerfectPitchSessionRepository(final SessionAggregatesFactory sessionAggregatesFactory, final Optional<UserResolver> userResolver, final SessionAggregateDTOAssembler dtoAssembler) {
        super(sessionAggregatesFactory, userResolver, dtoAssembler);

    }
}

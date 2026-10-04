package org.swetlokognatsk.earking_out.app.web.services.responsebuilders.perfectpitch;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.swetlokognatsk.earking_out.app.web.models.responses.perfectpitch.AudioPerfectPitchGuessResponse;
import org.swetlokognatsk.earking_out.app.web.services.renderers.perfectpitch.AudioPerfectPitchStatsRenderer;
import org.swetlokognatsk.earking_out.core.domain.helpers.SessionRepositoryDelegator;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionId;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionStates;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.session.perfectpitch.AudioPerfectPitchSessionAggregateDTO;
import org.swetlokognatsk.earking_out.core.ports.di.DI;
import org.swetlokognatsk.earking_out.infrastructure.adapters.hints.demonstrators.sound.WebAudioPerfectPitchHintDemonstrator;
import lombok.AllArgsConstructor;

@Component
@Lazy
@AllArgsConstructor
public class AudioPerfectPitchGuessResponseBuilder {

    private final SessionRepositoryDelegator sessionRepository;

    public AudioPerfectPitchGuessResponse build(final SessionId sessionId) {
        var session = (AudioPerfectPitchSessionAggregateDTO) sessionRepository.getSessionAggregateDTO(sessionId);

        var puzzlesCompleted = session.stats.puzzlesCompleted;
        var prevGuessIsSuccessful = session.prevGuessIsSuccessful;

        var newHint = prevGuessIsSuccessful
                ? DI.get(WebAudioPerfectPitchHintDemonstrator.class)
                        .getHintUrl()
                : null;

        var sessionStatsHtml = session.state == SessionStates.COMPLETED
                ? DI.get(AudioPerfectPitchStatsRenderer.class)
                        .renderPage(session)
                : null;
        return new AudioPerfectPitchGuessResponse(session.state, newHint, puzzlesCompleted, prevGuessIsSuccessful, sessionStatsHtml);
    }
}

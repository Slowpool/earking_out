package org.swetlokognatsk.earking_out.app.web.services.renderers.perfectpitch;

import java.util.HashMap;
import org.springframework.stereotype.Component;
import org.swetlokognatsk.earking_out.app.web.services.renderers.TemplateRenderer;
import org.swetlokognatsk.earking_out.core.domain.helpers.SessionRepositoryDelegator;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionId;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.session.perfectpitch.AudioPerfectPitchSessionAggregateDTO;
import org.swetlokognatsk.earking_out.core.domain.services.app.session.perfectpitch.PerfectPitchSessionStatsService;
import org.swetlokognatsk.earking_out.infrastructure.annotations.WebComponent;
import lombok.AllArgsConstructor;

@WebComponent
@AllArgsConstructor
public class AudioPerfectPitchStatsRenderer {

    private final PerfectPitchSessionStatsService<AudioPerfectPitchExercise> statsService;
    private final SessionRepositoryDelegator sessionRepository;
    private final TemplateRenderer templateRenderer;

    public String renderPage(final SessionId sessionId) {
        var sessionDto = (AudioPerfectPitchSessionAggregateDTO) sessionRepository.getSessionAggregateDTO(sessionId);
        return renderPage(sessionDto);
    }

    public String renderPage(final AudioPerfectPitchSessionAggregateDTO session) {
        var model = new HashMap<String, Object>();

        model.put("puzzlesCompletedPerfectly", session.stats.puzzlesCompletedPerfectly);
        model.put("numberOfCompletedPuzzles", session.stats.puzzlesCompleted);
        model.put("perfectlyCompletedPuzzlesRate", session.stats.getPerfectlyCompletedPuzzlesRate());

        var stats = statsService.getAggregatedStats(session.sessionId);
        model.put("stats", stats);

        return templateRenderer.renderTemplate("/stats/session/perfect_pitch/audio_perfect_pitch_session_stats", model);
    }
}

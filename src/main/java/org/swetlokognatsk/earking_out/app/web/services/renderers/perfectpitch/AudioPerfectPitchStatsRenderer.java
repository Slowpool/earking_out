package org.swetlokognatsk.earking_out.app.web.services.renderers.perfectpitch;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;
import org.swetlokognatsk.earking_out.core.domain.helpers.SessionRepositoryDelegator;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionId;
import org.swetlokognatsk.earking_out.core.domain.model.session.perfectpitch.PerfectPitchSessionStats;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.session.perfectpitch.AudioPerfectPitchSessionAggregateDTO;
import org.swetlokognatsk.earking_out.core.domain.services.app.session.perfectpitch.PerfectPitchSessionStatsService;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import lombok.AllArgsConstructor;

@Component
@Lazy
@AllArgsConstructor
public class AudioPerfectPitchStatsRenderer {

    private final PerfectPitchSessionStatsService<AudioPerfectPitchExercise> statsService;
    private final SessionRepositoryDelegator sessionRepository;
    private final SpringTemplateEngine templateEngine;

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
        var extendedStatsBlock = renderExtendedStatsBlock(stats);
        model.put("extendedStatsBlock", extendedStatsBlock);

        return renderTemplate("/stats/session/perfect_pitch/audio_perfect_pitch_session_stats", model);
    }

    private String renderExtendedStatsBlock(final PerfectPitchSessionStats stats) {
        if (stats.notesStats.length == 0) {
            return "";
        }
        // TODO
        return "extended stats";
    }

    // TODO ofc put it out of current class
    private final String renderTemplate(final String template, final HashMap<String, Object> variables) {
        var context = new Context(Locale.getDefault(), variables);
        return templateEngine.process(template, context);
    }
}

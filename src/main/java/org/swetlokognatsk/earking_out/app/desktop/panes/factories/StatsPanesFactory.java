package org.swetlokognatsk.earking_out.app.desktop.panes.factories;

import org.swetlokognatsk.earking_out.app.desktop.panes.SessionStatsPane;
import org.swetlokognatsk.earking_out.app.desktop.panes.perfectpitch.stats.PerfectPitchStatsPane;
import org.swetlokognatsk.earking_out.core.domain.helpers.SessionRepositoryDelegator;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionId;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.session.perfectpitch.AudioPerfectPitchSessionAggregateDTO;
import org.swetlokognatsk.earking_out.core.domain.services.app.session.perfectpitch.PerfectPitchSessionStatsService;
import org.swetlokognatsk.earking_out.core.ports.config.PuzzleConfigRepository;
import org.swetlokognatsk.earking_out.core.ports.di.DI;
import org.swetlokognatsk.earking_out.infrastructure.annotations.DesktopComponent;
import lombok.AllArgsConstructor;

@DesktopComponent
@AllArgsConstructor
public final class StatsPanesFactory {
    private final PuzzleConfigRepository puzzleConfigRepository;
    private final SessionRepositoryDelegator sessionRepository;

    public SessionStatsPane<?, ?, ?> create(final SessionId sessionId) {
        var sessionDto = sessionRepository.getSessionAggregateDTO(sessionId);
        var exercise = sessionDto.puzzleConfigDto.exercise;
        var sessionStatsPane = switch (exercise) {
        case AudioPerfectPitchExercise e -> new PerfectPitchStatsPane<AudioPerfectPitchExercise, AudioPerfectPitchSessionAggregateDTO>((AudioPerfectPitchSessionAggregateDTO) sessionDto, DI.get(PerfectPitchSessionStatsService.class));
        default -> throw new RuntimeException("unknown exercise: " + exercise);
        };
        return (SessionStatsPane<?, ?, ?>) sessionStatsPane;
    }
}

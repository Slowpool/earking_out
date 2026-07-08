package org.swetlokognatsk.earking_out.app.desktop.panes.factories;

import org.swetlokognatsk.earking_out.app.desktop.panes.SessionStatsPane;
import org.swetlokognatsk.earking_out.app.desktop.panes.perfectpitch.stats.PerfectPitchStatsPane;
import org.swetlokognatsk.earking_out.core.domain.helpers.SessionRepositoryDelegator;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionId;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.session.SessionAggregateDTOAssembler;
import org.swetlokognatsk.earking_out.core.ports.config.PuzzleConfigRepository;

public final class StatsPanesFactory {
    protected final PuzzleConfigRepository puzzleConfigRepository;
    protected final SessionRepositoryDelegator sessionRepository;

    public StatsPanesFactory(final PuzzleConfigRepository puzzleConfigRepository, final SessionRepositoryDelegator sessionRepository) {
        this.puzzleConfigRepository = puzzleConfigRepository;
        this.sessionRepository = sessionRepository;
    }

    public SessionStatsPane<?> create(final SessionId sessionId) {
        var sessionDto = SessionAggregateDTOAssembler.getSessionAggregateDTO(sessionId);
        var exercise = sessionDto.puzzleConfigDto.exercise;
        var sessionStatsPane = switch (exercise) {
        case AudioPerfectPitchExercise e -> new PerfectPitchStatsPane<>(sessionDto);
        default -> throw new RuntimeException("unknown exercise: " + exercise);
        };
        return sessionStatsPane;
    }
}

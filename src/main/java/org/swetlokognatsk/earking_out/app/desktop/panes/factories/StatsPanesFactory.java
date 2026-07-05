package org.swetlokognatsk.earking_out.app.desktop.panes.factories;

import java.util.UUID;
import org.swetlokognatsk.earking_out.app.desktop.panes.SessionStatsPane;
import org.swetlokognatsk.earking_out.app.desktop.panes.perfectpitch.stats.PerfectPitchStatsPane;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.PuzzleConfigDTO;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.perfectpitch.PerfectPitchConfigDTO;
import org.swetlokognatsk.earking_out.core.ports.DI;
import org.swetlokognatsk.earking_out.core.ports.config.PuzzleConfigRepository;
import org.swetlokognatsk.earking_out.core.ports.session.SessionRepository;

public final class StatsPanesFactory {
    protected final PuzzleConfigRepository puzzleConfigRepository;
    protected final SessionRepository sessionRepository;

    public StatsPanesFactory() {
        puzzleConfigRepository = DI.get(PuzzleConfigRepository.class);
        sessionRepository = DI.get(SessionRepository.class);
    }

    public SessionStatsPane<?> create(final UUID sessionId) {
        var session = sessionRepository.get(sessionId);
        var exercise = session.getPuzzleConfig().exercise;
        // TODO finish Session refactoring
        SessionStatsPane<?> sessionStatsPane = null;
        // var sessionStatsPane = switch (exercise.name) {
        // case PERFECT_PITCH -> switch (exercise.type) {
        // case VISUAL, AUDIO -> new PerfectPitchStatsPane<>((Session<PerfectPitchConfigDTO>) session);
        // default -> throw new RuntimeException("unknown exercise type for stats pane: " + exercise.type);
        // };
        // default -> throw new RuntimeException("unkonwn exercise for stats pane: " + exercise.name);
        // };
        return sessionStatsPane;
    }
}

package org.swetlokognatsk.earking_out.app.desktop.panes.factories;

import org.swetlokognatsk.earking_out.app.desktop.panes.SessionStatsPane;
import org.swetlokognatsk.earking_out.app.desktop.panes.perfectpitch.stats.PerfectPitchStatsPane;
import org.swetlokognatsk.earking_out.core.domain.model.session.Session;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.PuzzleConfigDTO;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.perfectpitch.PerfectPitchConfigDTO;

public final class StatsPanesFactory {

    private StatsPanesFactory() {
    }

    public static <PCDTO extends PuzzleConfigDTO<?>, SSP extends SessionStatsPane<PCDTO>> SSP create(Session<PCDTO> session) {
        var exercise = session.puzzleConfigDto().exercise;
        var sessionStatsPane = switch (exercise.name) {
        case PERFECT_PITCH -> switch (exercise.type) {
        case VISUAL, AUDIO -> new PerfectPitchStatsPane<>((Session<PerfectPitchConfigDTO>) session);
        default -> throw new RuntimeException("unknown exercise type for stats pane: " + exercise.type);
        };
        default -> throw new RuntimeException("unkonwn exercise for stats pane: " + exercise.name);
        };
        return (SSP) sessionStatsPane;
    }
}

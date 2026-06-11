package org.swetlokognatsk.earking_out.app.desktop.panes.factories;

import org.swetlokognatsk.earking_out.app.desktop.panes.SessionStatsPane;
import org.swetlokognatsk.earking_out.app.desktop.panes.perfectpitch.stats.PerfectPitchStatsPane;
import org.swetlokognatsk.earking_out.core.domain.model.Session;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfigAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.PerfectPitchConfigAggregate;

public final class StatsPanesFactory {

    private StatsPanesFactory() {
    }

    public static <PC extends PuzzleConfigAggregate<?>, SSP extends SessionStatsPane<PC>> SSP create(Session<PC> session) {
        var exercise = session.puzzleConfig().exercise;
        var sessionStatsPane = switch (exercise.name) {
        case PERFECT_PITCH -> switch (exercise.type) {
        case VISUAL, AUDIO -> new PerfectPitchStatsPane<>((Session<PerfectPitchConfigAggregate>) session);
        default -> throw new RuntimeException("unknown exercise type for stats pane: " + exercise.type);
        };
        default -> throw new RuntimeException("unkonwn exercise for stats pane: " + exercise.name);
        };
        return (SSP) sessionStatsPane;
    }
}

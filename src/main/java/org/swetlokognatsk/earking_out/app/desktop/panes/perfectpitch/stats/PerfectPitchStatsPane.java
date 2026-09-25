package org.swetlokognatsk.earking_out.app.desktop.panes.perfectpitch.stats;

import org.swetlokognatsk.earking_out.app.desktop.panes.SessionStatsPane;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.PerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.session.perfectpitch.PerfectPitchSessionAggregateDTO;
import org.swetlokognatsk.earking_out.core.domain.services.app.session.perfectpitch.PerfectPitchSessionStatsService;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

// no further inheritance because stats are the same for both visual and audio exercise types
public final class PerfectPitchStatsPane<E extends PerfectPitchExercise, SADTO extends PerfectPitchSessionAggregateDTO<E, ?, ?, ?>> extends SessionStatsPane<E, SADTO, PerfectPitchSessionStatsService<E>> {

    private final PerfectPitchSessionStatsService<?> statsService;

    public PerfectPitchStatsPane(final SADTO sessionDto, final PerfectPitchSessionStatsService<?> statsAggregator) {
        super(sessionDto);
        this.statsService = statsAggregator;
    }

    protected Pane buildStatsPane() {
        var perfectPitchStats = statsService.aggregate(sessionDto.sessionId);
        // TODO PerfectPitchStatsPane
        var pane = new VBox(new Label("some stats are here"));
        pane.setAlignment(Pos.CENTER);
        return pane;
    }
}

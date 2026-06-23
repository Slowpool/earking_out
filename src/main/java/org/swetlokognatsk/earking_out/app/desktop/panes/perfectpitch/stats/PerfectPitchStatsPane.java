package org.swetlokognatsk.earking_out.app.desktop.panes.perfectpitch.stats;

import org.swetlokognatsk.earking_out.app.desktop.panes.SessionStatsPane;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.PerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.session.Session;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.perfectpitch.PerfectPitchConfigDTO;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

// no further inheritance because stats are the same for both visual and audio exercise types
public final class PerfectPitchStatsPane<PCDTO extends PerfectPitchConfigDTO<? extends PerfectPitchExercise>> extends SessionStatsPane<PCDTO> {

    public PerfectPitchStatsPane(final Session<PCDTO> session) {
        super(session);

    }

    protected Pane buildStatsPane() {
        var pane = new VBox(new Label("some stats are here"));
        pane.setAlignment(Pos.CENTER);
        return pane;
    }
}

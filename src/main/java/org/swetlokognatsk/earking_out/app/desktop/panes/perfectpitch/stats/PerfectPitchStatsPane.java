package org.swetlokognatsk.earking_out.app.desktop.panes.perfectpitch.stats;

import org.swetlokognatsk.earking_out.app.desktop.panes.SessionStatsPane;
import org.swetlokognatsk.earking_out.core.domain.model.Session;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.PerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.PerfectPitchConfigAggregate;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

// no further inheritance because stats are the same for both visual and audio exercise types
public final class PerfectPitchStatsPane<PC extends PerfectPitchConfigAggregate<? extends PerfectPitchExercise>> extends SessionStatsPane<PC> {

    public PerfectPitchStatsPane(Session<PC> session) {
        super(session);

    }

    protected Pane buildStatsPane() {
        var pane = new VBox(new Label("some stats are here"));
        pane.setAlignment(Pos.CENTER);
        return pane;
    }
}

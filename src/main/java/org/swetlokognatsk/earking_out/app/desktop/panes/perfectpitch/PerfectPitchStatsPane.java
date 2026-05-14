package org.swetlokognatsk.earking_out.app.desktop.panes.perfectpitch;

import org.swetlokognatsk.earking_out.app.desktop.panes.SessionStatsPane;
import org.swetlokognatsk.earking_out.core.domain.model.Session;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfect_pitch.PerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.PerfectPitchConfig;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class PerfectPitchStatsPane<PC extends PerfectPitchConfig<? extends PerfectPitchExercise>> extends SessionStatsPane<PC> {

    public PerfectPitchStatsPane(Session<PC> session) {
        super(session);

    }

    protected Pane buildStatsPane() {
        var pane = new VBox(new Label("some stats are here"));
        pane.setAlignment(Pos.CENTER);
        return pane;
    }
}

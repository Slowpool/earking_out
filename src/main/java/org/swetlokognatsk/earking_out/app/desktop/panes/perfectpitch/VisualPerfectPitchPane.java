package org.swetlokognatsk.earking_out.app.desktop.panes.perfectpitch;

import org.swetlokognatsk.earking_out.core.domain.model.Session;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.typed.VisualPerfectPitchConfig;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class VisualPerfectPitchPane extends PerfectPitchPane<VisualPerfectPitchConfig> {

    public VisualPerfectPitchPane(Session<VisualPerfectPitchConfig> session) {
        super(session);

    }

    protected Pane buildPuzzlePane() {
        return new VBox();
    }

}

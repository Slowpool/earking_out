package org.swetlokognatsk.earking_out.app.desktop.panes.perfectpitch;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfect_pitch.typed.VisualPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.typed.VisualPerfectPitchConfig;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class VisualPerfectPitchPane extends PerfectPitchPane<VisualPerfectPitchExercise, VisualPerfectPitchConfig> {

    public VisualPerfectPitchPane(VisualPerfectPitchConfig config) {
        super(config);

    }

    protected Pane buildPuzzlePane() {
        return new VBox();
    }

}

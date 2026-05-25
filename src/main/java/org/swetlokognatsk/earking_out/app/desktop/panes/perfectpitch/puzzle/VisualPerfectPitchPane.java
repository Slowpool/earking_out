package org.swetlokognatsk.earking_out.app.desktop.panes.perfectpitch.puzzle;

import org.swetlokognatsk.earking_out.core.domain.model.Session;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.puzzles.VisualPerfectPitchPuzzle;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.typed.VisualPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.hints.UsualHint;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.typed.VisualPerfectPitchConfig;
import org.swetlokognatsk.earking_out.core.ports.puzzles.generators.perfectpitch.VisualPerfectPitchPuzzleGenerator;

import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class VisualPerfectPitchPane extends PerfectPitchPane<VisualPerfectPitchExercise, VisualPerfectPitchConfig, UsualHint, VisualPerfectPitchPuzzleGenerator, VisualPerfectPitchPuzzle> {

    public VisualPerfectPitchPane(Session<VisualPerfectPitchConfig> session, double width, double height) {
        super(session, width, height);

    }

    protected Pane buildPuzzlePane() {
        return new VBox();
    }

    // TODO
    protected void demonstrateHint() {
        throw new RuntimeException("demonstrating the hint");
    }
}

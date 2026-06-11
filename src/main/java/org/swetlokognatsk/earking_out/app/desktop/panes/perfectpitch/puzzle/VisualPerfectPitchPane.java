package org.swetlokognatsk.earking_out.app.desktop.panes.perfectpitch.puzzle;

import org.swetlokognatsk.earking_out.core.domain.model.Session;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.VisualPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.hints.UsualHint;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.VisualPerfectPitchConfigAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.perfectpitch.VisualPerfectPitchPuzzle;
import org.swetlokognatsk.earking_out.core.ports.puzzles.generators.perfectpitch.VisualPerfectPitchPuzzleGenerator;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public final class VisualPerfectPitchPane extends PerfectPitchPane<VisualPerfectPitchExercise, VisualPerfectPitchConfigAggregate, UsualHint, VisualPerfectPitchPuzzleGenerator, VisualPerfectPitchPuzzle> {

    public VisualPerfectPitchPane(Session<VisualPerfectPitchConfigAggregate> session, double width, double height) {
        super(session, width, height);

    }

    protected Pane buildPuzzlePane() {
        return new VBox();
    }

    // TODO
    protected void demonstrateNewHint() {
        throw new RuntimeException("demonstrating the hint");
    }

    // TODO
    protected void demonstrateHint() {
        throw new RuntimeException("demonstrating the hint");
    }
}

package org.swetlokognatsk.earking_out.app.desktop.panes;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfig;
import javafx.scene.layout.VBox;

public abstract class PuzzlePane<E extends Exercise, PC extends PuzzleConfig<E>> extends VBox {
    protected final PC config;

    public PuzzlePane(PC config) {
        this.config = config;
    }
}

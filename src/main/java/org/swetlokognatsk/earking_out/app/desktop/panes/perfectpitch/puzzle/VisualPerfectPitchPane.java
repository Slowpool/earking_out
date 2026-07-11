package org.swetlokognatsk.earking_out.app.desktop.panes.perfectpitch.puzzle;

import org.swetlokognatsk.earking_out.core.domain.model.session.SessionId;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.perfectpitch.VisualPerfectPitchConfigDTO;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public final class VisualPerfectPitchPane extends PerfectPitchPane<VisualPerfectPitchConfigDTO> {

    public VisualPerfectPitchPane(final SessionId sessionId, final VisualPerfectPitchConfigDTO puzzleConfigDto, final double width, final double height) {
        super(sessionId, puzzleConfigDto, width, height);
    }

    protected Pane buildInnerPuzzlePane(final VisualPerfectPitchConfigDTO puzzleConfigDto) {
        return new VBox();
    }

}

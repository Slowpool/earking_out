package org.swetlokognatsk.earking_out.app.desktop.panes.perfectpitch.config;

import org.swetlokognatsk.earking_out.app.desktop.helpers.PianoKeyboardHandlersRegister;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.VisualPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.perfectpitch.VisualPerfectPitchConfigDTO;

public final class VisualPerfectPitchConfigPane extends PerfectPitchConfigPane<VisualPerfectPitchExercise, VisualPerfectPitchConfigDTO> {

    public VisualPerfectPitchConfigPane(final VisualPerfectPitchConfigDTO puzzleConfigDto, final double width, final double height, final PianoKeyboardHandlersRegister pianoKeyboardHandlersRegister) {
        super(puzzleConfigDto, width, height, pianoKeyboardHandlersRegister);
    }
}

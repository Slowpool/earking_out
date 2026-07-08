package org.swetlokognatsk.earking_out.app.desktop.panes.perfectpitch.config;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.perfectpitch.AudioPerfectPitchConfigDTO;

public final class AudioPerfectPitchConfigPane extends PerfectPitchConfigPane<AudioPerfectPitchExercise, AudioPerfectPitchConfigDTO> {

    public AudioPerfectPitchConfigPane(final AudioPerfectPitchConfigDTO puzzleConfigDto, final double width, final double height) {
        super(puzzleConfigDto, width, height);
    }
}

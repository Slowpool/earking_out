package org.swetlokognatsk.earking_out.core.domain.model.puzzles.perfectpitch;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.hints.UsualHint;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.perfectpitch.AudioPerfectPitchConfigDTO;
import org.swetlokognatsk.earking_out.core.ports.puzzles.generators.perfectpitch.AudioPerfectPitchPuzzleGenerator;

public final class AudioPerfectPitchPuzzle extends PerfectPitchPuzzle<AudioPerfectPitchExercise, AudioPerfectPitchConfigDTO, UsualHint, AudioPerfectPitchPuzzleGenerator> {

    public AudioPerfectPitchPuzzle(final AudioPerfectPitchConfigDTO config, final AudioPerfectPitchPuzzleGenerator puzzleGenerator) {
        super(config, puzzleGenerator);

    }

}

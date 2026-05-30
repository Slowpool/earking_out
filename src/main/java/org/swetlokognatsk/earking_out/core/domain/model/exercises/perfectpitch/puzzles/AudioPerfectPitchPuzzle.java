package org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.puzzles;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.typed.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.hints.UsualHint;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.typed.AudioPerfectPitchConfig;
import org.swetlokognatsk.earking_out.core.ports.puzzles.generators.perfectpitch.AudioPerfectPitchPuzzleGenerator;

public final class AudioPerfectPitchPuzzle extends PerfectPitchPuzzle<AudioPerfectPitchExercise, AudioPerfectPitchConfig, UsualHint, AudioPerfectPitchPuzzleGenerator> {

    public AudioPerfectPitchPuzzle(AudioPerfectPitchConfig config, AudioPerfectPitchPuzzleGenerator puzzleGenerator) {
        super(config, puzzleGenerator);

    }

}

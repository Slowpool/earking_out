package org.swetlokognatsk.earking_out.core.domain.model.puzzles.perfectpitch;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.hints.UsualHint;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.AudioPerfectPitchConfigAggregate;
import org.swetlokognatsk.earking_out.core.ports.puzzles.generators.perfectpitch.AudioPerfectPitchPuzzleGenerator;

public final class AudioPerfectPitchPuzzle extends PerfectPitchPuzzle<AudioPerfectPitchExercise, AudioPerfectPitchConfigAggregate, UsualHint, AudioPerfectPitchPuzzleGenerator> {

    public AudioPerfectPitchPuzzle(AudioPerfectPitchConfigAggregate config, AudioPerfectPitchPuzzleGenerator puzzleGenerator) {
        super(config, puzzleGenerator);

    }

}

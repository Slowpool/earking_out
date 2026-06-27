package org.swetlokognatsk.earking_out.core.domain.model.puzzles.perfectpitch;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.hints.perfectpitch.AudioPerfectPitchHint;
import org.swetlokognatsk.earking_out.core.domain.model.solutions.Solution;

public final class AudioPerfectPitchPuzzle extends PerfectPitchPuzzle<AudioPerfectPitchExercise, AudioPerfectPitchHint> {

    public AudioPerfectPitchPuzzle(final AudioPerfectPitchExercise exercise, final Solution solution, final AudioPerfectPitchHint hint) {
        super(exercise, solution, hint);
    }
}

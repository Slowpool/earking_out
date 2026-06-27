package org.swetlokognatsk.earking_out.core.domain.model.puzzles.perfectpitch;

import org.swetlokognatsk.earking_out.core.domain.model.Solution;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.hints.perfectpitch.PerfectPitchHint;

public final class AudioPerfectPitchPuzzle extends PerfectPitchPuzzle<AudioPerfectPitchExercise> {

    public AudioPerfectPitchPuzzle(final AudioPerfectPitchExercise exercise, final Solution solution, final PerfectPitchHint hint) {
        super(exercise, solution, hint);
    }

}

package org.swetlokognatsk.earking_out.core.domain.model.puzzles.perfectpitch;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.hints.sound.SingleSoundHint;
import org.swetlokognatsk.earking_out.core.domain.model.solutions.Solution;

public final class AudioPerfectPitchPuzzle extends PerfectPitchPuzzle<AudioPerfectPitchExercise, SingleSoundHint> {

    public AudioPerfectPitchPuzzle(final AudioPerfectPitchExercise exercise, final Solution solution, final SingleSoundHint hint) {
        super(exercise, solution, hint);
    }
}

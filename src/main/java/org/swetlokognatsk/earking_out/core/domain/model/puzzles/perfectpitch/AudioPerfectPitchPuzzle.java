package org.swetlokognatsk.earking_out.core.domain.model.puzzles.perfectpitch;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.solutions.sound.SingleSoundSolution;

public final class AudioPerfectPitchPuzzle extends PerfectPitchPuzzle<AudioPerfectPitchExercise, SingleSoundSolution> {

    public AudioPerfectPitchPuzzle(final AudioPerfectPitchExercise exercise, final SingleSoundSolution solution) {
        super(exercise, solution);
    }
}

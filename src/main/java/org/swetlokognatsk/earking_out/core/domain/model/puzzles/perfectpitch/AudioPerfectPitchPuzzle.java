package org.swetlokognatsk.earking_out.core.domain.model.puzzles.perfectpitch;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.solutions.perfectpitch.AudioPerfectPitchSolution;

public final class AudioPerfectPitchPuzzle extends PerfectPitchPuzzle<AudioPerfectPitchExercise, AudioPerfectPitchSolution> {
    private static final long serialVersionUID = 1L;

    // TODO is it possible to remove exercise here and pass it inside constructor like super(AudioPerfectPitc...)
    public AudioPerfectPitchPuzzle(final AudioPerfectPitchExercise exercise, final AudioPerfectPitchSolution solution) {
        super(exercise, solution);
    }

    public boolean equals(final Object obj) {
        return super.equals(obj)
                && obj instanceof AudioPerfectPitchPuzzle;
    }
}

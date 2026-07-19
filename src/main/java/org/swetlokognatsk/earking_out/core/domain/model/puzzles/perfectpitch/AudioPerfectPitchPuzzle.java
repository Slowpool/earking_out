package org.swetlokognatsk.earking_out.core.domain.model.puzzles.perfectpitch;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.solutions.perfectpitch.AudioPerfectPitchSolution;

public final class AudioPerfectPitchPuzzle extends PerfectPitchPuzzle<AudioPerfectPitchExercise, AudioPerfectPitchSolution> {
    private static final long serialVersionUID = 1L;

    public AudioPerfectPitchPuzzle(final AudioPerfectPitchExercise exercise, final AudioPerfectPitchSolution solution) {
        super(exercise, solution);
    }

    public int hashCode() {
        return exercise.hashCode() + solution.hashCode();
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof AudioPerfectPitchPuzzle)) {
            return false;
        }
        var other = (AudioPerfectPitchPuzzle) obj;
        return exercise.equals(other.exercise) && solution.equals(other.solution);
    }
}

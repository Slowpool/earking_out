package org.swetlokognatsk.earking_out.core.domain.model.puzzles.perfectpitch;

import java.io.Serializable;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.solutions.perfectpitch.AudioPerfectPitchSolution;

public final class AudioPerfectPitchPuzzle extends PerfectPitchPuzzle<AudioPerfectPitchExercise, AudioPerfectPitchSolution> implements Serializable {

    public AudioPerfectPitchPuzzle(final AudioPerfectPitchExercise exercise, final AudioPerfectPitchSolution solution) {
        super(exercise, solution);
    }
}

package org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.AudioExercise;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.ExerciseTypes;

public final class AudioPerfectPitchExercise extends PerfectPitchExercise {
    private static final long serialVersionUID = 1L;

    public AudioPerfectPitchExercise() {
        super(ExerciseTypes.AUDIO);
    }

    public String tType() {
        return AudioExercise.tType();
    }
}

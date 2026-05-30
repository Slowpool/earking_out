package org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.typed;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.AudioExercise;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.ExerciseTypes;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.PerfectPitchExercise;

public final class AudioPerfectPitchExercise extends PerfectPitchExercise {

    public AudioPerfectPitchExercise() {
        super(ExerciseTypes.AUDIO);

    }

    public String tType() {
        return AudioExercise.tType();
    }
}

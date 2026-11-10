package org.swetlokognatsk.earking_out.core.domain.model.solutions.perfectpitch;

import static org.swetlokognatsk.earking_out.core.domain.model.exercises.ExercisesFactory.AUDIO_PERFECT_PITCH_EXERCISE;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.PerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class AudioPerfectPitchSolution extends PerfectPitchSolution {
    private static final long serialVersionUID = 1L;

    public AudioPerfectPitchSolution(final PianoKeyNumber keyNumber) {
        super(AUDIO_PERFECT_PITCH_EXERCISE, keyNumber);
    }

    public AudioPerfectPitchSolution(final AudioPerfectPitchExercise exercise, @JsonProperty("keyNumber") final PianoKeyNumber keyNumber) {
        super(exercise, keyNumber);
    }

    public int hashCode() {
        return keyNumber.hashCode();
    }

    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }
        // Audio and Visual and etc. are used for polymorphism (in hint demonstrator), actually they all are of PerfectPitchSolution class (the solution is the same for both audio and visual perfect pitch solutions)
        if (!(obj instanceof PerfectPitchSolution)) {
            return false;
        }
        var other = (PerfectPitchSolution) obj;
        return keyNumber.equals(other.keyNumber);
    }
}

package org.swetlokognatsk.earking_out.core.domain.model.solutions.perfectpitch;

import java.io.Serializable;

import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;

public final class AudioPerfectPitchSolution extends PerfectPitchSolution implements Serializable {

    public AudioPerfectPitchSolution(final PianoKeyNumber keyNumber) {
        super(keyNumber);
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

package org.swetlokognatsk.earking_out.core.domain.model.solutions.perfectpitch;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.PerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;
import org.swetlokognatsk.earking_out.core.domain.model.solutions.sound.PianoKeyNumberSolution;

public abstract class PerfectPitchSolution extends PianoKeyNumberSolution {
    private static final long serialVersionUID = 1L;

    public PerfectPitchSolution(final PerfectPitchExercise exercise, final PianoKeyNumber keyNumber) {
        super(exercise, keyNumber);
    }
}

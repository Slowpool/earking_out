package org.swetlokognatsk.earking_out.core.domain.model.solutions.perfectpitch;

import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;
import org.swetlokognatsk.earking_out.core.domain.model.solutions.sound.PianoKeyNumberSolution;

public abstract class PerfectPitchSolution extends PianoKeyNumberSolution {

    public PerfectPitchSolution(final PianoKeyNumber keyNumber) {
        super(keyNumber);
    }
}

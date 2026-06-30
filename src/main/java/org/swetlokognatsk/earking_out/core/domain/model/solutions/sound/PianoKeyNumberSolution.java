package org.swetlokognatsk.earking_out.core.domain.model.solutions.sound;

import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;
import org.swetlokognatsk.earking_out.core.domain.model.solutions.Solution;

public abstract class PianoKeyNumberSolution extends Solution {
    public final PianoKeyNumber keyNumber;

    public PianoKeyNumberSolution(final PianoKeyNumber keyNumber) {
        this.keyNumber = keyNumber;
    }

}

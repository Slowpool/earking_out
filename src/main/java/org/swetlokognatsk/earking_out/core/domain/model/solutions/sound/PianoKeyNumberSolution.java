package org.swetlokognatsk.earking_out.core.domain.model.solutions.sound;

import java.io.Serializable;
import java.util.Objects;

import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;
import org.swetlokognatsk.earking_out.core.domain.model.solutions.Solution;

public abstract class PianoKeyNumberSolution extends Solution implements Serializable {
    private static final long serialVersionUID = 1L;

    public final PianoKeyNumber keyNumber;

    public PianoKeyNumberSolution(final PianoKeyNumber keyNumber) {
        this.keyNumber = Objects.requireNonNull(keyNumber);
    }

}

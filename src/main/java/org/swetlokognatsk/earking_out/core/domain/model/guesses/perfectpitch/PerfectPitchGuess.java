package org.swetlokognatsk.earking_out.core.domain.model.guesses.perfectpitch;

import org.swetlokognatsk.earking_out.core.domain.model.guesses.Guess;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;

public abstract class PerfectPitchGuess extends Guess {
    protected final PianoKeyNumber pianoKey;

    public PerfectPitchGuess(final PianoKeyNumber pianoKey) {
        this.pianoKey = pianoKey;
    }
}

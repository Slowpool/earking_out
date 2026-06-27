package org.swetlokognatsk.earking_out.core.domain.services.app;

import org.swetlokognatsk.earking_out.core.domain.model.guesses.Guess;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardId;
import org.swetlokognatsk.earking_out.core.ports.DI;

// TODO or PerfectPitchGuessingService?
public final class PuzzleGuessingService {

    public void guess(Guess guess) {
        // TODO use this method for NOTES_AS_CHARACTERS case
    }

    public void guessViaPianoKeyPressing(final PianoKeyboardId pianoKeyboardId, final PianoKeyNumber keyNumber) {
        // TODO
    }

    public void releasePianoKey(final PianoKeyboardId pianoKeyboardId) {
        // TODO
    }

}

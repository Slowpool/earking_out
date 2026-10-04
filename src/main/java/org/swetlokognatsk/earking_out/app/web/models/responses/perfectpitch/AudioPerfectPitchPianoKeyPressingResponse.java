package org.swetlokognatsk.earking_out.app.web.models.responses.perfectpitch;

import org.swetlokognatsk.earking_out.app.web.models.responses.GuessResponse;
import org.swetlokognatsk.earking_out.app.web.models.responses.PuzzlePianoKeyPressingResponse;
import org.swetlokognatsk.earking_out.app.web.views.models.PianoKeyboardViewModel;

public final class AudioPerfectPitchPianoKeyPressingResponse extends PuzzlePianoKeyPressingResponse {

    public final GuessResponse guessResult;

    // TODO is it possible to simplify it using lombok?
    public AudioPerfectPitchPianoKeyPressingResponse(final PianoKeyboardViewModel pianoKeyboard, final GuessResponse guessResult) {
        super(pianoKeyboard);
        this.guessResult = guessResult;
    }
}

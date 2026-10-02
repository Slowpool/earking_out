package org.swetlokognatsk.earking_out.app.web.models.responses;

import org.swetlokognatsk.earking_out.app.web.views.models.PianoKeyboardViewModel;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionStates;

public final class AudioPerfectPitchPianoKeyPressingResponse extends PuzzlePianoKeyPressingResponse {

    public final SessionStates sessionState;
    public final String newHint;
    public final int numberOfCompletedPuzzles;
    public final boolean guessIsSuccessful;

    // TODO is it possible to simplify it using lombok?
    public AudioPerfectPitchPianoKeyPressingResponse(final int numberOfCompletedPuzzles, final boolean guessIsSuccessful, final SessionStates sessionState, final PianoKeyboardViewModel pianoKeyboard, final String newHint) {
        super(pianoKeyboard);
        this.numberOfCompletedPuzzles = numberOfCompletedPuzzles;
        this.guessIsSuccessful = guessIsSuccessful;
        this.sessionState = sessionState;
        this.newHint = newHint;
    }
}

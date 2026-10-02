package org.swetlokognatsk.earking_out.app.web.models.responses;

import org.swetlokognatsk.earking_out.app.web.views.models.PianoKeyboardViewModel;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionStates;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class PuzzlePianoKeyPressingResponse {
    public final int numberOfCompletedPuzzles;
    public final boolean guessIsSuccessful;
    public final SessionStates sessionState;
    public final PianoKeyboardViewModel pianoKeyboard;
}

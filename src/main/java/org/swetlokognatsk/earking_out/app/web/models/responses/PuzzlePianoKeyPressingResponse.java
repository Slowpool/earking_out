package org.swetlokognatsk.earking_out.app.web.models.responses;

import org.swetlokognatsk.earking_out.app.web.views.models.PianoKeyboardViewModel;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class PuzzlePianoKeyPressingResponse {
    // why no GuessResponse? assumption: because piano key pressing may be just a part of a guess, e.g. when the solution is sequence of piano keys
    public final PianoKeyboardViewModel pianoKeyboard;
}

package org.swetlokognatsk.earking_out.app.web.models.responses;

import java.util.List;
import org.swetlokognatsk.earking_out.app.web.views.models.PianoKeyboardViewModel;

public class PuzzleConfigPianoKeyReleasingResponse extends PuzzleConfigPianoKeyActionResponse {

    public PuzzleConfigPianoKeyReleasingResponse(final PianoKeyboardViewModel pianoKeyboard) {
        super(pianoKeyboard);
    }

    public PuzzleConfigPianoKeyReleasingResponse(final PianoKeyboardViewModel pianoKeyboard, List<String> errors) {
        super(pianoKeyboard, errors);
    }
}

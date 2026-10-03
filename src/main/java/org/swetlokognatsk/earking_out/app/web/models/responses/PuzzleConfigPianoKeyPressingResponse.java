package org.swetlokognatsk.earking_out.app.web.models.responses;

import java.util.List;
import org.swetlokognatsk.earking_out.app.web.views.models.PianoKeyboardViewModel;

public class PuzzleConfigPianoKeyPressingResponse extends PuzzleConfigPianoKeyActionResponse {

    public PuzzleConfigPianoKeyPressingResponse(final PianoKeyboardViewModel pianoKeyboard) {
        super(pianoKeyboard);
    }

    public PuzzleConfigPianoKeyPressingResponse(final PianoKeyboardViewModel pianoKeyboard, List<String> errors) {
        super(pianoKeyboard, errors);
    }
}

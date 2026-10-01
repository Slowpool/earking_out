package org.swetlokognatsk.earking_out.app.web.models.responses;

import java.util.List;
import org.swetlokognatsk.earking_out.app.web.views.models.PianoKeyboardViewModel;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PuzzleConfigPianoKeyPressingResponse {

    public final PianoKeyboardViewModel pianoKeyboard;
    public final List<String> errors;

    public PuzzleConfigPianoKeyPressingResponse(final PianoKeyboardViewModel pianoKeyboard) {
        this.pianoKeyboard = pianoKeyboard;
        this.errors = List.of();
    }

}

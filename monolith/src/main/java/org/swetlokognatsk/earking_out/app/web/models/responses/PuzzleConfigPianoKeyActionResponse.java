package org.swetlokognatsk.earking_out.app.web.models.responses;

import java.util.List;
import org.swetlokognatsk.earking_out.app.web.views.models.PianoKeyboardViewModel;
import lombok.AllArgsConstructor;

@AllArgsConstructor
abstract class PuzzleConfigPianoKeyActionResponse {

    public final PianoKeyboardViewModel pianoKeyboard;
    public final List<String> errors;

    public PuzzleConfigPianoKeyActionResponse(final PianoKeyboardViewModel pianoKeyboard) {
        this(pianoKeyboard, List.of());
    }
}

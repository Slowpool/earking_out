package org.swetlokognatsk.earking_out.app.web.models.responses;

import org.swetlokognatsk.earking_out.app.web.views.models.PianoKeyboardViewModel;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class PuzzlePianoKeyPressingResponse {
    public final PianoKeyboardViewModel pianoKeyboard;
}

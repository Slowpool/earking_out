package org.swetlokognatsk.earking_out.app.web.models.requests;

import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardId;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PianoKeyboardActionRequest {

    public final PianoKeyboardId pianoKeyboardId;
    public final PianoKeyNumber pianoKeyNumber;
}

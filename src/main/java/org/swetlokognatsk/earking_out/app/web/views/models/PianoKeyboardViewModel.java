package org.swetlokognatsk.earking_out.app.web.views.models;

import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardId;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PianoKeyboardViewModel {

    public final PianoKeyboardId id;
    public final String htmlClass;
    public final boolean areKeySoundsEnabled;
    public final PianoKeyViewModel[] pianoKeys;
}

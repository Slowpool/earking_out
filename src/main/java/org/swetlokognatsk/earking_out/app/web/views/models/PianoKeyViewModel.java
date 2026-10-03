package org.swetlokognatsk.earking_out.app.web.views.models;

import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyColor;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PianoKeyViewModel {
    private final PianoKeyNumber keyNumber;
    private final PianoKeyColor color;
    private final boolean isPressed;
    private final boolean isSelected;

}

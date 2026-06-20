package org.swetlokognatsk.earking_out.core.domain.services.app.dto.piano.keyboard;

import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKey;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardAggregate;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.piano.key.PianoKeyDTOAssembler;

public final class PianoKeyboardDtoAssembler {

    public static PianoKeyboardDTO assemble(final PianoKeyboardAggregate pianoKeyboard) {
        var pianoKeys = pianoKeyboard.getPianoKeys().values().toArray(PianoKey[]::new);
        var pianoKeysDtos = PianoKeyDTOAssembler.assemble(pianoKeys);
        return new PianoKeyboardDTO(pianoKeyboard.getMode(), pianoKeysDtos, pianoKeyboard.getSelectedKeyNumbers(), pianoKeyboard.getPressedPianoKeyNumber());
    }
}

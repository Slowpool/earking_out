package org.swetlokognatsk.earking_out.core.domain.services.app.dto.piano.keyboard;

import org.springframework.stereotype.Component;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardAggregate;

@Component
public final class PianoKeyboardDtoAssembler {

    public PianoKeyboardDtoAssembler() {
    }

    public PianoKeyboardDTO assemble(final PianoKeyboardAggregate pianoKeyboard) {
        var pianoKeysDtos = pianoKeyboard.getPianoKeys();
        return new PianoKeyboardDTO(pianoKeyboard.getMode(), pianoKeysDtos, pianoKeyboard.getSelectedKeyNumbers(), pianoKeyboard.getPressedPianoKeyNumber());
    }
}

package org.swetlokognatsk.earking_out.core.domain.services.app.dto.piano.keyboard;

import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardId;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.piano.key.PianoKeyDTOAssembler;
import org.swetlokognatsk.earking_out.core.ports.piano.PianoKeyboardRepository;

public final class PianoKeyboardDtoAssembler {

    public PianoKeyboardDtoAssembler() {
    }

    public PianoKeyboardDTO assemble(final PianoKeyboardAggregate pianoKeyboard) {
        var pianoKeysDtos = pianoKeyboard.getPianoKeys();
        return new PianoKeyboardDTO(pianoKeyboard.getMode(), pianoKeysDtos, pianoKeyboard.getSelectedKeyNumbers(), pianoKeyboard.getPressedPianoKeyNumber());
    }
}

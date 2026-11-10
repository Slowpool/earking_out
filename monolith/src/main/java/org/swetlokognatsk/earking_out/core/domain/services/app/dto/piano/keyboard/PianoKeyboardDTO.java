package org.swetlokognatsk.earking_out.core.domain.services.app.dto.piano.keyboard;

import java.util.Map;

import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardMode;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.piano.key.PianoKeyDTO;

public record PianoKeyboardDTO(PianoKeyboardMode mode, Map<PianoKeyNumber, PianoKeyDTO> pianoKeys, PianoKeyNumber[] selectedKeys, PianoKeyNumber pressedKey) {

}

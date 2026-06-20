package org.swetlokognatsk.earking_out.core.domain.services.app.dto.piano.keyboard;

import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardMode;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.piano.key.PianoKeyDTO;

// TODO create PianoKeyNumber VO, use it everywhere
public record PianoKeyboardDTO(PianoKeyboardMode mode, PianoKeyDTO[] pianoKeys, byte[] selectedKeys, Byte pressedKey) {

}

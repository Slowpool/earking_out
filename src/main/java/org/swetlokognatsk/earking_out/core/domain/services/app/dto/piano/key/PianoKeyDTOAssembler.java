package org.swetlokognatsk.earking_out.core.domain.services.app.dto.piano.key;

import java.util.Arrays;

import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKey;

public final class PianoKeyDTOAssembler {

    public static PianoKeyDTO assemble(final PianoKey pianoKey) {
        return new PianoKeyDTO(pianoKey.keyNumber, pianoKey.color, pianoKey.getMode(), pianoKey.getIsSelected(), pianoKey.getIsPressed());
    }

    public static PianoKeyDTO[] assemble(final PianoKey[] pianoKeys) {
        var stream = Arrays.stream(pianoKeys);
        var dtosStream = stream.map((PianoKey pianoKey) -> assemble(pianoKey));
        var dtos = dtosStream.toArray(PianoKeyDTO[]::new);
        return dtos;
    }
}

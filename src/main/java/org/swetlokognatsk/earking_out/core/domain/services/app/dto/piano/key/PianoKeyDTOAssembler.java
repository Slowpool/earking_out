package org.swetlokognatsk.earking_out.core.domain.services.app.dto.piano.key;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKey;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;

public final class PianoKeyDTOAssembler {

    public static PianoKeyDTO assemble(final PianoKey pianoKey) {
        return new PianoKeyDTO(pianoKey.keyNumber, pianoKey.color, pianoKey.mode, pianoKey.getIsSelected(), pianoKey.getIsPressed());
    }

    public static PianoKeyDTO[] assemble(final PianoKey[] pianoKeys) {
        var stream = Arrays.stream(pianoKeys);
        var dtosStream = stream.map((PianoKey pianoKey) -> assemble(pianoKey));
        var dtos = dtosStream.toArray(PianoKeyDTO[]::new);
        return dtos;
    }

    public static Map<PianoKeyNumber, PianoKeyDTO> assemble(final Map<PianoKeyNumber, PianoKey> pianoKeys) {
        var dtos = new HashMap<PianoKeyNumber, PianoKeyDTO>();

        PianoKeyDTO dto;
        PianoKey pianoKey;
        for (var pianoKeyNumber : pianoKeys.keySet()) {
            pianoKey = pianoKeys.get(pianoKeyNumber);
            dto = assemble(pianoKey);
            dtos.put(pianoKeyNumber, dto);
        }
        return dtos;
    }
}

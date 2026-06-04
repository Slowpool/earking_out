package org.swetlokognatsk.earking_out.core.domain.services.domain.piano;

import org.swetlokognatsk.earking_out.core.domain.model.music.Invariants;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyColor;

// btw interface is redundant here cuz i can't imagine different implementation
public class PianoKeyColorServiceImpl implements PianoKeyColorService {
    public PianoKeyColor getColor(byte keyNumber) {
        var octaveScopedKeyNumber = (byte) ((keyNumber - Invariants.SHIFT - 1) % Invariants.KEYS_IN_OCTAVE + 1);

        var color = switch (octaveScopedKeyNumber) {
        case 1, 3, 5, 6, 8, 10, 12 -> PianoKeyColor.WHITE;
        case 2, 4, 7, 9, 11 -> PianoKeyColor.BLACK;
        default -> throw new ArithmeticException("wrong octaveScopedKeyNumber: " + octaveScopedKeyNumber);
        };

        return color;
    }

}

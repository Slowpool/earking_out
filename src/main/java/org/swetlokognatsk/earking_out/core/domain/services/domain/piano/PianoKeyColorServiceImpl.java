package org.swetlokognatsk.earking_out.core.domain.services.domain.piano;

import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyColor;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;

// btw interface is redundant here cuz i can't imagine different implementation
public class PianoKeyColorServiceImpl implements PianoKeyColorService {
    public PianoKeyColor getColor(final PianoKeyNumber keyNumber) {
        var octaveScopedKeyNumber = keyNumber.getOctaveScopedKeyNumber();

        var color = switch (octaveScopedKeyNumber) {
        case 1, 3, 5, 6, 8, 10, 12 -> PianoKeyColor.WHITE;
        case 2, 4, 7, 9, 11 -> PianoKeyColor.BLACK;
        default -> throw new ArithmeticException("wrong octaveScopedKeyNumber: " + octaveScopedKeyNumber);
        };

        return color;
    }

}

package org.swetlokognatsk.earking_out.app.desktop.helpers;

import java.util.ArrayList;
import java.util.Map;
import java.util.function.Consumer;
import org.swetlokognatsk.earking_out.app.desktop.components.PianoKey;
import org.swetlokognatsk.earking_out.core.domain.model.music.Invariants;
import org.swetlokognatsk.earking_out.core.domain.model.piano.PianoKeyColor;
import org.swetlokognatsk.earking_out.core.domain.services.piano.PianoKeyColorService;
import org.swetlokognatsk.earking_out.core.ports.DI;

public final class PianoKeysHelper {
    public static final int WHITE_KEYS = 0;
    public static final int BLACK_KEYS = 1;

    private static PianoKeyColorService colorService = DI.get(PianoKeyColorService.class);

    private PianoKeysHelper() {
    }

    /**
     * Divides `pianoKeys` into white ones and black ones
     * 
     * @param pianoKeys
     * @return
     */
    public static PianoKey[][] dichotomize(Map<Byte, PianoKey> pianoKeys) {
        var whiteKeys = new ArrayList<PianoKey>(Invariants.WHITE_PIANO_KEYS_NUMBER);
        var blackKeys = new ArrayList<PianoKey>(Invariants.BLACK_PIANO_KEYS_NUMBER);

        ArrayList<PianoKey> someKeys;
        PianoKeyColor color;
        for (Byte pianoKeyNumber : pianoKeys.keySet()) {
            color = colorService.getColor(pianoKeyNumber);
            someKeys = color == PianoKeyColor.WHITE ? whiteKeys : blackKeys;
            someKeys.add(pianoKeys.get(pianoKeyNumber));
        }

        var dichotomizedKeys = new PianoKey[2][];
        dichotomizedKeys[WHITE_KEYS] = whiteKeys.toArray(PianoKey[]::new);
        dichotomizedKeys[BLACK_KEYS] = blackKeys.toArray(PianoKey[]::new);
        return dichotomizedKeys;
    }

    public static void forEachKey(Consumer<Byte> action) {
        for (Byte keyNumber = Invariants.FIRST_NOTE_NUMBER; keyNumber < Invariants.PIANO_KEYS_NUMBER + Invariants.FIRST_NOTE_NUMBER; keyNumber++) {
            action.accept(keyNumber);
        }
    }
}

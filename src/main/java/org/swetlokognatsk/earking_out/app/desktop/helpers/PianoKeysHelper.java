package org.swetlokognatsk.earking_out.app.desktop.helpers;

import java.util.Map;
import org.swetlokognatsk.earking_out.app.desktop.components.PianoKey;
import org.swetlokognatsk.earking_out.core.domain.model.music.Invariants;

public class PianoKeysHelper {
    public static final int WHITE_KEYS = 0;
    public static final int BLACK_KEYS = 1;

    /**
     * Divides `pianoKeys` into white ones and black ones
     * @param pianoKeys
     * @return
     */
    public static PianoKey[][] dichotomize(Map<Byte, PianoKey> pianoKeys) {
        // TODO dirty, dirty code, refactoring
        var dichotomizedPianoKeys = new PianoKey[2][];
        dichotomizedPianoKeys[WHITE_KEYS] = new PianoKey[Invariants.WHITE_PIANO_KEYS_NUMBER];
        dichotomizedPianoKeys[BLACK_KEYS] = new PianoKey[Invariants.BLACK_PIANO_KEYS_NUMBER];

        int pianoKeyColor;
        int i;
        byte whiteI = 0;
        byte blackI = 0;
        for (var pianoKey : pianoKeys.values()) {
            pianoKeyColor = pianoKey.isWhite() ? WHITE_KEYS : BLACK_KEYS;
            i = pianoKey.isWhite() ? whiteI++ : blackI++;
            dichotomizedPianoKeys[pianoKeyColor][i] = pianoKey;
        }
        return dichotomizedPianoKeys;
    }
}

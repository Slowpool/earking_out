package org.swetlokognatsk.earking_out.app.desktop.helpers;

import java.util.ArrayList;
import java.util.Map;
import java.util.function.Consumer;

import org.swetlokognatsk.earking_out.app.desktop.components.PianoKey;
import org.swetlokognatsk.earking_out.core.domain.model.music.Invariants;

public class PianoKeysHelper {
    public static final int WHITE_KEYS = 0;
    public static final int BLACK_KEYS = 1;

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
        for (var pianoKey : pianoKeys.values()) {
            someKeys = pianoKey.isWhite() ? whiteKeys : blackKeys;
            someKeys.add(pianoKey);
        }
        var dichotomizedKeys = new PianoKey[][] { whiteKeys.toArray(PianoKey[]::new), blackKeys.toArray(PianoKey[]::new) };

        return dichotomizedKeys;
    }

    public static void forEachKey(Consumer<Byte> action) {
        for (Byte keyNumber = Invariants.FIRST_NOTE_NUMBER; keyNumber < Invariants.PIANO_KEYS_NUMBER + Invariants.FIRST_NOTE_NUMBER; keyNumber++) {
            action.accept(keyNumber);
        }
    }
}

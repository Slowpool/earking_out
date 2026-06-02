package org.swetlokognatsk.earking_out.core.domain.model.piano;

import static org.junit.Assert.*;
import org.apache.commons.lang3.ArrayUtils;

public final class PianoKeyboardTestHelper {

    private PianoKeyboardTestHelper() {
    }

    public static PianoKeyboardAggregate createPianoKeyboard(PianoKeyboardMode mode) {
        var pianoKeyboard = new PianoKeyboardAggregate(mode);
        return pianoKeyboard;
    }

    public static PianoKeyboardAggregate createPianoKeyboard(PianoKeyboardMode mode, byte[] selectedKeys) {
        var pianoKeyboard = new PianoKeyboardAggregate(mode, selectedKeys);
        return pianoKeyboard;
    }

    public static void assertOnlyTheseKeysAreSelected(byte[] pianoKeys, PianoKeyboardAggregate pianoKeyboard) {
        var selectedPianoKeys = pianoKeyboard.getSelectedKeyNumbers();

        assertEquals(pianoKeys.length, selectedPianoKeys.length);
        for (var pianoKey : pianoKeys) {
            assertTrue(ArrayUtils.contains(selectedPianoKeys, pianoKey));
        }
    }

    public static void assertOnlyTheseKeysAreSelected(byte key, PianoKeyboardAggregate pianoKeyboard) {
        assertOnlyTheseKeysAreSelected(new byte[] { key }, pianoKeyboard);
    }

    public static void assertNoSelectedKeys(PianoKeyboardAggregate pianoKeyboard) {
        assertOnlyTheseKeysAreSelected(new byte[0], pianoKeyboard);
    }
}

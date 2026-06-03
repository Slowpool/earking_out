package org.swetlokognatsk.earking_out.core.domain.model.piano;

import static org.junit.Assert.*;
import org.apache.commons.lang3.ArrayUtils;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardId;

public final class PianoKeyboardTestHelper {

    private PianoKeyboardTestHelper() {
    }

    public static PianoKeyboardAggregate createPianoKeyboard(PianoKeyboardId id) {
        var pianoKeyboard = new PianoKeyboardAggregate(id);
        return pianoKeyboard;
    }

    public static PianoKeyboardAggregate createPianoKeyboard(PianoKeyboardId id, byte[] selectedKeys) {
        var pianoKeyboard = new PianoKeyboardAggregate(id, selectedKeys);
        return pianoKeyboard;
    }

    public static void assertOnlyTheseKeysAreSelected(byte[] pianoKeys, PianoKeyboardAggregate pianoKeyboard) {
        var selectedPianoKeys = pianoKeyboard.getSelectedKeyNumbers();

        assertEquals(pianoKeys.length, selectedPianoKeys.length);
        for (var pianoKey : pianoKeys) {
            assertTrue(ArrayUtils.contains(selectedPianoKeys, pianoKey));
        }
    }

    public static void assertOnlyThisKeyIsSelected(byte key, PianoKeyboardAggregate pianoKeyboard) {
        assertOnlyTheseKeysAreSelected(new byte[] { key }, pianoKeyboard);
    }

    public static void assertNoSelectedKeys(PianoKeyboardAggregate pianoKeyboard) {
        assertOnlyTheseKeysAreSelected(new byte[0], pianoKeyboard);
    }
}

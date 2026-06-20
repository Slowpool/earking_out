package org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard;

import static org.junit.Assert.*;
import org.apache.commons.lang3.ArrayUtils;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKey;

public final class PianoKeyboardTestHelper {
    // NOTE factory should be instantiable right away to avoid (static -> instance) refactoring when some dependencies show up. // TODO will they?
    protected final static PianoKeyboardAggregatesFactory pianoKeyboardAggregatesFactory = new PianoKeyboardAggregatesFactory();

    public static PianoKeyboardAggregate createPianoKeyboard(final PianoKeyboardId id) {
        var pianoKeyboard = pianoKeyboardAggregatesFactory.create(id);
        return pianoKeyboard;
    }

    public static PianoKeyboardAggregate createPianoKeyboard(final PianoKeyboardId id, final byte[] selectedKeys) {
        var pianoKeyboard = pianoKeyboardAggregatesFactory.create(id, selectedKeys);
        return pianoKeyboard;
    }

    public static void assertOnlyTheseKeysAreSelected(byte[] expectedPianoKeys, PianoKeyboardAggregate pianoKeyboard) {
        var selectedPianoKeys = pianoKeyboard.getSelectedKeyNumbers();

        assertEquals(expectedPianoKeys.length, selectedPianoKeys.length);
        // TODO make it less dirty
        // check the state of PianoKeyboard
        for (var pianoKey : expectedPianoKeys) {
            assertTrue(ArrayUtils.contains(selectedPianoKeys, pianoKey));
        }

        // check the states of PianoKeys of PianoKeyboard
        PianoKey pianoKeyObj;
        Byte[] ByteExpectedPianoKeys = ArrayUtils.toObject(expectedPianoKeys);
        for (var pianoKey : ByteExpectedPianoKeys) {
            pianoKeyObj = pianoKeyboard.getPianoKeys().get(pianoKey);
            assertTrue(pianoKeyObj.getIsSelected());
        }
    }

    public static void assertOnlyThisKeyIsSelected(byte expectedKey, PianoKeyboardAggregate pianoKeyboard) {
        assertOnlyTheseKeysAreSelected(new byte[] { expectedKey }, pianoKeyboard);
    }

    public static void assertNoSelectedKeys(PianoKeyboardAggregate pianoKeyboard) {
        assertOnlyTheseKeysAreSelected(new byte[0], pianoKeyboard);
    }
}

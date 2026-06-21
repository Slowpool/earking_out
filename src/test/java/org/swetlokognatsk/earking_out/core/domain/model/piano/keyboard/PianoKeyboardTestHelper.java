package org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard;

import static org.junit.Assert.*;
import org.apache.commons.lang3.ArrayUtils;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKey;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;

public final class PianoKeyboardTestHelper {
    // NOTE factory should be instantiable right away to avoid (static -> instance) refactoring when some dependencies show up. // TODO will they? is it fine for factory to be instantiable at all? if yes, keep making them instantiable everywhere. if not, make all of them static
    protected final static PianoKeyboardAggregatesFactory pianoKeyboardAggregatesFactory = new PianoKeyboardAggregatesFactory();

    public static PianoKeyboardAggregate createPianoKeyboard(final PianoKeyboardId id) {
        var pianoKeyboard = pianoKeyboardAggregatesFactory.create(id);
        return pianoKeyboard;
    }

    public static PianoKeyboardAggregate createPianoKeyboard(final PianoKeyboardId id, final PianoKeyNumber[] selectedKeys) {
        var pianoKeyboard = pianoKeyboardAggregatesFactory.create(id, selectedKeys);
        return pianoKeyboard;
    }

    public static void assertOnlyTheseKeysAreSelected(final PianoKeyNumber[] expectedPianoKeys, final PianoKeyboardAggregate pianoKeyboard) {
        var selectedPianoKeys = pianoKeyboard.getSelectedKeyNumbers();

        assertEquals(expectedPianoKeys.length, selectedPianoKeys.length);
        // TODO make it less dirty
        // check the state of PianoKeyboard
        for (var pianoKey : expectedPianoKeys) {
            assertTrue(ArrayUtils.contains(selectedPianoKeys, pianoKey));
        }

        // check the states of PianoKeys of PianoKeyboard
        PianoKey pianoKeyObj;
        for (var pianoKey : expectedPianoKeys) {
            pianoKeyObj = pianoKeyboard.getPianoKeys().get(pianoKey);
            assertTrue(pianoKeyObj.getIsSelected());
        }
    }

    public static void assertOnlyThisKeyIsSelected(final PianoKeyNumber expectedKey, final PianoKeyboardAggregate pianoKeyboard) {
        assertOnlyTheseKeysAreSelected(new PianoKeyNumber[] { expectedKey }, pianoKeyboard);
    }

    public static void assertNoSelectedKeys(final PianoKeyboardAggregate pianoKeyboard) {
        assertOnlyTheseKeysAreSelected(new PianoKeyNumber[0], pianoKeyboard);
    }
}

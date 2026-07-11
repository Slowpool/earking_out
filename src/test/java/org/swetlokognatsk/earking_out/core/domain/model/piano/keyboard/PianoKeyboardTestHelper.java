package org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard;

import static org.junit.Assert.*;
import java.util.Map;
import org.apache.commons.lang3.ArrayUtils;
import org.swetlokognatsk.earking_out.app.desktop.helpers.PianoKeysHelper;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKey;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;
import org.swetlokognatsk.earking_out.core.ports.DI;

public final class PianoKeyboardTestHelper {
    // NOTE factory should be instantiable right away to avoid (static -> instance) refactoring when some dependencies show up
    protected final static PianoKeyboardAggregatesFactory pianoKeyboardAggregatesFactory = DI.get(PianoKeyboardAggregatesFactory.class);

    public static PianoKeyboardAggregate createPianoKeyboard(final PianoKeyboardId id) {
        var pianoKeyboard = pianoKeyboardAggregatesFactory.create(id);
        return pianoKeyboard;
    }

    public static PianoKeyboardAggregate createPianoKeyboard(final PianoKeyboardId id, final PianoKeyNumber[] selectedKeys) {
        var pianoKeyboard = pianoKeyboardAggregatesFactory.create(id, selectedKeys);
        return pianoKeyboard;
    }

    public static void assertOnlyTheseKeysAreSelected(final PianoKeyNumber[] expectedPianoKeys, final PianoKeyboardAggregate pianoKeyboard) {
        assertOnlyTheseKeysAreSelectedInPianoKeyboard(expectedPianoKeys, pianoKeyboard.getSelectedKeyNumbers());
        assertOnlyTheseKeysAreSelectedForPianoKeys(expectedPianoKeys, pianoKeyboard.getPianoKeys());
    }

    private static void assertOnlyTheseKeysAreSelectedInPianoKeyboard(final PianoKeyNumber[] expectedPianoKeys, final PianoKeyNumber[] selectedPianoKeys) {
        assertEquals(expectedPianoKeys.length, selectedPianoKeys.length);
        for (var pianoKey : expectedPianoKeys) {
            assertTrue(ArrayUtils.contains(selectedPianoKeys, pianoKey));
        }
    }

    private static void assertOnlyTheseKeysAreSelectedForPianoKeys(final PianoKeyNumber[] expectedPianoKeys, final Map<PianoKeyNumber, PianoKey> pianoKeys) {
        PianoKeysHelper.forEachKey((PianoKeyNumber pianoKey) -> {
            PianoKey pianoKeyObj = pianoKeys.get(pianoKey);
            if (ArrayUtils.contains(expectedPianoKeys, pianoKey)) {
                assertTrue(pianoKeyObj.getIsSelected());
            } else {
                assertFalse(pianoKeyObj.getIsSelected());
            }
        });
    }

    public static void assertOnlyThisKeyIsSelected(final PianoKeyNumber expectedKey, final PianoKeyboardAggregate pianoKeyboard) {
        assertOnlyTheseKeysAreSelected(new PianoKeyNumber[] { expectedKey }, pianoKeyboard);
    }

    public static void assertNoSelectedKeys(final PianoKeyboardAggregate pianoKeyboard) {
        assertOnlyTheseKeysAreSelected(new PianoKeyNumber[0], pianoKeyboard);
    }
}

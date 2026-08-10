package org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard;

import static org.junit.Assert.*;
import java.util.Map;
import org.apache.commons.lang3.ArrayUtils;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.piano.key.PianoKeyDTO;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.piano.keyboard.PianoKeyboardDTO;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.piano.keyboard.PianoKeyboardDtoAssembler;
import org.swetlokognatsk.earking_out.core.ports.di.DI;

public final class PianoKeyboardTestHelper {
    // NOTE factory should be instantiable right away to avoid (static -> instance) refactoring when some dependencies show up
    private final static PianoKeyboardAggregatesFactory pianoKeyboardAggregatesFactory = DI.get(PianoKeyboardAggregatesFactory.class);
    private static final PianoKeyboardDtoAssembler pianoKeyboardDtoAssembler = DI.get(PianoKeyboardDtoAssembler.class);

    public static PianoKeyboardAggregate createPianoKeyboard(final PianoKeyboardId id) {
        var pianoKeyboard = pianoKeyboardAggregatesFactory.create(id);
        return pianoKeyboard;
    }

    public static PianoKeyboardAggregate createPianoKeyboard(final PianoKeyboardId id, final PianoKeyNumber[] selectedKeys) {
        var pianoKeyboard = pianoKeyboardAggregatesFactory.create(id, selectedKeys);
        return pianoKeyboard;
    }

    public static void assertOnlyTheseKeysAreSelected(final PianoKeyNumber[] expectedPianoKeys, final PianoKeyboardDTO pianoKeyboard) {
        assertOnlyTheseKeysAreSelectedInPianoKeyboard(expectedPianoKeys, pianoKeyboard.selectedKeys());
        assertOnlyTheseKeysAreSelectedForPianoKeys(expectedPianoKeys, pianoKeyboard.pianoKeys());
    }

    public static void assertOnlyTheseKeysAreSelected(final PianoKeyNumber[] expectedPianoKeys, final PianoKeyboardAggregate pianoKeyboard) {
        var pianoKeyboardDto = pianoKeyboardDtoAssembler.assemble(pianoKeyboard);
        assertOnlyTheseKeysAreSelected(expectedPianoKeys, pianoKeyboardDto);
    }

    private static void assertOnlyTheseKeysAreSelectedInPianoKeyboard(final PianoKeyNumber[] expectedPianoKeys, final PianoKeyNumber[] selectedPianoKeys) {
        assertEquals(expectedPianoKeys.length, selectedPianoKeys.length);
        for (var pianoKey : expectedPianoKeys) {
            assertTrue(ArrayUtils.contains(selectedPianoKeys, pianoKey));
        }
    }

    private static void assertOnlyTheseKeysAreSelectedForPianoKeys(final PianoKeyNumber[] expectedPianoKeys, final Map<PianoKeyNumber, PianoKeyDTO> pianoKeys) {
        PianoKeyNumber.forEachKey((PianoKeyNumber pianoKeyNumber) -> {
            PianoKeyDTO pianoKey = pianoKeys.get(pianoKeyNumber);
            if (ArrayUtils.contains(expectedPianoKeys, pianoKeyNumber)) {
                assertTrue(pianoKey.isSelected());
            } else {
                assertFalse(pianoKey.isSelected());
            }
        });
    }

    public static void assertOnlyThisKeyIsSelected(final PianoKeyNumber expectedKey, final PianoKeyboardDTO pianoKeyboard) {
        assertOnlyTheseKeysAreSelected(new PianoKeyNumber[] { expectedKey }, pianoKeyboard);
    }

    public static void assertOnlyThisKeyIsSelected(final PianoKeyNumber expectedKey, final PianoKeyboardAggregate pianoKeyboard) {
        var pianoKeyboardDto = pianoKeyboardDtoAssembler.assemble(pianoKeyboard);
        assertOnlyThisKeyIsSelected(expectedKey, pianoKeyboardDto);
    }

    public static void assertNoSelectedKeys(final PianoKeyboardDTO pianoKeyboard) {
        assertOnlyTheseKeysAreSelected(new PianoKeyNumber[0], pianoKeyboard);
    }

    public static void assertNoSelectedKeys(final PianoKeyboardAggregate pianoKeyboard) {
        var pianoKeyboardDto = pianoKeyboardDtoAssembler.assemble(pianoKeyboard);
        assertNoSelectedKeys(pianoKeyboardDto);
    }

}

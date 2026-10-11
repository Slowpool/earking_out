package org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard;

import org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Map;
import org.apache.commons.lang3.ArrayUtils;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.piano.key.PianoKeyDTO;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.piano.keyboard.PianoKeyboardDTO;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.piano.keyboard.PianoKeyboardDtoAssembler;
import org.swetlokognatsk.earking_out.core.ports.identity.UserResolver;
import org.swetlokognatsk.earking_out.infrastructure.annotations.TestComponent;
import lombok.AllArgsConstructor;

@TestComponent
@AllArgsConstructor
public final class PianoKeyboardTestHelper {

    private final PianoKeyboardAggregatesFactory pianoKeyboardAggregatesFactory;
    private final PianoKeyboardDtoAssembler pianoKeyboardDtoAssembler;
    private final UserResolver userResolver;

    public PianoKeyboardAggregate createPianoKeyboard(final PianoKeyboardId id) {
        return createPianoKeyboard(id, new PianoKeyNumber[0]);
    }

    public PianoKeyboardAggregate createPianoKeyboard(final PianoKeyboardId id, final PianoKeyNumber[] selectedKeys) {
        var userId = userResolver.getCurrentUserId();
        var ownedPianoKeyboardId = new OwnedPianoKeyboardId(id, userId);
        var pianoKeyboard = pianoKeyboardAggregatesFactory.create(ownedPianoKeyboardId, selectedKeys);
        return pianoKeyboard;
    }

    public void assertOnlyTheseKeysAreSelected(final PianoKeyNumber[] expectedPianoKeys, final PianoKeyboardDTO pianoKeyboard) {
        assertOnlyTheseKeysAreSelectedInPianoKeyboard(expectedPianoKeys, pianoKeyboard.selectedKeys());
        assertOnlyTheseKeysAreSelectedForPianoKeys(expectedPianoKeys, pianoKeyboard.pianoKeys());
    }

    public void assertOnlyTheseKeysAreSelected(final PianoKeyNumber[] expectedPianoKeys, final PianoKeyboardAggregate pianoKeyboard) {
        var pianoKeyboardDto = pianoKeyboardDtoAssembler.assemble(pianoKeyboard);
        assertOnlyTheseKeysAreSelected(expectedPianoKeys, pianoKeyboardDto);
    }

    private void assertOnlyTheseKeysAreSelectedInPianoKeyboard(final PianoKeyNumber[] expectedPianoKeys, final PianoKeyNumber[] selectedPianoKeys) {
        assertEquals(expectedPianoKeys.length, selectedPianoKeys.length);
        for (var pianoKey : expectedPianoKeys) {
            assertTrue(ArrayUtils.contains(selectedPianoKeys, pianoKey));
        }
    }

    private void assertOnlyTheseKeysAreSelectedForPianoKeys(final PianoKeyNumber[] expectedPianoKeys, final Map<PianoKeyNumber, PianoKeyDTO> pianoKeys) {
        PianoKeyNumber.forEachKey((PianoKeyNumber pianoKeyNumber) -> {
            PianoKeyDTO pianoKey = pianoKeys.get(pianoKeyNumber);
            if (ArrayUtils.contains(expectedPianoKeys, pianoKeyNumber)) {
                assertTrue(pianoKey.isSelected());
            } else {
                assertFalse(pianoKey.isSelected());
            }
        });
    }

    public void assertOnlyThisKeyIsSelected(final PianoKeyNumber expectedKey, final PianoKeyboardDTO pianoKeyboard) {
        assertOnlyTheseKeysAreSelected(new PianoKeyNumber[] { expectedKey }, pianoKeyboard);
    }

    public void assertOnlyThisKeyIsSelected(final PianoKeyNumber expectedKey, final PianoKeyboardAggregate pianoKeyboard) {
        var pianoKeyboardDto = pianoKeyboardDtoAssembler.assemble(pianoKeyboard);
        assertOnlyThisKeyIsSelected(expectedKey, pianoKeyboardDto);
    }

    public void assertNoSelectedKeys(final PianoKeyboardDTO pianoKeyboard) {
        assertOnlyTheseKeysAreSelected(new PianoKeyNumber[0], pianoKeyboard);
    }

    public void assertNoSelectedKeys(final PianoKeyboardAggregate pianoKeyboard) {
        var pianoKeyboardDto = pianoKeyboardDtoAssembler.assemble(pianoKeyboard);
        assertNoSelectedKeys(pianoKeyboardDto);
    }

    public void assertNoPressedKeys(final PianoKeyboardAggregate pianoKeyboard) {
        var pianoKeyboardDto = pianoKeyboardDtoAssembler.assemble(pianoKeyboard);
        assertNoPressedKeys(pianoKeyboardDto);
    }

    public void assertNoPressedKeys(final PianoKeyboardDTO pianoKeyboard) {
        assertNull(pianoKeyboard.pressedKey());
    }

    public void assertDoesNotHaveEvents(final PianoKeyboardAggregate pianoKeyboard) {
        var events = pianoKeyboard.flushEvents();
        assertEquals(0, events.size());
    }
}

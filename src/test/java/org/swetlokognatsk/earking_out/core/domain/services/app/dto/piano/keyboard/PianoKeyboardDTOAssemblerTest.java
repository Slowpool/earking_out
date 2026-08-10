package org.swetlokognatsk.earking_out.core.domain.services.app.dto.piano.keyboard;

import static org.junit.Assert.*;
import static org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber.*;
import org.junit.*;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardAggregatesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardId;
import org.swetlokognatsk.earking_out.core.ports.di.DI;

/**
 * idea: to test the dto assembling itself, we need only 2 pairs of aggregate
 * and corresponding dto. both aggregates' fields must be different, so that
 * aggregate.x != dto.x for each field. then, if mapping of x is correct for
 * first and second pair, then mapping of this field works fine. this way we
 * test each field and if mapping works fine for these 2 pairs, then the whole
 * mapping works fine. using extra pairs (third, fourth and further) for mapping
 * itself is redundant.
 */
public final class PianoKeyboardDTOAssemblerTest {
    private final PianoKeyboardAggregatesFactory pianoKeyboardFactory;
    private final PianoKeyboardDtoAssembler pianoKeyboardDtoAssembler;

    private final PianoKeyboardAggregate pianoKeyboard1;
    private final PianoKeyboardDTO dto1;

    private final PianoKeyboardAggregate pianoKeyboard2;
    private final PianoKeyboardDTO dto2;

    public PianoKeyboardDTOAssemblerTest() {
        pianoKeyboardFactory = DI.get(PianoKeyboardAggregatesFactory.class);
        pianoKeyboardDtoAssembler = DI.get(PianoKeyboardDtoAssembler.class);

        var selectedKeys = new PianoKeyNumber[] { FIRST_NOTE_NUMBER, FIRST_NOTE_NUMBER.increment() };
        pianoKeyboard1 = pianoKeyboardFactory.create(PianoKeyboardId.AUDIO_PERFECT_PITCH_NOTES_PICKER, selectedKeys);
        dto1 = pianoKeyboardDtoAssembler.assemble(pianoKeyboard1);

        pianoKeyboard2 = pianoKeyboardFactory.create(PianoKeyboardId.AUDIO_PERFECT_PITCH_ROOT_NOTE_PICKER);
        pianoKeyboard2.pressKey(FIRST_NOTE_NUMBER.add(3));
        dto2 = pianoKeyboardDtoAssembler.assemble(pianoKeyboard2);
    }

    @Test
    public void mode1() {
        assertEquals(dto1.mode(), pianoKeyboard1.getMode());
    }

    @Test
    public void mode2() {
        assertEquals(dto2.mode(), pianoKeyboard2.getMode());
    }

    // responsibility for further dto assembling is on PianoKeyDTOAssembler. so just testing the general stuff (number of elements, dto is null or is not null and etc.)
    @Test
    public void pianoKeys() {
        assertNotNull(dto1.pianoKeys());
        assertEquals(pianoKeyboard1.getPianoKeys().size(), dto1.pianoKeys().size());

        assertNotNull(dto2.pianoKeys());
        assertEquals(pianoKeyboard2.getPianoKeys().size(), dto2.pianoKeys().size());
    }

    @Test
    public void selectedKeys() {
        assertNotNull(dto1.selectedKeys());
        assertEquals(pianoKeyboard1.getSelectedKeyNumbers().length, dto1.selectedKeys().length);

        assertNotNull(dto2.selectedKeys());
        assertEquals(pianoKeyboard2.getSelectedKeyNumbers().length, dto2.selectedKeys().length);
    }

    @Test
    public void pressedKey() {
        assertNull(dto1.pressedKey());
        assertNotNull(dto2.pressedKey());
    }
}

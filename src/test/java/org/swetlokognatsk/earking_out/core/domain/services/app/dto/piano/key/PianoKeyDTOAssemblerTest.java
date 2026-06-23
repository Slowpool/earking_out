package org.swetlokognatsk.earking_out.core.domain.services.app.dto.piano.key;

import static org.junit.Assert.assertEquals;
import static org.swetlokognatsk.earking_out.core.domain.model.music.Invariants.*;
import org.junit.*;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKey;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyMode;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeysFactory;
import org.swetlokognatsk.earking_out.core.ports.DI;

/**
 * See
 * {@link org.swetlokognatsk.earking_out.core.domain.services.app.dto.piano.keyboard.PianoKeyboardDTOAssemblerTest}
 * class description for explanation.
 */
public final class PianoKeyDTOAssemblerTest {
    protected final PianoKeysFactory pianoKeysFactory;

    protected final PianoKey pianoKey1;
    protected final PianoKeyDTO dto1;

    protected final PianoKey pianoKey2;
    protected final PianoKeyDTO dto2;

    public PianoKeyDTOAssemblerTest() {
        pianoKeysFactory = DI.get(PianoKeysFactory.class);

        pianoKey1 = pianoKeysFactory.create(FIRST_NOTE_NUMBER, PianoKeyMode.SELECT);
        dto1 = PianoKeyDTOAssembler.assemble(pianoKey1);

        pianoKey2 = pianoKeysFactory.create(FIRST_NOTE_NUMBER.increment(), PianoKeyMode.TOUCH);
        pianoKey2.press();
        pianoKey2.select();
        dto2 = PianoKeyDTOAssembler.assemble(pianoKey2);
    }

    @Test
    public void keyNumber1() {
        assertEquals(pianoKey1.keyNumber, dto1.keyNumber());
    }

    @Test
    public void keyNumber2() {
        assertEquals(pianoKey2.keyNumber, dto2.keyNumber());
    }

    @Test
    public void color1() {
        assertEquals(pianoKey1.color, dto1.color());
    }

    @Test
    public void color2() {
        assertEquals(pianoKey2.color, dto2.color());
    }

    @Test
    public void mode1() {
        assertEquals(pianoKey1.getMode(), dto1.mode());
    }

    @Test
    public void mode2() {
        assertEquals(pianoKey2.getMode(), dto2.mode());
    }

    @Test
    public void isSelected1() {
        assertEquals(pianoKey1.getIsSelected(), dto1.isSelected());
    }

    @Test
    public void isSelected2() {
        assertEquals(pianoKey2.getIsSelected(), dto2.isSelected());
    }

    @Test
    public void isPressed1() {
        assertEquals(pianoKey1.getIsPressed(), dto1.isPressed());
    }

    @Test
    public void isPressed2() {
        assertEquals(pianoKey2.getIsPressed(), dto2.isPressed());
    }

}

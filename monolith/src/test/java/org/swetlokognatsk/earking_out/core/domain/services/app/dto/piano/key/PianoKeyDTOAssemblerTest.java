package org.swetlokognatsk.earking_out.core.domain.services.app.dto.piano.key;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.swetlokognatsk.earking_out.core.domain.model.music.Constants.*;
import static org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber.*;
import org.junit.jupiter.api.*;
import org.swetlokognatsk.earking_out.EOSpringBootTest;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKey;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyMode;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeysFactory;
import org.swetlokognatsk.earking_out.core.ports.di.DI;

/**
 * See
 * {@link org.swetlokognatsk.earking_out.core.domain.services.app.dto.piano.keyboard.PianoKeyboardDTOAssemblerTest}
 * class description for explanation.
 */
@EOSpringBootTest
public final class PianoKeyDTOAssemblerTest {
    private PianoKeysFactory pianoKeysFactory;

    private PianoKey pianoKey1;
    private PianoKeyDTO dto1;

    private PianoKey pianoKey2;
    private PianoKeyDTO dto2;

    // TODO WHAT! HOW IT WORKS!? 
    @BeforeEach
    void setup() {
        pianoKeysFactory = DI.get(PianoKeysFactory.class);

        pianoKey1 = pianoKeysFactory.create(FIRST_NOTE_NUMBER, PianoKeyMode.SELECT);
        dto1 = PianoKeyDTOAssembler.assemble(pianoKey1);

        pianoKey2 = pianoKeysFactory.create(FIRST_NOTE_NUMBER.increment(), PianoKeyMode.TOUCH);
        pianoKey2.press();
        pianoKey2.select();
        dto2 = PianoKeyDTOAssembler.assemble(pianoKey2);
    }

    @Test
    void keyNumber1() {
        assertEquals(pianoKey1.keyNumber, dto1.keyNumber());
    }

    @Test
    void keyNumber2() {
        assertEquals(pianoKey2.keyNumber, dto2.keyNumber());
    }

    @Test
    void color1() {
        assertEquals(pianoKey1.color, dto1.color());
    }

    @Test
    void color2() {
        assertEquals(pianoKey2.color, dto2.color());
    }

    @Test
    void mode1() {
        assertEquals(pianoKey1.mode, dto1.mode());
    }

    @Test
    void mode2() {
        assertEquals(pianoKey2.mode, dto2.mode());
    }

    @Test
    void isSelected1() {
        assertEquals(pianoKey1.getIsSelected(), dto1.isSelected());
    }

    @Test
    void isSelected2() {
        assertEquals(pianoKey2.getIsSelected(), dto2.isSelected());
    }

    @Test
    void isPressed1() {
        assertEquals(pianoKey1.getIsPressed(), dto1.isPressed());
    }

    @Test
    void isPressed2() {
        assertEquals(pianoKey2.getIsPressed(), dto2.isPressed());
    }

}

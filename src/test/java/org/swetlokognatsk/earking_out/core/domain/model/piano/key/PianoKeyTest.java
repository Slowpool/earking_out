package org.swetlokognatsk.earking_out.core.domain.model.piano.key;

import static org.junit.Assert.*;
import static org.swetlokognatsk.earking_out.core.domain.model.music.Invariants.*;
import java.util.Objects;
import org.junit.*;
import org.swetlokognatsk.earking_out.app.desktop.helpers.PianoKeysHelper;

public final class PianoKeyTest {
    protected static PianoKeyNumber ANY_PIANO_KEY_NUMBER = FIRST_NOTE_NUMBER;

    @Test
    public void pianoKeyColorTest() {
        PianoKeysHelper.forEachKey((PianoKeyNumber keyNumber) -> {
            var pianoKey = PianoKeysFactory.create(keyNumber, PianoKeyMode.TOUCH);
            var expectedColor = getExpectedPianoKeyColor(keyNumber);
            assertEquals(expectedColor, pianoKey.color);
        });
    }

    protected static PianoKeyColor getExpectedPianoKeyColor(final PianoKeyNumber keyNumber) {
        Objects.nonNull(keyNumber);

        return switch (keyNumber.octaveScopedKeyNumber) {
        case 1, 3, 5, 6, 8, 10, 12 -> PianoKeyColor.WHITE;
        case 2, 4, 7, 9, 11 -> PianoKeyColor.BLACK;
        default -> throw new ArithmeticException("incorrect octaveScopedKeyNumber: " + keyNumber.octaveScopedKeyNumber);
        };
    }

    @Test
    public void pressPianoKeyInTouchMode() {
        var pianoKey = PianoKeysFactory.create(ANY_PIANO_KEY_NUMBER, PianoKeyMode.TOUCH);

        pianoKey.press();
        assertTrue(pianoKey.getIsPressed());

        pianoKey.release();
        assertFalse(pianoKey.getIsPressed());
    }

    @Test
    public void releasePianoKeyInTouchMode() {
        var pianoKey = PianoKeysFactory.create(ANY_PIANO_KEY_NUMBER, PianoKeyMode.TOUCH);

        try {
            pianoKey.release();
            fail();
        } catch (IllegalStateException e) {
        }
    }

    @Test
    public void pressTwicePianoKeyInTouchMode() {
        var pianoKey = PianoKeysFactory.create(ANY_PIANO_KEY_NUMBER, PianoKeyMode.TOUCH);

        pianoKey.press();
        try {
            pianoKey.press();
            fail();
        } catch (IllegalStateException e) {
        }
    }
}

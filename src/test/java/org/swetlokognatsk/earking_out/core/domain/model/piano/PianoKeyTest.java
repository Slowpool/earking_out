package org.swetlokognatsk.earking_out.core.domain.model.piano;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.swetlokognatsk.earking_out.app.desktop.helpers.PianoKeysHelper;
import org.swetlokognatsk.earking_out.core.domain.model.music.Invariants;

public final class PianoKeyTest {
    protected static byte ANY_PIANO_KEY_NUMBER = 4;

    @Test
    public void pianoKeyColorTest() {
        PianoKeysHelper.forEachKey((Byte keyNumber) -> {
            var pianoKey = PianoKeysFactory.create((byte) keyNumber, PianoKeyMode.TOUCH);
            var expectedColor = getExpectedPianoKeyColor(keyNumber);
            assertEquals(expectedColor, pianoKey.color);
        });
    }

    protected PianoKeyColor getExpectedPianoKeyColor(byte keyNumber) {
        return switch (keyNumber - Invariants.SHIFT) {
        case
        1+(0*12), 3+(0*12), 5+(0*12), 6+(0*12), 8+(0*12), 10+(0*12), 12+(0*12),
        1+(1*12), 3+(1*12), 5+(1*12), 6+(1*12), 8+(1*12), 10+(1*12), 12+(1*12),
        1+(2*12), 3+(2*12), 5+(2*12), 6+(2*12), 8+(2*12), 10+(2*12), 12+(2*12),
        1+(3*12), 3+(3*12), 5+(3*12), 6+(3*12), 8+(3*12), 10+(3*12), 12+(3*12),
        1+(4*12), 3+(4*12), 5+(4*12), 6+(4*12), 8+(4*12), 10+(4*12), 12+(4*12),
        1+(5*12), 3+(5*12), 5+(5*12), 6+(5*12), 8+(5*12), 10+(5*12), 12+(5*12),
        1+(6*12), 3+(6*12), 5+(6*12), 6+(6*12), 8+(6*12), 10+(6*12), 12+(6*12)
         -> PianoKeyColor.WHITE;
        case
        2+(12*0), 4+(12*0), 7+(12*0), 9+(12*0), 11+(12*0),
        2+(12*1), 4+(12*1), 7+(12*1), 9+(12*1), 11+(12*1),
        2+(12*2), 4+(12*2), 7+(12*2), 9+(12*2), 11+(12*2),
        2+(12*3), 4+(12*3), 7+(12*3), 9+(12*3), 11+(12*3),
        2+(12*4), 4+(12*4), 7+(12*4), 9+(12*4), 11+(12*4),
        2+(12*5), 4+(12*5), 7+(12*5), 9+(12*5), 11+(12*5),
        2+(12*6), 4+(12*6), 7+(12*6), 9+(12*6), 11+(12*6)
        -> PianoKeyColor.BLACK;
        default -> throw new ArithmeticException();
        };
    }

    @Test
    public void pressPianoKeyInTouchMode() {
        var pianoKey = PianoKeysFactory.create(ANY_PIANO_KEY_NUMBER, PianoKeyMode.TOUCH);

        pianoKey.press();
        assertTrue(pianoKey.getIsSelected());

        pianoKey.release();
        assertFalse(pianoKey.getIsSelected());
    }

    @Test
    public void releasePianoKeyInTouchMode() {
        var pianoKey = PianoKeysFactory.create(ANY_PIANO_KEY_NUMBER, PianoKeyMode.TOUCH);

        try {
            pianoKey.release();
            fail();
        }
        catch (IllegalStateException e) {
        }
    }

    @Test
    public void pressTwicePianoKeyInTouchMode() {
        var pianoKey = PianoKeysFactory.create(ANY_PIANO_KEY_NUMBER, PianoKeyMode.TOUCH);

        pianoKey.press();
        try {
            pianoKey.press();
            fail();
        }
        catch (IllegalStateException e) {
        }
    }
}

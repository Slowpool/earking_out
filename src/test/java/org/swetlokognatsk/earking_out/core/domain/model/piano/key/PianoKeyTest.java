package org.swetlokognatsk.earking_out.core.domain.model.piano.key;

import static org.junit.jupiter.api.Assertions.*;
import static org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber.*;
import java.util.Objects;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.swetlokognatsk.earking_out.core.ports.di.DI;
import org.swetlokognatsk.earking_out.infrastructure.adapters.piano.MockPianoKeySoundsPlayer;

@SpringBootTest
public final class PianoKeyTest {
    private static PianoKeysFactory pianoKeysFactory;
    private static PianoKeyNumber ANY_PIANO_KEY_NUMBER = FIRST_NOTE_NUMBER;

    private MockPianoKeySoundsPlayer mockSoundPlayer;

    @BeforeAll
    public static void initializeCdommonContext() {
        pianoKeysFactory = DI.get(PianoKeysFactory.class);
    }

    @BeforeEach
    public void setup() {
        // TODO mockito?
        mockSoundPlayer = new MockPianoKeySoundsPlayer();
    }

    @Test
    public void pianoKeyColorTest() {
        PianoKeyNumber.forEachKey((PianoKeyNumber keyNumber) -> {
            var pianoKey = pianoKeysFactory.create(keyNumber, PianoKeyMode.TOUCH);
            var expectedColor = getExpectedPianoKeyColor(keyNumber);
            assertEquals(expectedColor, pianoKey.color);
        });
    }

    private static PianoKeyColor getExpectedPianoKeyColor(final PianoKeyNumber keyNumber) {
        Objects.requireNonNull(keyNumber);

        return switch (keyNumber.octaveScopedKeyNumber) {
        case 1, 3, 5, 6, 8, 10, 12 -> PianoKeyColor.WHITE;
        case 2, 4, 7, 9, 11 -> PianoKeyColor.BLACK;
        default -> throw new ArithmeticException("incorrect octaveScopedKeyNumber: " + keyNumber.octaveScopedKeyNumber);
        };
    }

    @Test
    public void pressPianoKeyInTouchMode() {
        var pianoKey = pianoKeysFactory.create(ANY_PIANO_KEY_NUMBER, PianoKeyMode.TOUCH);

        pianoKey.press();
        assertTrue(pianoKey.getIsPressed());

        pianoKey.release();
        assertFalse(pianoKey.getIsPressed());
    }

    @Test
    public void releasePianoKeyInTouchMode() {
        var pianoKey = pianoKeysFactory.create(ANY_PIANO_KEY_NUMBER, PianoKeyMode.TOUCH);

        try {
            pianoKey.release();
            fail();
        } catch (IllegalStateException e) {
        }
    }

    @Test
    public void pressTwicePianoKeyInTouchMode() {
        var pianoKey = pianoKeysFactory.create(ANY_PIANO_KEY_NUMBER, PianoKeyMode.TOUCH);

        pianoKey.press();
        try {
            pianoKey.press();
            fail();
        } catch (IllegalStateException e) {
        }
    }
}

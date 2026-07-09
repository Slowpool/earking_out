package org.swetlokognatsk.earking_out.core.domain.model.piano.key;

import static org.junit.Assert.*;
import static org.swetlokognatsk.earking_out.core.domain.model.music.Constants.*;
import java.util.Objects;
import org.junit.*;
import org.swetlokognatsk.earking_out.app.desktop.helpers.PianoKeysHelper;
import org.swetlokognatsk.earking_out.core.ports.DI;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.piano.MockPianoKeySoundsPlayer;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.sounds.MockSoundPlayer;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.sounds.SoundPlayer;
import org.swetlokognatsk.earking_out.core.domain.services.domain.piano.PianoKeyColorService;

public final class PianoKeyTest {
    protected static PianoKeyNumber ANY_PIANO_KEY_NUMBER = FIRST_NOTE_NUMBER;
    protected final PianoKeysFactory pianoKeysFactory;

    protected MockPianoKeySoundsPlayer mockSoundPlayer;

    // TODO @BeforeClass or constructor?
    public PianoKeyTest() {
        pianoKeysFactory = DI.get(PianoKeysFactory.class);
    }

    @Before
    public void setup() {
        // TODO mockito?
        mockSoundPlayer = new MockPianoKeySoundsPlayer();
    }

    @Test
    public void pianoKeyColorTest() {
        PianoKeysHelper.forEachKey((PianoKeyNumber keyNumber) -> {
            var pianoKey = pianoKeysFactory.create(keyNumber, PianoKeyMode.TOUCH);
            var expectedColor = getExpectedPianoKeyColor(keyNumber);
            assertEquals(expectedColor, pianoKey.color);
        });
    }

    protected static PianoKeyColor getExpectedPianoKeyColor(final PianoKeyNumber keyNumber) {
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

    @Test
    public void playSoundOnPress() {
        var pianoKey = new PianoKey(ANY_PIANO_KEY_NUMBER, PianoKeyMode.TOUCH, false, DI.get(PianoKeyColorService.class), mockSoundPlayer);
        pianoKey.press();
        assertTrue(mockSoundPlayer.stopAndPlayIsPressed);
    }

}

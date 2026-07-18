package org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard;

import static org.junit.Assert.*;
import org.junit.Test;
import org.swetlokognatsk.earking_out.app.desktop.helpers.PianoKeysHelper;
import org.swetlokognatsk.earking_out.core.domain.helpers.PianoKeyboardHelper;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;
import org.swetlokognatsk.earking_out.core.ports.DI;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.piano.MockPianoKeySoundsPlayer;

public final class PianoKeyboardSoundModeTest extends PianoKeyboardTest {

    protected PianoKeyboardId getSomeSuitablePianoKeyboardId() {
        return PianoKeyboardId.AUDIO_PERFECT_PITCH_NOTES_GUESSING;
    }

    @Test
    public void allKeysAreSoundlessForSoundlessMode() {
        var pianoKeyboard = createPianoKeyboard(PianoKeyboardSoundMode.SOUNDLESS);
        PianoKeyNumber.forEachKey((PianoKeyNumber pianoKeyNumber) -> {
            pianoKeyboard.touchKey(pianoKeyNumber);
        });

        var mockPianoKeySoundsPlayer = DI.get(MockPianoKeySoundsPlayer.class);
        assertFalse(mockPianoKeySoundsPlayer.playIsPressed);
        assertFalse(mockPianoKeySoundsPlayer.stopIsPressed);
        assertFalse(mockPianoKeySoundsPlayer.stopAndPlayIsPressed);
    }

    @Test
    public void allKeysMakeSoundForUsualMode() {
        PianoKeyNumber.forEachKey((PianoKeyNumber pianoKeyNumber) -> {
            var pianoKeyboard = createPianoKeyboard(PianoKeyboardSoundMode.USUAL);

            pianoKeyboard.touchKey(pianoKeyNumber);

            var mockPianoKeySoundsPlayer = DI.get(MockPianoKeySoundsPlayer.class);
            assertFalse(mockPianoKeySoundsPlayer.playIsPressed);
            assertFalse(mockPianoKeySoundsPlayer.stopIsPressed);
            assertTrue(mockPianoKeySoundsPlayer.stopAndPlayIsPressed);
            // this is needed to refresh mock sound service state
            DI.deleteSingletons();
        });
    }

}

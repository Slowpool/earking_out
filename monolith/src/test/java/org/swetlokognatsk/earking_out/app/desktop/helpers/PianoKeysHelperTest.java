package org.swetlokognatsk.earking_out.app.desktop.helpers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.swetlokognatsk.earking_out.core.domain.model.music.Constants.*;
import org.junit.jupiter.api.Test;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;

public final class PianoKeysHelperTest {

    private int numberOfKeysTraversed = 0;

    @Test
    public void forEachKeyTraverseAllNotes() {
        PianoKeyNumber.forEachKey(pianoKeyNumber -> numberOfKeysTraversed++);
        assertEquals(PIANO_KEYS_NUMBER, numberOfKeysTraversed);
    }

}

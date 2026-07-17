package org.swetlokognatsk.earking_out.app.desktop.helpers;

import static org.junit.Assert.assertEquals;
import static org.swetlokognatsk.earking_out.core.domain.model.music.Constants.*;
import static org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber.*;
import org.junit.*;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;

public final class PianoKeysHelperTest {

    protected int numberOfKeysTraversed = 0;

    @Test
    public void forEachKeyTraverseAllNotes() {
        PianoKeyNumber.forEachKey(pianoKeyNumber -> numberOfKeysTraversed++);
        assertEquals(PIANO_KEYS_NUMBER, numberOfKeysTraversed);
    }

}

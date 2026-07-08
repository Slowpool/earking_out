package org.swetlokognatsk.earking_out.core.domain.model.music;

import static org.junit.Assert.*;
import org.junit.*;
import org.swetlokognatsk.earking_out.core.domain.model.music.Accidentals;
import org.swetlokognatsk.earking_out.core.domain.model.music.NoteNames;
import org.swetlokognatsk.earking_out.core.domain.model.music.Octaves;
import org.swetlokognatsk.earking_out.core.domain.model.music.sounds.Note;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;

public class MusicTest {

    @Test
    public void createNote() {
        var note = new Note(NoteNames.C, Accidentals.SHARP, Octaves.FIRST);

        int shift = 3;
        int selectedNote = 1;
        int sharp = 1;
        int octave = 1;
        byte correctNormalizedValue = (byte) (shift + selectedNote + 12 * (octave - 1) + sharp);
        assertEquals(correctNormalizedValue, 5);
        var keyNumber = PianoKeyNumber.valueOf(correctNormalizedValue);
        assertEquals(note.normalize(), keyNumber);
    }
}

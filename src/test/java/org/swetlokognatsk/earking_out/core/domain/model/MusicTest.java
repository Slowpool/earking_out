package org.swetlokognatsk.earking_out.core.domain.model;

import static org.junit.Assert.*;
import org.junit.*;
import org.swetlokognatsk.earking_out.core.domain.model.music.Accidentals;
import org.swetlokognatsk.earking_out.core.domain.model.music.NoteNames;
import org.swetlokognatsk.earking_out.core.domain.model.music.NoteWithAccidental;
import org.swetlokognatsk.earking_out.core.domain.model.music.Octaves;
import org.swetlokognatsk.earking_out.core.ports.DI;
import org.swetlokognatsk.earking_out.core.ports.music.INoteNormalizer;

public class MusicTest {
    @Test
    public void createNoteWithAccidental() {
        var noteWithAccidental = new NoteWithAccidental(NoteNames.C, Accidentals.SHARP, Octaves.FIRST);

        int shift = 3;
        int selectedNote = 1;
        int sharp = 1;
        int octave = 1;
        byte correctNormalizedValue = (byte)(shift + selectedNote + 12 * (octave - 1) + sharp);
        assertEquals(correctNormalizedValue, 5);

        assertEquals(noteWithAccidental.normalize(), correctNormalizedValue);
    }
}

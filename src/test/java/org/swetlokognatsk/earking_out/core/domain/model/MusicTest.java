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
        // TODO mark somewhere skipping 
        int shift = 3;
        int selectedNote = 0;
        int octave = 1;
        // adds one semitone
        int sharp = 1;

        assertEquals(noteWithAccidental.normalize(), shift + selectedNote + 12 * (octave - 1) + sharp);
    }
}

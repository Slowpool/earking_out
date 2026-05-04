package org.swetlokognatsk.earking_out.core.domain.model;

import static org.junit.Assert.*;

import org.junit.Test;
import org.swetlokognatsk.earking_out.core.domain.model.music.NoteNames;
import org.swetlokognatsk.earking_out.core.domain.model.music.NoteWithAccidental;
import org.swetlokognatsk.earking_out.core.domain.model.music.Octaves;
import org.swetlokognatsk.earking_out.core.ports.DI;
import org.swetlokognatsk.earking_out.core.ports.music.INoteNormalizer;

public class NoteWithAccidentalTest {
    @Test
    public void testGetNoteNormalizer() {
        var noteNormalizer = getNoteNormalizer();
        assertNotNull(getNoteNormalizer());
        assertTrue(noteNormalizer instanceof INoteNormalizer);
    }
    
    @Test
    public void normalizeTest() {
        var noteNormalizer = getNoteNormalizer();
        var notesWithAccidental = new NoteWithAccidental[] {
            new NoteWithAccidental(NoteNames.C, null, Octaves.FIRST),
            new NoteWithAccidental(NoteNames.D, null, Octaves.FIRST),
            new NoteWithAccidental(NoteNames.E, null, Octaves.FIRST),
            new NoteWithAccidental(NoteNames.F, null, Octaves.FIRST),
            new NoteWithAccidental(NoteNames.G, null, Octaves.FIRST),
            new NoteWithAccidental(NoteNames.A, null, Octaves.FIRST),
            new NoteWithAccidental(NoteNames.B, null, Octaves.FIRST),
        };

        var normalizedValues = new int[]{
            4,
            6,
            8,
            9,
            11,
            13
        };
        for (int i = 0; i < notesWithAccidental.length; i++) {
            var normalizedValue = noteNormalizer.normalize(notesWithAccidental[i]);
            assertEquals(normalizedValues[i], normalizedValue);
        }
    }

    @Test
    public void normalizeInOctaveTest() {
        here i go
    }

    INoteNormalizer getNoteNormalizer() {
        return DI.get(INoteNormalizer.class);
    }
}

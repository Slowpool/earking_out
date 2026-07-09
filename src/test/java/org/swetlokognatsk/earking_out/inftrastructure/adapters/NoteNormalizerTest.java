package org.swetlokognatsk.earking_out.inftrastructure.adapters;

import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;
import org.swetlokognatsk.earking_out.core.domain.model.music.Accidentals;
import org.swetlokognatsk.earking_out.core.domain.model.music.Constants;
import org.swetlokognatsk.earking_out.core.domain.model.music.NoteTest;
import org.swetlokognatsk.earking_out.core.domain.model.music.sounds.Note;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;
import org.swetlokognatsk.earking_out.core.ports.DI;
import org.swetlokognatsk.earking_out.core.ports.music.NoteNormalizer;

public final class NoteNormalizerTest {
    static NoteNormalizer noteNormalizer;
    static Note[] notes = NoteTest.notes;
    static PianoKeyNumber[] normalizedValues = NoteTest.normalizedValues;

    @BeforeClass
    public static void setup() {
        noteNormalizer = getNoteNormalizer();
    }

    static NoteNormalizer getNoteNormalizer() {
        return DI.get(NoteNormalizer.class);
    }

    @Test
    public void gettingNoteNormalizer() {
        assertNotNull(getNoteNormalizer());
        assertTrue(noteNormalizer instanceof NoteNormalizer);
    }

    @Test
    public void normalizingInOctave() {
        byte expected;
        for (int i = 0; i < notes.length; i++) {
            var normalizedValue = noteNormalizer.normalizeInOctave(notes[i]);
            // normalizedValues are defined for FIRST octave, whereas this test checks for octave-scoped value
            expected = normalizedValues[i].value;
            expected -= Constants.SHIFT;
            assertEquals(expected, normalizedValue);
        }
    }

    // TODO implementation tests further. isn't it awkward?
    @Test
    public void gettingAccidentalShiftSharp() {
        var accidentalShift = Accidentals.getShift(Accidentals.SHARP);
        assertEquals(1, accidentalShift);
    }

    @Test
    public void gettingAccidentalShiftNatural() {
        var accidentalShift = Accidentals.getShift(Accidentals.NATURAL);
        assertEquals(0, accidentalShift);
    }

    @Test
    public void gettingAccidentalShiftFlat() {
        var accidentalShift = Accidentals.getShift(Accidentals.FLAT);
        assertEquals(-1, accidentalShift);
    }
}

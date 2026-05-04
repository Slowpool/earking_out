package org.swetlokognatsk.earking_out.core.domain.model;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.swetlokognatsk.earking_out.core.domain.model.music.Accidentals;
import org.swetlokognatsk.earking_out.core.domain.model.music.NoteNames;
import org.swetlokognatsk.earking_out.core.domain.model.music.NoteWithAccidental;
import org.swetlokognatsk.earking_out.core.domain.model.music.Octaves;
import org.swetlokognatsk.earking_out.core.ports.DI;
import org.swetlokognatsk.earking_out.core.ports.music.INoteNormalizer;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.NoteNormalizer;

public class NoteWithAccidentalTest {
    static NoteWithAccidental[] notesWithAccidental;
    static byte[] normalizedValues;
    static INoteNormalizer noteNormalizer;

    @BeforeClass
    public static void setup() {
        // TODO fix auto-formatting
        notesWithAccidental = new NoteWithAccidental[] { new NoteWithAccidental(NoteNames.C, null, Octaves.FIRST), new NoteWithAccidental(NoteNames.D, null, Octaves.FIRST), new NoteWithAccidental(NoteNames.E, null, Octaves.FIRST), new NoteWithAccidental(NoteNames.F, null, Octaves.FIRST), new NoteWithAccidental(NoteNames.G, null, Octaves.FIRST), new NoteWithAccidental(NoteNames.A, null, Octaves.FIRST), new NoteWithAccidental(NoteNames.B, null, Octaves.FIRST) };

        normalizedValues = new byte[] { 4, // C1
                6, // D1
                8, // E1
                9, // F1
                11, // G1
                13, // A1
                15 // B1
        };
        noteNormalizer = getNoteNormalizer();
    }

    static INoteNormalizer getNoteNormalizer() {
        return DI.get(INoteNormalizer.class);
    }

    @Test
    public void gettingNoteNormalizer() {
        assertNotNull(getNoteNormalizer());
        assertTrue(noteNormalizer instanceof INoteNormalizer);
    }

    @Test
    public void normalizingInOctave() {
        byte expected;
        for (int i = 0; i < notesWithAccidental.length; i++) {
            var normalizedValue = noteNormalizer.normalizeInOctave(notesWithAccidental[i]);
            // normalizedValues are defined for FIRST octave, whereas this test checks for octave-scoped value
            expected = (byte) (normalizedValues[i] - INoteNormalizer.SHIFT);
            assertEquals(expected, normalizedValue);
        }
    }

    // TODO checks normalizing for first and second octave. i feel mathematically that's sufficient (induction), but proofs are welcomed
    @Test
    public void normalizingFirstAndSecondOctave() {
        var octaves = new Octaves[] { Octaves.FIRST, Octaves.SECOND };
        var octaveShifts = new byte[] { 0, 12 };

        NoteWithAccidental noteWithAccidental;
        byte normalizedValue;
        byte correctNormalizedValue;
        for (int octave = 0; octave < octaves.length; octave++) {
            for (int i = 0; i < notesWithAccidental.length; i++) {
                noteWithAccidental = notesWithAccidental[i].withOctave(octaves[octave]);
                normalizedValue = noteNormalizer.normalize(noteWithAccidental);
                correctNormalizedValue = (byte)(normalizedValues[i] + octaveShifts[octave]);
                assertEquals(correctNormalizedValue, normalizedValue);
            }
        }
    }

    @Test
    public void normalizingWithAccidentals() {

    }

    @Test
    public void normalizingWithAccidentalsOverOctaves() {

    }

    // TODO implementation tests further. isn't it awkward?
    @Test
    public void gettingAccidentalShiftSharp() {
        var accidentalShift = NoteNormalizer.getAccidentalShift(Accidentals.SHARP);
        assertEquals(1, accidentalShift);
    }

    @Test
    public void gettingAccidentalShiftNatural() {
        var accidentalShift = NoteNormalizer.getAccidentalShift(Accidentals.NATURAL);
        assertEquals(0, accidentalShift);
    }

    @Test
    public void gettingAccidentalShiftFlat() {
        var accidentalShift = NoteNormalizer.getAccidentalShift(Accidentals.FLAT);
        assertEquals(-1, accidentalShift);
    }
}

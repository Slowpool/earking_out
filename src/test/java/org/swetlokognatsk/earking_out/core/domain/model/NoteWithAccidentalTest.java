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

public class NoteWithAccidentalTest {
    public static NoteWithAccidental[] notesWithAccidental;
    public static byte[] normalizedValues;

    static {
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
    }

    // TODO checks normalizing for first and second octave. i feel mathematically that's sufficient (induction), but proofs are welcomed
    @Test
    public void normalizingFirstAndSecondOctave() {
        var octaves = new Octaves[] { Octaves.FIRST, Octaves.SECOND };
        var octaveShifts = new byte[] { 0, 12 };
        for (int octave = 0; octave < octaves.length; octave++) {
            checkOctaveNormalizing(octaves[octave], octaveShifts[octave], null, (byte) 0);
        }
    }

    @Test
    public void normalizingWithAccidentals() {
        var accidentals = new Accidentals[] { Accidentals.SHARP, Accidentals.FLAT, Accidentals.NATURAL };
        // TODO pretty sure more elegant way exists
        var accidentalShifts = new byte[] { 1, -1, 0 };

        for (int accidentalNumber = 0; accidentalNumber < accidentals.length; accidentalNumber++) {
            checkOctaveNormalizing(null, (byte) 0, accidentals[accidentalNumber], accidentalShifts[accidentalNumber]);
        }
    }

    private void checkOctaveNormalizing(Octaves octave, byte octaveShift, Accidentals accidental, byte accidentalShift) {
        NoteWithAccidental noteWithAccidental;
        byte correctNormalizedValue;
        for (int i = 0; i < notesWithAccidental.length; i++) {
            noteWithAccidental = applyOctaveAndAccidentals(notesWithAccidental[i], octave, accidental);
            correctNormalizedValue = adjustCorrectNoteValue(normalizedValues[i], octaveShift, accidentalShift);
            checkNoteNormalizing(noteWithAccidental, correctNormalizedValue);
        }
    }

    private NoteWithAccidental applyOctaveAndAccidentals(NoteWithAccidental noteWithAccidental, Octaves octave, Accidentals accidental) {
        if (octave != null) {
            noteWithAccidental = noteWithAccidental.withOctave(octave);
        }
        if (accidental != null) {
            noteWithAccidental = noteWithAccidental.withAccidental(accidental);
        }
        return noteWithAccidental;
    }
       
    private byte adjustCorrectNoteValue(byte correctNormalizedValue, byte octaveShift, byte accidentalShift) {
        correctNormalizedValue += octaveShift;
        correctNormalizedValue += accidentalShift;
        return correctNormalizedValue;
    }

    private void checkNoteNormalizing(NoteWithAccidental noteWithAccidental, byte correctNormalizedValue) {
        var normalizedValue = noteWithAccidental.normalize();
        assertEquals(correctNormalizedValue, normalizedValue);
    }

    @Test
    public void normalizingWithAccidentalsOverOctaves() {

    }
}

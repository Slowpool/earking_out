package org.swetlokognatsk.earking_out.core.domain.model;

import static org.junit.Assert.*;
import org.junit.Test;
import org.swetlokognatsk.earking_out.core.domain.model.music.Accidentals;
import org.swetlokognatsk.earking_out.core.domain.model.music.NoteNames;
import org.swetlokognatsk.earking_out.core.domain.model.music.NoteWithAccidental;
import org.swetlokognatsk.earking_out.core.domain.model.music.Octaves;

public class NoteWithAccidentalTest {
    public static final NoteWithAccidental[] notesWithAccidental;
    public static final byte[] normalizedValues;

    static final Accidentals[] accidentals;
    static final byte[] accidentalShifts;

    static final Octaves[] octaves;
    static final byte[] octaveShifts;

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

        // TODO pretty sure more elegant way exists
        accidentals = new Accidentals[] { Accidentals.SHARP, Accidentals.FLAT, Accidentals.NATURAL };
        accidentalShifts = new byte[] { 1, -1, 0 };

        // TODO pretty sure more elegant way exists
        octaves = new Octaves[] { Octaves.FIRST, Octaves.SECOND };
        octaveShifts = new byte[] { 0, 12 };
    }

    // TODO checks normalizing for first and second octave. i feel mathematically that's sufficient (induction), but proofs are welcomed
    @Test
    public void normalizingFirstAndSecondOctave() {
        for (int octave = 0; octave < octaves.length; octave++) {
            checkOctaveNormalizing(octaves[octave], octaveShifts[octave], null, (byte) 0);
        }
    }

    @Test
    public void normalizingWithAccidentals() {
        for (int accidentalNumber = 0; accidentalNumber < accidentals.length; accidentalNumber++) {
            checkOctaveNormalizing(null, (byte) 0, accidentals[accidentalNumber], accidentalShifts[accidentalNumber]);
        }
    }

    @Test
    public void normalizingWithAccidentalsOverOctaves() {
        for (int octave = 0; octave < octaves.length; octave++) {
            for (int accidentalNumber = 0; accidentalNumber < accidentals.length; accidentalNumber++) {
                checkOctaveNormalizing(octaves[octave], octaveShifts[octave], accidentals[accidentalNumber], accidentalShifts[accidentalNumber]);
            }
        }
    }

    @Test
    public void humanReadableNormalizingTest1() {
        var octave = Octaves.FIRST;

        var E = new NoteWithAccidental(NoteNames.E, Accidentals.NATURAL, octave);
        var normalizedE = E.normalize();

        var FFlat = new NoteWithAccidental(NoteNames.F, Accidentals.FLAT, octave);
        var normalizedFFlat = FFlat.normalize();

        assertEquals(normalizedE, normalizedFFlat);
    }

    @Test
    public void humanReadableNormalizingTest2() {
        var octave = Octaves.EIGHTH;

        var CSharp = new NoteWithAccidental(NoteNames.C, Accidentals.SHARP, octave);
        var normalizedCSharp = CSharp.normalize();

        var DFlat = new NoteWithAccidental(NoteNames.D, Accidentals.FLAT, octave);
        var normalizedDFlat = DFlat.normalize();

        assertEquals(normalizedCSharp, normalizedDFlat);
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
}

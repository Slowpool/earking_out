package org.swetlokognatsk.earking_out.core.domain.model.music;

import static org.junit.Assert.*;
import static org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber.*;
import java.util.LinkedList;
import org.junit.Test;
import org.swetlokognatsk.earking_out.core.domain.model.music.Accidentals;
import org.swetlokognatsk.earking_out.core.domain.model.music.NoteNames;
import org.swetlokognatsk.earking_out.core.domain.model.music.Octaves;
import org.swetlokognatsk.earking_out.core.domain.model.music.sounds.Note;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;
import org.swetlokognatsk.earking_out.core.domain.services.domain.music.NotesNormalizingService;
import org.swetlokognatsk.earking_out.core.ports.di.DI;

public final class NoteTest {
    public static final Note[] notes;
    public static final PianoKeyNumber[] normalizedValues;

    static final Accidentals[] accidentals;
    static final byte[] accidentalShifts;

    static final Octaves[] octaves;
    static final byte[] octaveShifts;

    static {
        // TODO fix auto-formatting
        notes = new Note[] { Note.valueOf(NoteNames.C, null, Octaves.FIRST), Note.valueOf(NoteNames.D, null, Octaves.FIRST), Note.valueOf(NoteNames.E, null, Octaves.FIRST), Note.valueOf(NoteNames.F, null, Octaves.FIRST), Note.valueOf(NoteNames.G, null, Octaves.FIRST), Note.valueOf(NoteNames.A, null, Octaves.FIRST), Note.valueOf(NoteNames.B, null, Octaves.FIRST) };

        normalizedValues = new PianoKeyNumber[] { PianoKeyNumber.valueOf(4), // C1
                PianoKeyNumber.valueOf(6), // D1
                PianoKeyNumber.valueOf(8), // E1
                PianoKeyNumber.valueOf(9), // F1
                PianoKeyNumber.valueOf(11), // G1
                PianoKeyNumber.valueOf(13), // A1
                PianoKeyNumber.valueOf(15) // B1
        };

        // TODO pretty sure more elegant way exists
        accidentals = new Accidentals[] { Accidentals.SHARP, Accidentals.FLAT, Accidentals.NATURAL };
        accidentalShifts = new byte[] { 1, -1, 0 };

        // TODO pretty sure more elegant way exists
        octaves = new Octaves[] { Octaves.FIRST, Octaves.SECOND };
        octaveShifts = new byte[] { 0, 12 };
    }

    private void checkOctaveNormalizing(final Octaves octave, final byte octaveShift, final Accidentals accidental, final byte accidentalShift) {
        Note note;
        PianoKeyNumber correctNormalizedValue;
        for (int i = 0; i < notes.length; i++) {
            note = applyOctaveAndAccidentals(notes[i], octave, accidental);
            if (thisNoteExistsButIsNotUsedInApp(normalizedValues[i], accidentalShift)) {
                continue;
            }
            correctNormalizedValue = adjustCorrectNoteValue(normalizedValues[i], octaveShift, accidentalShift);
            assertNoteNormalizing(note, correctNormalizedValue);
        }
    }

    /**
     * Tests traverse all combinations of all notes/accidentals/octaves used in app,
     * though there's edge cases like C1b - the combination of
     * note/accidental/octave is permissible, though normalized value of this note
     * is 3 - there's no PianoKeyNumber in app with such a note. btw from the point
     * of view of domain, note 3 exists.
     */
    private boolean thisNoteExistsButIsNotUsedInApp(final PianoKeyNumber keyNumber, final byte accidentalShift) {
        if (keyNumber.equals(FIRST_NOTE_NUMBER) && accidentalShift == Accidentals.FLAT.shift) {
            return true;
        } else if (keyNumber.equals(LAST_NOTE_NUMBER) && accidentalShift == Accidentals.SHARP.shift) {
            return true;
        }
        return false;
    }

    private Note applyOctaveAndAccidentals(Note note, Octaves octave, Accidentals accidental) {
        if (octave != null) {
            note = note.withOctave(octave);
        }
        if (accidental != null) {
            note = note.withAccidental(accidental);
        }
        return note;
    }

    private PianoKeyNumber adjustCorrectNoteValue(PianoKeyNumber correctNormalizedValue, final byte octaveShift, final byte accidentalShift) {
        correctNormalizedValue = correctNormalizedValue.add(octaveShift);
        correctNormalizedValue = correctNormalizedValue.add(accidentalShift);
        return correctNormalizedValue;
    }

    private void assertNoteNormalizing(final Note note, final PianoKeyNumber expectedtNormalizedValue) {
        var normalizedValue = note.normalize();
        assertEquals(expectedtNormalizedValue, normalizedValue);
    }

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

        var E = Note.valueOf(NoteNames.E, Accidentals.NATURAL, octave);
        var normalizedE = E.normalize();

        var FFlat = Note.valueOf(NoteNames.F, Accidentals.FLAT, octave);
        var normalizedFFlat = FFlat.normalize();

        assertEquals(normalizedE, normalizedFFlat);
    }

    @Test
    public void humanReadableNormalizingTest2() {
        var octave = Octaves.SEVENTH;

        var CSharp = Note.valueOf(NoteNames.C, Accidentals.SHARP, octave);
        var normalizedCSharp = CSharp.normalize();

        var DFlat = Note.valueOf(NoteNames.D, Accidentals.FLAT, octave);
        var normalizedDFlat = DFlat.normalize();

        assertEquals(normalizedCSharp, normalizedDFlat);
    }

    @Test
    @Deprecated
    public void attachedPianoKeyNumber() {
        var allNotes = new LinkedList<Note>();

        for (var noteName : NoteNames.values()) {
            for (var accidental : Accidentals.values()) {
                for (var octave : Octaves.values()) {
                    if (isEdgeCaseNote(noteName, accidental, octave)) {
                        continue;
                    }
                    var note = Note.valueOf(noteName, accidental, octave);
                    allNotes.add(note);
                }
            }
        }

        var normalizingService = DI.get(NotesNormalizingService.class);
        for (var note : allNotes) {
            var expectedNormalizedValue = normalizingService.normalize(note);
        }
    }

    @Deprecated
    private boolean isEdgeCaseNote(final NoteNames noteName, final Accidentals accidental, final Octaves octave) {
        return noteName == NoteNames.C
                && accidental == Accidentals.FLAT
                && octave == Octaves.FIRST
                ||
                noteName == NoteNames.B
                        && accidental == Accidentals.SHARP
                        && octave == Octaves.SEVENTH;
    }

}

package org.swetlokognatsk.earking_out.infrastructure.adapters;

import org.apache.commons.lang3.function.TriConsumer;
import org.junit.*;
import static org.junit.Assert.*;
import org.swetlokognatsk.earking_out.core.domain.model.music.Accidentals;
import org.swetlokognatsk.earking_out.core.domain.model.music.NoteNames;
import org.swetlokognatsk.earking_out.core.domain.model.music.Octaves;
import org.swetlokognatsk.earking_out.core.domain.model.music.sounds.Note;
import org.swetlokognatsk.earking_out.core.domain.model.session.exceptions.InvalidTextNoteException;
import org.swetlokognatsk.earking_out.core.domain.model.session.exceptions.OutOfRangeTextNoteException;
import org.swetlokognatsk.earking_out.core.domain.services.domain.music.NotesParsingService;
import org.swetlokognatsk.earking_out.core.ports.di.DI;
import static org.swetlokognatsk.earking_out.core.domain.model.music.NoteNames.*;
import static org.swetlokognatsk.earking_out.core.domain.model.music.Accidentals.*;
import static org.swetlokognatsk.earking_out.core.domain.model.music.Octaves.*;
import java.util.HashMap;
import java.util.Map;

public class NotesParsingServiceTest {

    private static final NotesParsingService noteParser = DI.get(NotesParsingService.class);

    // TODO think about this arrays mess
    private static final String[] CORRECT_TEXT_NOTES = {
            "C1", "C#1", "Db1", "D1", "D#1", "Eb1", "E1", "E#1", "F1", "F#1", "Gb1", "G1", "G#1", "Ab1", "A1", "A#1", "Bb1", "B1", "B#1",
            "C2", "C#2", "Db2", "D2", "D#2", "Eb2", "E2", "E#2", "F2", "F#2", "Gb2", "G2", "G#2", "Ab2", "A2", "A#2", "Bb2", "B2", "B#2",
            "C3", "C#3", "Db3", "D3", "D#3", "Eb3", "E3", "E#3", "F3", "F#3", "Gb3", "G3", "G#3", "Ab3", "A3", "A#3", "Bb3", "B3", "B#3",
            "C4", "C#4", "Db4", "D4", "D#4", "Eb4", "E4", "E#4", "F4", "F#4", "Gb4", "G4", "G#4", "Ab4", "A4", "A#4", "Bb4", "B4", "B#4",
            "C5", "C#5", "Db5", "D5", "D#5", "Eb5", "E5", "E#5", "F5", "F#5", "Gb5", "G5", "G#5", "Ab5", "A5", "A#5", "Bb5", "B5", "B#5",
            "C6", "C#6", "Db6", "D6", "D#6", "Eb6", "E6", "E#6", "F6", "F#6", "Gb6", "G6", "G#6", "Ab6", "A6", "A#6", "Bb6", "B6", "B#6",
            "C7", "C#7", "Db7", "D7", "D#7", "Eb7", "E7", "E#7", "F7", "F#7", "Gb7", "G7", "G#7", "Ab7", "A7", "A#7", "Bb7", "B7" };
    @Deprecated
    private static final int[] CORRECT_TEXT_NOTES_NUMBERS = { 4, 5, 5, 6, 7, 7, 8, 9, 9, 10, 10, 11, 12, 12, 13, 14, 14, 15, 16, 16, 17, 17, 18, 19, 19, 20, 21, 21, 22, 22, 23, 24, 24, 25, 26, 26, 27, 28, 28, 29, 29, 30, 31, 31, 32, 33, 33, 34, 34, 35, 36, 36, 37, 38, 38, 39, 40, 40, 41, 41, 42, 43, 43, 44, 45, 45, 46, 46, 47, 48, 48, 49, 50, 50, 51, 52, 52, 53, 53, 54, 55, 55, 56, 57, 57, 58, 58, 59, 60, 60, 61, 62, 62, 63, 64, 64, 65, 65, 66, 67, 67, 68, 69, 69, 70, 70, 71, 72, 72, 73, 74, 74, 75, 76, 76, 77, 77, 78, 79, 79, 80, 81, 81, 82, 82, 83, 84, 84, 85, 86, 86, 87 };
    private static final NoteNames[] EXPECTED_TEXT_NOTES_NOTE_NAMES = { C, C, D, D, D, E, E, E, F, F, G, G, G, A, A, A, B, B, B, C, C, D, D, D, E, E, E, F, F, G, G, G, A, A, A, B, B, B, C, C, D, D, D, E, E, E, F, F, G, G, G, A, A, A, B, B, B, C, C, D, D, D, E, E, E, F, F, G, G, G, A, A, A, B, B, B, C, C, D, D, D, E, E, E, F, F, G, G, G, A, A, A, B, B, B, C, C, D, D, D, E, E, E, F, F, G, G, G, A, A, A, B, B, B, C, C, D, D, D, E, E, E, F, F, G, G, G, A, A, A, B, B };
    private static final Accidentals[] EXPECTED_TEXT_NOTES_ACCIDENTALS = { NATURAL, SHARP, FLAT, NATURAL, SHARP, FLAT, NATURAL, SHARP, NATURAL, SHARP, FLAT, NATURAL, SHARP, FLAT, NATURAL, SHARP, FLAT, NATURAL, SHARP, NATURAL, SHARP, FLAT, NATURAL, SHARP, FLAT, NATURAL, SHARP, NATURAL, SHARP, FLAT, NATURAL, SHARP, FLAT, NATURAL, SHARP, FLAT, NATURAL, SHARP, NATURAL, SHARP, FLAT, NATURAL, SHARP, FLAT, NATURAL, SHARP, NATURAL, SHARP, FLAT, NATURAL, SHARP, FLAT, NATURAL, SHARP, FLAT, NATURAL, SHARP, NATURAL, SHARP, FLAT, NATURAL, SHARP, FLAT, NATURAL, SHARP, NATURAL, SHARP, FLAT, NATURAL, SHARP, FLAT, NATURAL, SHARP, FLAT, NATURAL, SHARP, NATURAL, SHARP, FLAT, NATURAL, SHARP, FLAT, NATURAL, SHARP, NATURAL, SHARP, FLAT, NATURAL, SHARP, FLAT, NATURAL, SHARP, FLAT, NATURAL, SHARP, NATURAL, SHARP, FLAT, NATURAL, SHARP, FLAT, NATURAL, SHARP, NATURAL, SHARP, FLAT, NATURAL, SHARP, FLAT, NATURAL, SHARP, FLAT, NATURAL, SHARP, NATURAL, SHARP, FLAT, NATURAL, SHARP, FLAT, NATURAL, SHARP, NATURAL, SHARP, FLAT, NATURAL, SHARP, FLAT, NATURAL, SHARP, FLAT, NATURAL };
    private static final Octaves[] EXPECTED_TEXT_NOTES_OCTAVES = { FIRST, FIRST, FIRST, FIRST, FIRST, FIRST, FIRST, FIRST, FIRST, FIRST, FIRST, FIRST, FIRST, FIRST, FIRST, FIRST, FIRST, FIRST, FIRST, SECOND, SECOND, SECOND, SECOND, SECOND, SECOND, SECOND, SECOND, SECOND, SECOND, SECOND, SECOND, SECOND, SECOND, SECOND, SECOND, SECOND, SECOND, SECOND, THIRD, THIRD, THIRD, THIRD, THIRD, THIRD, THIRD, THIRD, THIRD, THIRD, THIRD, THIRD, THIRD, THIRD, THIRD, THIRD, THIRD, THIRD, THIRD, FOURTH, FOURTH, FOURTH, FOURTH, FOURTH, FOURTH, FOURTH, FOURTH, FOURTH, FOURTH, FOURTH, FOURTH, FOURTH, FOURTH, FOURTH, FOURTH, FOURTH, FOURTH, FOURTH, FIFTH, FIFTH, FIFTH, FIFTH, FIFTH, FIFTH, FIFTH, FIFTH, FIFTH, FIFTH, FIFTH, FIFTH, FIFTH, FIFTH, FIFTH, FIFTH, FIFTH, FIFTH, FIFTH, SIXTH, SIXTH, SIXTH, SIXTH, SIXTH, SIXTH, SIXTH, SIXTH, SIXTH, SIXTH, SIXTH, SIXTH, SIXTH, SIXTH, SIXTH, SIXTH, SIXTH, SIXTH, SIXTH, SEVENTH, SEVENTH, SEVENTH, SEVENTH, SEVENTH, SEVENTH, SEVENTH, SEVENTH, SEVENTH, SEVENTH, SEVENTH, SEVENTH, SEVENTH, SEVENTH, SEVENTH, SEVENTH, SEVENTH, SEVENTH };

    private static final Map<String, Class<? extends Exception>> INVALID_TEXT_NOTES;

    static {
        INVALID_TEXT_NOTES = new HashMap<>();
        var itn = INVALID_TEXT_NOTES;
        itn.put("C", InvalidTextNoteException.class);
        itn.put("X", InvalidTextNoteException.class);
        itn.put("1", InvalidTextNoteException.class);
        itn.put("#", InvalidTextNoteException.class);
        itn.put("C##", InvalidTextNoteException.class);
        itn.put("C#b", InvalidTextNoteException.class);
        itn.put("#bC1", InvalidTextNoteException.class);
        itn.put("C#9", OutOfRangeTextNoteException.class);
        itn.put("Cb9", OutOfRangeTextNoteException.class);
        itn.put("Bb9", OutOfRangeTextNoteException.class);
        itn.put("C0", OutOfRangeTextNoteException.class);
    }

    private Note parse(final String textNote) throws InvalidTextNoteException, OutOfRangeTextNoteException {
        return noteParser.parse(textNote);
    }

    private Note parseWithoutException(final String textNote) {
        try {
            return parse(textNote);
        } catch (InvalidTextNoteException | OutOfRangeTextNoteException e) {
            fail();
            // logically unreachable code
            return null;
        }
    }

    private void parseEachNoteAndAssert(final TriConsumer<String, Note, Integer> assertt) {
        String textNote;
        Note parsedNote;
        for (int i = 0; i < CORRECT_TEXT_NOTES.length; i++) {
            textNote = CORRECT_TEXT_NOTES[i];

            parsedNote = parseWithoutException(textNote);

            assertt.accept(textNote, parsedNote, i);
        }
    }

    @Test
    public void noteNamesAreParsedCorrectly() {
        parseEachNoteAndAssert((String textNote, Note parsedNote, Integer i) -> {
            var expectedNoteName = EXPECTED_TEXT_NOTES_NOTE_NAMES[i];
            assertEquals(expectedNoteName, parsedNote.noteName());
        });
    }

    @Test
    public void accidentalsAreParsedCorrectly() {
        parseEachNoteAndAssert((String textNote, Note parsedNote, Integer i) -> {
            var expectedAccidental = EXPECTED_TEXT_NOTES_ACCIDENTALS[i];
            assertEquals(expectedAccidental, parsedNote.accidental());
        });
    }

    @Test
    public void octavesAreParsedCorrectly() {
        parseEachNoteAndAssert((String textNote, Note parsedNote, Integer i) -> {
            var expectedOctave = EXPECTED_TEXT_NOTES_OCTAVES[i];
            assertEquals(expectedOctave, parsedNote.octave());
        });
    }

    @Test
    public void traverseInvalidTextNotes() {
        Class<? extends Exception> expectedExceptionClass;
        for (String invalidTextNote : INVALID_TEXT_NOTES.keySet()) {
            try {
                parse(invalidTextNote);
                fail();
            } catch (InvalidTextNoteException | OutOfRangeTextNoteException e) {
                expectedExceptionClass = INVALID_TEXT_NOTES.get(invalidTextNote);
                if (!e.getClass().equals(expectedExceptionClass)) {
                    fail();
                }
            }
        }
    }
}

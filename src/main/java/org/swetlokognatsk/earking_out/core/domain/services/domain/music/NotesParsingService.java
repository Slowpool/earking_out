package org.swetlokognatsk.earking_out.core.domain.services.domain.music;

import static org.swetlokognatsk.earking_out.core.domain.model.music.Octaves.*;
import org.swetlokognatsk.earking_out.core.domain.model.music.Accidentals;
import org.swetlokognatsk.earking_out.core.domain.model.music.NoteNames;
import org.swetlokognatsk.earking_out.core.domain.model.music.Octaves;
import org.swetlokognatsk.earking_out.core.domain.model.music.sounds.Note;
import org.swetlokognatsk.earking_out.core.domain.model.session.exceptions.InvalidTextNoteException;
import org.swetlokognatsk.earking_out.core.domain.model.session.exceptions.OutOfRangeOctaveException;
import org.swetlokognatsk.earking_out.core.domain.model.session.exceptions.OutOfRangeTextNoteException;

public final class NotesParsingService {

    private static final String SHARP = "#";
    private static final String FLAT = "b";

    public Note parse(final String textNote) throws InvalidTextNoteException, OutOfRangeTextNoteException {
        if (textNote.length() < 2 || textNote.length() > 3) {
            throw new InvalidTextNoteException(textNote);
        }

        var textNoteName = charAtAsString(textNote, 0);
        var noteName = NoteNames.valueOf(textNoteName);

        var accidental = parseAccidental(textNote);
        if (accidental == null) {
            throw new InvalidTextNoteException(textNote);
        }

        Octaves octave;
        try {
            octave = parseOctave(textNote, giveAccidentalExistenceHint(accidental));
        }
        catch (OutOfRangeOctaveException e) {
            throw new OutOfRangeTextNoteException(textNoteName);
        }

        if (octave == null) {
            throw new InvalidTextNoteException(textNote);
        }

        return new Note(noteName, accidental, octave);
    }

    private Accidentals parseAccidental(final String textNote) throws InvalidTextNoteException {
        var textAccidental = charAtAsString(textNote, 1);
        // TODO add Accidentals.valueOfOrNull()
        Accidentals accidental;
        try {
            accidental = switch (textAccidental) {
            case SHARP -> Accidentals.SHARP;
            case FLAT -> Accidentals.FLAT;
            default -> throw new IllegalArgumentException();
            };
        } catch (IllegalArgumentException e) {
            var accidentalChar = textAccidental.charAt(0);
            // accidental does not care what's going further, there must be at least one digit, further - does not matter. e.g. C111111 note won't throw an exception inside `parseAccidental()` method
            if (Character.isDigit(accidentalChar)) {
                accidental = Accidentals.NATURAL;
            } else {
                accidental = null;
            }
        }
        return accidental;
    }

    private boolean giveAccidentalExistenceHint(final Accidentals accidental) {
        return accidental == Accidentals.SHARP || accidental == Accidentals.FLAT;
    }

    private Octaves parseOctave(final String textNote, final boolean hasAccidentalChar) throws OutOfRangeOctaveException {
        // 01
        // C1
        // _^
        var expectedOctavePos = 1;
        if (hasAccidentalChar) {
            //  01    012
            // C#1 => C#1
            // _^     __^
            expectedOctavePos++;
        }
        var octaveChar = charAtAsString(textNote, expectedOctavePos);
        if (!Character.isDigit(octaveChar.charAt(0))) {
            return null;
        }
        return switch (octaveChar) {
        case "0", "8", "9" -> throw new OutOfRangeOctaveException();
        case "1" -> FIRST;
        case "2" -> SECOND;
        case "3" -> THIRD;
        case "4" -> FOURTH;
        case "5" -> FIFTH;
        case "6" -> SIXTH;
        case "7" -> SEVENTH;
        default -> null;
        };
    }

    private static final String charAtAsString(final String text, final int pos) {
        return text.substring(pos, pos + 1);
    }
}

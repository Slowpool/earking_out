package org.swetlokognatsk.earking_out.inftrastructure.adapters;

import org.swetlokognatsk.earking_out.core.domain.model.music.Accidentals;
import org.swetlokognatsk.earking_out.core.domain.model.music.Invariants;
import org.swetlokognatsk.earking_out.core.domain.model.music.NoteNames;
import org.swetlokognatsk.earking_out.core.domain.model.music.NoteWithAccidental;
import org.swetlokognatsk.earking_out.core.domain.model.music.Octaves;
import org.swetlokognatsk.earking_out.core.ports.music.NoteNormalizer;

public class NoteNormalizerImpl implements NoteNormalizer {

    public byte normalize(NoteWithAccidental noteWithAccidental) {
        byte value = SHIFT;
        value += getOctavesShift(noteWithAccidental.octave());
        value += normalizeInOctave(noteWithAccidental);
        return value;
    }

    public byte normalizeInOctave(NoteWithAccidental noteWithAccidental) {
        byte octaveScopedNoteValue = normalizeNoteName(noteWithAccidental.noteName());
        byte accidentalShift = getAccidentalShift(noteWithAccidental.accidental());
        return (byte) (octaveScopedNoteValue + accidentalShift);
    }

    public static byte getAccidentalShift(Accidentals accidental) {
        return switch (accidental) {
        case SHARP -> 1;
        case NATURAL -> 0;
        case null -> 0;
        case FLAT -> -1;
        };
    }

    // TODO does better way exist?
    private static byte normalizeNoteName(NoteNames noteName) {
        return switch (noteName) {
        case C -> 1;
        case D -> 3;
        case E -> 5;
        case F -> 6;
        case G -> 8;
        case A -> 10;
        case B -> 12;
        };
    }

    private static byte getOctavesShift(Octaves octave) {
        int octaveNumber = getOctaveNumber(octave);
        return (byte) ((octaveNumber - 1) * Invariants.OCTAVE_SIZE);
    }

    // TODO encapsulate it into Octaves somehow?
    private static int getOctaveNumber(Octaves octave) {
        return switch (octave) {
        case FIRST -> 1;
        case SECOND -> 2;
        case THIRD -> 3;
        case FOURTH -> 4;
        case FIFTH -> 5;
        case SIXTH -> 6;
        case SEVENTH -> 7;
        case EIGHTH -> 8;
        default -> throw new RuntimeException("unknown octave");
        };
    }
}

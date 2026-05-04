package org.swetlokognatsk.earking_out.inftrastructure.adapters;

import org.swetlokognatsk.earking_out.core.domain.model.music.Accidentals;
import org.swetlokognatsk.earking_out.core.domain.model.music.Invariants;
import org.swetlokognatsk.earking_out.core.domain.model.music.NoteNames;
import org.swetlokognatsk.earking_out.core.domain.model.music.NoteWithAccidental;
import org.swetlokognatsk.earking_out.core.domain.model.music.Octaves;
import org.swetlokognatsk.earking_out.core.ports.music.INoteNormalizer;

public class NoteNormalizer implements INoteNormalizer {

    public byte normalize(NoteWithAccidental noteWithAccidental) {
        byte value = SHIFT; // 3 skipped notes on the left
        value += getOctavesShift(noteWithAccidental.octave());
        value += normalizeInOctave(noteWithAccidental);
        return value;
    }

    public byte normalizeInOctave(NoteWithAccidental noteWithAccidental) {
        byte octaveScopedNoteValue = normalizeNoteName(noteWithAccidental.noteName());
        byte accidentalShift = getAccidentalShift(noteWithAccidental.accidental());
        return (byte)(octaveScopedNoteValue + accidentalShift);
    }

    public static byte getAccidentalShift(Accidentals accidental) {
        // TODO how to switch (enum) in java
        // return switch(accidental.name()) {
        //     case Accidentals.SHARP.name() -> 1;
        // };
        if (accidental == Accidentals.SHARP) {
            return 1;
        }
        else if (accidental == Accidentals.NATURAL || accidental == null) {
            return 0;
        }
        else if (accidental == Accidentals.FLAT) {
            return -1;
        }
        else {
            throw new RuntimeException("unkown accidental value " + accidental.name());
        }
    }

    private static byte normalizeNoteName(NoteNames noteName) {
        return switch (noteName) {
            case NoteNames.C -> 1;
            case NoteNames.D -> 3;
            case NoteNames.E -> 5;
            case NoteNames.F -> 6;
            case NoteNames.G -> 8;
            case NoteNames.A -> 10;
            case NoteNames.B -> 12;
        };
    }

    private static byte getOctavesShift(Octaves octave) {
        int octaveNumber = getOctaveNumber(octave);
        return (byte)((octaveNumber - 1) * Invariants.OCTAVE_SIZE);
    }

    private static int getOctaveNumber(Octaves octave) {
        // TODO made in a hurry, refactoring
        if (octave == Octaves.FIRST) {
            return 1;
        }
        if (octave == Octaves.SECOND) {
            return 2;
        }
        if (octave == Octaves.THIRD) {
            return 3;
        }
        if (octave == Octaves.FOURTH) {
            return 4;
        }
        if (octave == Octaves.FIFTH) {
            return 5;
        }
        if (octave == Octaves.SIXTH) {
            return 6;
        }
        if (octave == Octaves.SEVENTH) {
            return 7;
        }
        if (octave == Octaves.EIGHTH) {
            return 8;
        }
        throw new RuntimeException("unknown octave");
    }
}

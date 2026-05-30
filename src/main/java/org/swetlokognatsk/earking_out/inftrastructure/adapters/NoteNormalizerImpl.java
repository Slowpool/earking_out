package org.swetlokognatsk.earking_out.inftrastructure.adapters;

import org.swetlokognatsk.earking_out.core.domain.model.music.Accidentals;
import org.swetlokognatsk.earking_out.core.domain.model.music.Invariants;
import org.swetlokognatsk.earking_out.core.domain.model.music.NoteWithAccidental;
import org.swetlokognatsk.earking_out.core.domain.model.music.Octaves;
import org.swetlokognatsk.earking_out.core.ports.music.NoteNormalizer;

// TODO it must be somewhere else cuz it's a core business logic, not technology-dependend service
public final class NoteNormalizerImpl implements NoteNormalizer {

    public byte normalize(NoteWithAccidental noteWithAccidental) {
        byte value = Invariants.SHIFT;
        value += getOctavesShift(noteWithAccidental.octave());
        value += normalizeInOctave(noteWithAccidental);
        return value;
    }

    public byte normalizeInOctave(NoteWithAccidental noteWithAccidental) {
        byte octaveScopedNoteValue = noteWithAccidental.noteName().keyNumber;
        byte accidentalShift = Accidentals.getShift(noteWithAccidental.accidental());
        return (byte) (octaveScopedNoteValue + accidentalShift);
    }

    private static byte getOctavesShift(Octaves octave) {
        int octaveNumber = octave.number;
        return (byte) ((octaveNumber - 1) * Invariants.KEYS_IN_OCTAVE);
    }
}

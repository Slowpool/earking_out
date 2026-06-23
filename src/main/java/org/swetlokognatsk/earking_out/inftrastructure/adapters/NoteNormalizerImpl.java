package org.swetlokognatsk.earking_out.inftrastructure.adapters;

import org.swetlokognatsk.earking_out.core.domain.model.music.Accidentals;
import static org.swetlokognatsk.earking_out.core.domain.model.music.Invariants.*;
import org.swetlokognatsk.earking_out.core.domain.model.music.NoteWithAccidental;
import org.swetlokognatsk.earking_out.core.domain.model.music.Octaves;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;
import org.swetlokognatsk.earking_out.core.ports.music.NoteNormalizer;

// TODO it must be somewhere else cuz it's a core business logic, not technology-dependend service
public final class NoteNormalizerImpl implements NoteNormalizer {

    public PianoKeyNumber normalize(final NoteWithAccidental noteWithAccidental) {
        // TODO theoretically overflow is possible below. refactoring via FIRST_NOTE_NUMBER
        byte value = SHIFT;
        value += getOctavesShift(noteWithAccidental.octave());
        value += normalizeInOctave(noteWithAccidental);
        return PianoKeyNumber.valueOf(value);
    }

    public byte normalizeInOctave(final NoteWithAccidental noteWithAccidental) {
        byte octaveScopedNoteValue = noteWithAccidental.noteName().octaveScopedKeyNumber;
        byte accidentalShift = Accidentals.getShift(noteWithAccidental.accidental());
        return (byte) (octaveScopedNoteValue + accidentalShift);
    }

    private static byte getOctavesShift(final Octaves octave) {
        int octaveNumber = octave.number;
        return (byte) ((octaveNumber - 1) * KEYS_IN_OCTAVE);
    }
}

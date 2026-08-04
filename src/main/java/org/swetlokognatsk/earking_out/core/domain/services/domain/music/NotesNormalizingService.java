package org.swetlokognatsk.earking_out.core.domain.services.domain.music;

import org.swetlokognatsk.earking_out.core.domain.model.music.Accidentals;
import static org.swetlokognatsk.earking_out.core.domain.model.music.Constants.*;
import org.swetlokognatsk.earking_out.core.domain.model.music.Octaves;
import org.swetlokognatsk.earking_out.core.domain.model.music.sounds.Note;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;

/**
 * Note normalizing - mapping the note to key number. For example, notes C#1
 * and Db1 actually have the same key number (and the sound accordingly) - that's
 * 5th key number
 */
public final class NotesNormalizingService {

    public PianoKeyNumber normalize(final Note note) {
        // TODO theoretically overflow is possible below. refactoring via FIRST_NOTE_NUMBER
        byte value = SHIFT;
        value += getOctavesShift(note.octave());
        value += normalizeInOctave(note);
        return PianoKeyNumber.valueOf(value);
    }

    /**
     * Normalizes the note, ignoring the octave of note - instead, it takes
     * Octave.FIRST as octave always.
     * 
     * @param note
     * @return
     */
    public byte normalizeInOctave(final Note note) {
        byte octaveScopedNoteValue = note.noteName().octaveScopedKeyNumber;
        byte accidentalShift = Accidentals.getShift(note.accidental());
        return (byte) (octaveScopedNoteValue + accidentalShift);
    }

    private static byte getOctavesShift(final Octaves octave) {
        int octaveNumber = octave.number;
        return (byte) ((octaveNumber - 1) * KEYS_IN_OCTAVE);
    }
}

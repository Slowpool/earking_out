package org.swetlokognatsk.earking_out.core.ports.music;

import org.swetlokognatsk.earking_out.core.domain.model.music.NoteWithAccidental;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;

/**
 * Note normalizing - mapping the note to key number. E.g. different notes C#1
 * and Db1 actually have the same key number (and sound accordingly) and it's
 * 5th, so both of their normalized values must be "5"
 */
public interface NoteNormalizer {
    PianoKeyNumber normalize(NoteWithAccidental noteWithAccidental);

    /**
     * Normalizes the note, ignoring the octave of note - instead, it takes
     * Octave.FIRST as octave always.
     * 
     * @param noteWithAccidental
     * @return
     */
    byte normalizeInOctave(NoteWithAccidental noteWithAccidental);
}

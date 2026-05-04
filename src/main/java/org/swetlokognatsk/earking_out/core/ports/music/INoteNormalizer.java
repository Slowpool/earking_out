package org.swetlokognatsk.earking_out.core.ports.music;

import org.swetlokognatsk.earking_out.core.domain.model.music.NoteWithAccidental;

public interface INoteNormalizer {
    byte normalize(NoteWithAccidental noteWithAccidental);
    /**
     * Normalizes the note, ignoring the octave of note - instead, it takes Octave.FIRST as octave always.
     * @param noteWithAccidental
     * @return
     */
    byte normalizeInOctave(NoteWithAccidental noteWithAccidental);
}

package org.swetlokognatsk.earking_out.core.ports.music;

import org.swetlokognatsk.earking_out.core.domain.model.music.NoteWithAccidental;

// TODO description
public interface INoteNormalizer {
    /**
     * Skipping A0, A#0, B0, so that `C1.normalize() == 4`. Why? These three notes aren't used in app by two reasons:
     * 1. To simplify the logic, cause i doubt anybody would train them
     * 2. To keep key numbers in "canonical" way. @see https://en.wikipedia.org/wiki/Piano_key_frequencies ctrl+f `Piano key number`
     * 
    */
    public static final int SHIFT = 3;
    
    byte normalize(NoteWithAccidental noteWithAccidental);
    /**
     * Normalizes the note, ignoring the octave of note - instead, it takes Octave.FIRST as octave always.
     * @param noteWithAccidental
     * @return
     */
    byte normalizeInOctave(NoteWithAccidental noteWithAccidental);
}

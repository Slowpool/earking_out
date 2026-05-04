package org.swetlokognatsk.earking_out.inftrastructure.adapters;

import org.swetlokognatsk.earking_out.core.domain.model.music.NoteNames;
import org.swetlokognatsk.earking_out.core.domain.model.music.NoteWithAccidental;
import org.swetlokognatsk.earking_out.core.ports.music.INoteNormalizer;

public class NoteNormalizer implements INoteNormalizer {
    // skipping A0, A#0, B0, so that `C1.normalize() == 4`
    final int SHIFT = 3;

    public byte normalize(NoteWithAccidental noteWithAccidental) {
        
        return SHIFT;
    }

    public byte normalizeInOctave(NoteWithAccidental noteWithAccidental) {
        byte octaveScopedNoteValue = normalizeNoteName(noteWithAccidental.noteName());
        byte Accidental
        return ;
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
        }
    }
}

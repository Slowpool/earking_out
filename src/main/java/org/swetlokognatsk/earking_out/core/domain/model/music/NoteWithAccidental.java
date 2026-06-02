package org.swetlokognatsk.earking_out.core.domain.model.music;

import org.swetlokognatsk.earking_out.core.ports.DI;
import org.swetlokognatsk.earking_out.core.ports.music.NoteNormalizer;

// TODO ValueObject
public record NoteWithAccidental(NoteNames noteName, Accidentals accidental, Octaves octave) {

    public byte normalize() {
        var noteNormalizer = DI.get(NoteNormalizer.class);
        return noteNormalizer.normalize(this);
    }

    public NoteWithAccidental withOctave(Octaves octave) {
        return new NoteWithAccidental(noteName, accidental, octave);
    }

    public NoteWithAccidental withAccidental(Accidentals accidental) {
        return new NoteWithAccidental(noteName, accidental, octave);
    }
}

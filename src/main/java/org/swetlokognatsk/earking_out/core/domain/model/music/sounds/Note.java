package org.swetlokognatsk.earking_out.core.domain.model.music.sounds;

import org.swetlokognatsk.earking_out.core.domain.model.music.Accidentals;
import org.swetlokognatsk.earking_out.core.domain.model.music.NoteNames;
import org.swetlokognatsk.earking_out.core.domain.model.music.Octaves;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;
import org.swetlokognatsk.earking_out.core.ports.DI;
import org.swetlokognatsk.earking_out.core.ports.music.NoteNormalizer;

// TODO ValueObject
public record Note(NoteNames noteName, Accidentals accidental, Octaves octave) {

    public PianoKeyNumber normalize() {
        var noteNormalizer = DI.get(NoteNormalizer.class);
        return noteNormalizer.normalize(this);
    }

    public Note withOctave(Octaves octave) {
        return new Note(noteName, accidental, octave);
    }

    public Note withAccidental(Accidentals accidental) {
        return new Note(noteName, accidental, octave);
    }
}

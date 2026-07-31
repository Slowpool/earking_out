package org.swetlokognatsk.earking_out.core.domain.model.music.sounds;

import java.io.Serializable;
import java.util.Objects;
import org.swetlokognatsk.earking_out.core.domain.model.music.Accidentals;
import org.swetlokognatsk.earking_out.core.domain.model.music.NoteNames;
import org.swetlokognatsk.earking_out.core.domain.model.music.Octaves;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;
import org.swetlokognatsk.earking_out.core.domain.services.domain.music.NotesNormalizingService;
import org.swetlokognatsk.earking_out.core.ports.di.DI;

public record Note(NoteNames noteName, Accidentals accidental, Octaves octave) implements Serializable {
    private static final long serialVersionUID = 1L;

    public Note {
        Objects.requireNonNull(noteName);
        Objects.requireNonNull(octave);
    }

    public PianoKeyNumber normalize() {
        var noteNormalizer = DI.get(NotesNormalizingService.class);
        return noteNormalizer.normalize(this);
    }

    public Note withOctave(Octaves octave) {
        return new Note(noteName, accidental, octave);
    }

    public Note withAccidental(Accidentals accidental) {
        return new Note(noteName, accidental, octave);
    }
}

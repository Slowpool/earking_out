package org.swetlokognatsk.earking_out.core.domain.model.music.sounds;

import java.io.Serializable;
import java.util.Objects;
import org.swetlokognatsk.earking_out.core.domain.model.music.Accidentals;
import org.swetlokognatsk.earking_out.core.domain.model.music.NoteNames;
import org.swetlokognatsk.earking_out.core.domain.model.music.Octaves;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;
import org.swetlokognatsk.earking_out.core.domain.services.domain.music.NotesNormalizingService;
import org.swetlokognatsk.earking_out.core.ports.di.DI;

// TODO apply the same in-memory optimization as for PianoKeyNumber
public record Note(NoteNames noteName, Accidentals accidental, Octaves octave) implements Serializable {
    private static final long serialVersionUID = 1L;

    public Note {
        Objects.requireNonNull(noteName);
        Objects.requireNonNull(octave);
    }

    // technically, low-level-module-depends-on-high-level-module violation. pragmatically, convenience method
    public PianoKeyNumber normalize() {
        return DI.get(NotesNormalizingService.class)
                .normalize(this);
    }

    public Note withOctave(Octaves octave) {
        return new Note(noteName, accidental, octave);
    }

    public Note withAccidental(Accidentals accidental) {
        return new Note(noteName, accidental, octave);
    }
}

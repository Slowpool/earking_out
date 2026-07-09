package org.swetlokognatsk.earking_out.core.domain.model.music.sounds;

import java.io.Serializable;
import java.util.Objects;
import org.swetlokognatsk.earking_out.core.domain.model.base.ValueObject;
import org.swetlokognatsk.earking_out.core.domain.model.music.Accidentals;
import org.swetlokognatsk.earking_out.core.domain.model.music.NoteNames;
import org.swetlokognatsk.earking_out.core.domain.model.music.Octaves;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;
import org.swetlokognatsk.earking_out.core.ports.DI;
import org.swetlokognatsk.earking_out.core.ports.music.NoteNormalizer;

public class Note extends ValueObject implements Serializable {
    private static final long serialVersionUID = 1L;

    public final NoteNames noteName;
    public final Accidentals accidental;
    public final Octaves octave;

    public Note(final NoteNames noteName, final Accidentals accidental, final Octaves octave) {
        this.noteName = Objects.requireNonNull(noteName);
        this.accidental = Objects.requireNonNull(accidental);
        this.octave = Objects.requireNonNull(octave);
    }

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

    public int hashCode() {
        return noteName.hashCode() + accidental.hashCode() + octave.hashCode();
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Note)) {
            return false;
        }
        var other = (Note) obj;
        return noteName.equals(other.noteName) && accidental.equals(other.accidental) && octave.equals(other.octave);
    }
}

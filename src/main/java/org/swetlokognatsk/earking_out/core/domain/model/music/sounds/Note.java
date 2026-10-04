package org.swetlokognatsk.earking_out.core.domain.model.music.sounds;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import static java.util.Objects.requireNonNull;
import org.swetlokognatsk.earking_out.core.domain.model.base.ValueObject;
import org.swetlokognatsk.earking_out.core.domain.model.music.Accidentals;
import org.swetlokognatsk.earking_out.core.domain.model.music.NoteNames;
import org.swetlokognatsk.earking_out.core.domain.model.music.Octaves;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;
import org.swetlokognatsk.earking_out.core.domain.services.domain.music.NotesNormalizingService;
import org.swetlokognatsk.earking_out.core.ports.di.DI;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class Note extends ValueObject implements Serializable {
    private static final long serialVersionUID = 1L;
    private static Set<Note> innerStorage;

    public final NoteNames noteName;
    public final Accidentals accidental;
    public final Octaves octave;

    // `pianoKeyNumber <-> note` is one<-to->one-or-two relationship. so, this variable is more the reference, rather than the value constituting the state
    public final PianoKeyNumber keyNumber;

    static {
        var innerStorage = new HashSet<>();

        for (var noteName : NoteNames.values()) {
            for (var accidental : Accidentals.values()) {
                for (var octave : Octaves.values()) {
                    innerStorage.add(new Note(noteName, accidental, octave));
                }
            }
        }
    }

    private Note(final NoteNames noteName, final Accidentals accidental, final Octaves octave, final PianoKeyNumber keyNumber) {
        this.noteName = requireNonNull(noteName);
        this.accidental = accidental;
        this.octave = requireNonNull(octave);

        this.keyNumber = requireNonNull(keyNumber);
    }

    public static Note valueOf(final NoteNames noteName, final Accidentals accidental, final Octaves octave) {

    }

    // technically, low-level-module-depends-on-high-level-module violation. pragmatically, convenience method
    public PianoKeyNumber normalize() {
        return DI.get(NotesNormalizingService.class)
                .normalize(this);
    }

    public Note withOctave(Octaves octave) {
        return valueOf(noteName, accidental, octave);
    }

    public Note withAccidental(Accidentals accidental) {
        return valueOf(noteName, accidental, octave);
    }
}

package org.swetlokognatsk.earking_out.core.domain.model.music.sounds;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import static java.util.Objects.requireNonNull;
import org.swetlokognatsk.earking_out.core.domain.model.base.ValueObject;
import org.swetlokognatsk.earking_out.core.domain.model.music.Accidentals;
import org.swetlokognatsk.earking_out.core.domain.model.music.NoteNames;
import org.swetlokognatsk.earking_out.core.domain.model.music.Octaves;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;
import org.swetlokognatsk.earking_out.core.domain.services.domain.music.NotesNormalizingService;
import org.swetlokognatsk.earking_out.core.ports.di.DI;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * `pianoKeyNumber <-> note` is one<-to->one-or-two relationship
 */
@Data
@EqualsAndHashCode(callSuper = false)
public final class Note extends ValueObject implements Serializable {
    private static final long serialVersionUID = 1L;
    private static Set<Note> innerStorage;

    public final NoteNames noteName;
    public final Accidentals accidental;
    public final Octaves octave;

    static {
        var innerStorage = new HashSet<Note>();

        var accidentals = new ArrayList<>(Arrays.asList(Accidentals.values()));
        accidentals.add(null);
        for (var noteName : NoteNames.values()) {
            for (var accidental : accidentals) {
                for (var octave : Octaves.values()) {
                    innerStorage.add(new Note(noteName, accidental, octave));
                }
            }
        }

        Note.innerStorage = innerStorage;
    }

    private Note(final NoteNames noteName, final Accidentals accidental, final Octaves octave) {
        this.noteName = requireNonNull(noteName);
        this.accidental = accidental;
        this.octave = requireNonNull(octave);
    }

    public static Note valueOf(final NoteNames noteName, final Accidentals accidental, final Octaves octave) {
        // yep, pretty not optimized, but we know that premature optimization is bad idea
        return innerStorage.stream()
                .filter((Note note) -> note.noteName.equals(noteName)
                        && Objects.equals(note.accidental, accidental)
                        && note.octave.equals(octave))
                .findFirst()
                .orElseThrow();
    }

    public static Note[] denormalize(final PianoKeyNumber pianoKeyNumber, final boolean excludeNaturalAccidental) {
        return innerStorage.stream()
                .filter((Note note) -> Objects.equals(note.normalize(), pianoKeyNumber) &&
                        !(excludeNaturalAccidental && Objects.equals(note.accidental, Accidentals.NATURAL)))
                .toArray(Note[]::new);
    }

    public static Note[] denormalize(final PianoKeyNumber pianoKeyNumber) {
        return denormalize(pianoKeyNumber, false);
    }

    public static Note[] getAll() {
        return innerStorage.toArray(Note[]::new);
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

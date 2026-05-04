package org.swetlokognatsk.earking_out.core.domain.model.music;

// TODO make it value object
// TODO somehow mark that Accidentals is nullable
public record NoteWithAccidental(NoteNames noteName, Accidentals accidental, Octaves octave) {
    // public NoteWithAccidental(NoteNames noteName, Accidentals accidental, Octaves octave) {

    // }

    public byte normalize() {
        return 0;
    }

    public NoteWithAccidental withOctave(Octaves octave) {
        return new NoteWithAccidental(noteName, accidental, octave);
    }
}

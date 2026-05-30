package org.swetlokognatsk.earking_out.core.domain.model.music;

public enum NoteNames {
    C((byte) 1), D((byte) 3), E((byte) 5), F((byte) 6), G((byte) 8), A((byte) 10), B((byte) 12);

    public final byte keyNumber;

    private NoteNames(byte keyNumber) {
        this.keyNumber = keyNumber;
    }
}

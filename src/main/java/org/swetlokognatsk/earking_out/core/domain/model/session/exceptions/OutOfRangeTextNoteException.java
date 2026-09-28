package org.swetlokognatsk.earking_out.core.domain.model.session.exceptions;

public final class OutOfRangeTextNoteException extends Exception {
    public final String invalidTextNote;

    public OutOfRangeTextNoteException(final String invalidTextNote) {
        this.invalidTextNote = invalidTextNote;
    }
}

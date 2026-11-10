package org.swetlokognatsk.earking_out.core.domain.model.session.exceptions;

public final class InvalidTextNoteException extends Exception {
    public final String invalidTextNote;

    public InvalidTextNoteException(final String invalidTextNote) {
        this.invalidTextNote = invalidTextNote;
    }
}

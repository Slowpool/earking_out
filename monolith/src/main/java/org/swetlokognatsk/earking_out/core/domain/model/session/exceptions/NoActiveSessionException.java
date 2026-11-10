package org.swetlokognatsk.earking_out.core.domain.model.session.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NoActiveSessionException extends RuntimeException {

    public NoActiveSessionException(final String message) {
        super(message);
    }
}

package org.swetlokognatsk.earking_out.core.domain.model.base;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AggregateNotFoundException extends RuntimeException {

    public AggregateNotFoundException(final String message) {
        super(message);
    }
}

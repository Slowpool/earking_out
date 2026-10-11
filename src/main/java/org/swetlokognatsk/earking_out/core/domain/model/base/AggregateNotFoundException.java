package org.swetlokognatsk.earking_out.core.domain.model.base;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AggregateNotFoundException extends Exception {

    public AggregateNotFoundException(final String message) {
        super(message);
    }
}

package org.swetlokognatsk.earking_out.core.domain.model.identity;

import java.io.Serializable;

public record UserId(int id) implements Serializable {
    private static final long serialVersionUID = 1L;

    public String toString() {
        return String.valueOf(id);
    }
}
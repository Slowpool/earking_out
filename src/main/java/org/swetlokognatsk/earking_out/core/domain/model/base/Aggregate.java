package org.swetlokognatsk.earking_out.core.domain.model.base;

import org.apache.commons.lang3.NotImplementedException;

public abstract class Aggregate extends Entity implements Model {

    public String[] getErrors() {
        throw new NotImplementedException();
    }
}

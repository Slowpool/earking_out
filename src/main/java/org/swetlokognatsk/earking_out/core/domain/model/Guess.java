package org.swetlokognatsk.earking_out.core.domain.model;

import org.swetlokognatsk.earking_out.core.domain.model.base.ValueObject;

public sealed class Guess extends ValueObject permits Solution {
    public final String value;

    public Guess(String guess) {
        this.value = guess;
    }

    public int hashCode() {
        return value.hashCode();
    }
    
    public boolean equals(Object obj) {
        boolean result;
        if (obj instanceof Solution) {
            var solution = (Solution)obj;
            result = value.equals(solution.value);
        }
        else {
            result = super.equals(obj);
        }
        return result;
    }
}

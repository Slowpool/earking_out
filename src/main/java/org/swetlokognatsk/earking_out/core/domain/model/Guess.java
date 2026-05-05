package org.swetlokognatsk.earking_out.core.domain.model;

public sealed class Guess permits Solution {
    public final String guess;

    public Guess(String guess) {
        this.guess = guess;
    }

    public boolean equals(Object obj) {
        boolean result;
        if (obj instanceof Solution) {
            var solution = (Solution)obj;
            result = guess == solution.guess;
        }
        else {
            result = super.equals(obj);
        }
        return result;
    }
}

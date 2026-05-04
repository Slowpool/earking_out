package org.swetlokognatsk.earking_out.core.domain.model;

public sealed class Guess permits Solution {
    public final String guess;

    public Guess(String guess) {
        this.guess = guess;
    }
}

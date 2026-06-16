package org.swetlokognatsk.earking_out.core.domain.model;

import org.swetlokognatsk.earking_out.core.domain.model.base.ValueObject;

// TODO use OOP
// TODO how 'bout renaming it to SessionProgress?
public final class SessionStats extends ValueObject {
    public final int puzzlesCompletedCorrectly;
    public final int puzzlesCompleted;

    public SessionStats(final int puzzlesCompletedCorrectly, final int puzzlesCompleted) {
        this.puzzlesCompletedCorrectly = puzzlesCompletedCorrectly;
        this.puzzlesCompleted = puzzlesCompleted;
    }
}

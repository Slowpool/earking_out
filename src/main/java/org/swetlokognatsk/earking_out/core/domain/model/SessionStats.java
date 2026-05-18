package org.swetlokognatsk.earking_out.core.domain.model;

// TODO use OOP
// TODO how 'bout renaming it to SessionProgress?
public class SessionStats {
    public int puzzlesCompletedCorrectly;
    public final int puzzlesCompleted;

    public SessionStats(final int puzzlesCompletedCorrectly, final int puzzlesCompleted) {
        this.puzzlesCompletedCorrectly = puzzlesCompletedCorrectly;
        this.puzzlesCompleted = puzzlesCompleted;
    }
}

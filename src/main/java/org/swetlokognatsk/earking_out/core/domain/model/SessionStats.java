package org.swetlokognatsk.earking_out.core.domain.model;

// TODO use OOP
// TODO how 'bout renaming it to SessionProgress?
public class SessionStats {
    public int puzzlesCompletedCorrectly;
    public int puzzlesCompleted;

    public SessionStats(int puzzlesCompletedCorrectly, int puzzlesCompleted) {
        this.puzzlesCompletedCorrectly = puzzlesCompletedCorrectly;
        this.puzzlesCompleted = puzzlesCompleted;
    }
}

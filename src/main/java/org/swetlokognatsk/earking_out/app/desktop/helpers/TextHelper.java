package org.swetlokognatsk.earking_out.app.desktop.helpers;

public final class TextHelper {

    public static String interpolatePuzzleProgress(final int numberOfPuzzles, final int targetNumberOfPuzzles) {
        return String.format("%d of %d are completed", numberOfPuzzles, targetNumberOfPuzzles);
    }
}

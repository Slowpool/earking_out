package org.swetlokognatsk.earking_out.core.domain.model.music;

public final class Invariants {
    private Invariants() {
    }

    /**
     * Skipping A0, A#0, B0 notes, so that `C1.normalize() == 4`. Why not 1? These
     * three notes aren't used in app by three reasons: 1. To simplify the logic 2.
     * I doubt anybody gonna use them 3. To keep key numbers names as-is in domain
     * 
     * @see https://en.wikipedia.org/wiki/Piano_key_frequencies ctrl+f `Piano key
     *      number`
     */
    public static final byte SHIFT = 3;
    public static final byte FIRST_NOTE_NUMBER = 1 + SHIFT;
    /**
     * 12 semitones
     */
    public static final int KEYS_IN_OCTAVE = 12;
    public static final int WHITE_KEYS_IN_OCTAVE = 7;
    public static final int BLACK_KEYS_IN_OCTAVE = KEYS_IN_OCTAVE - WHITE_KEYS_IN_OCTAVE;
    /**
     * 3 left and 1 right keys are omitted
     */
    // TODO why WHITE_PIANO_KEYS_NUMBER gives error when the expression is used here?
    public static final byte PIANO_KEYS_NUMBER = 84;//(byte)(KEYS_IN_OCTAVE * Octaves.values().length);
    /**
     * Active octave is octave, that may be used in exercises
     */
    public static final byte ACTIVE_OCTAVES_NUMBER = (byte) (PIANO_KEYS_NUMBER / KEYS_IN_OCTAVE);
    public static final byte WHITE_PIANO_KEYS_NUMBER = ACTIVE_OCTAVES_NUMBER * WHITE_KEYS_IN_OCTAVE;
    public static final byte BLACK_PIANO_KEYS_NUMBER = ACTIVE_OCTAVES_NUMBER * BLACK_KEYS_IN_OCTAVE;

    // source: https://www.reddit.com/r/piano/comments/wkofm/what_are_the_dimension_of_the_piano_keys/
    private static final double WHITE_KEY_WIDTH_CM = 2.357142857;
    private static final double BLACK_KEY_WIDTH_CM = 0.95;
    public static final double BLACK_KEY_WIDTH_RELATIVELY_TO_WHITE_KEY_WIDTH = BLACK_KEY_WIDTH_CM / WHITE_KEY_WIDTH_CM;

    private static final double WHITE_KEY_LENGTH = 15;
    private static final double BLACK_KEY_LENGTH = 9;
    public static final double BLACK_KEY_LENGTH_RELATIVELY_TO_WHITE_KEY_LENGTH = BLACK_KEY_LENGTH / WHITE_KEY_LENGTH;

    // TODO actually it's not invariant, then where to store it?
    public static final String APP_NAME = "Earking out";
}

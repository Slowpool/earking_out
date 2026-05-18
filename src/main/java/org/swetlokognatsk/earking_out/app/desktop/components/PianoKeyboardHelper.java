package org.swetlokognatsk.earking_out.app.desktop.components;

import org.swetlokognatsk.earking_out.core.domain.model.music.Invariants;
import org.swetlokognatsk.earking_out.core.ports.music.NoteNormalizer;
import javafx.geometry.Point2D;

final class PianoKeyboardHelper {
    public static Point2D calculatePosition(byte keyNumber, double keyboardWidth) {
        double x = isWhite(keyNumber) ? calculateWhiteX(keyNumber, keyboardWidth) : calculateBlackX(keyNumber, keyboardWidth);
        return new Point2D(x, 0);
    }

    // TODO put it into core, this is a business logic
    private static boolean isWhite(byte keyNumber) {
        var keyNumberModule = keyNumber - NoteNormalizer.SHIFT;
        if (keyNumberModule <= Invariants.KEYS_IN_OCTAVE) {
            return switch (keyNumberModule) {
            case 1, 3, 5, 6, 8, 10, 12 -> true;
            case 2, 4, 7, 9, 11 -> false;
            default -> throw new RuntimeException("wrong math for keyNumberModule: " + keyNumberModule);
            };
        }

        return isWhite((byte) (keyNumber - Invariants.KEYS_IN_OCTAVE));
    }

    private static double calculateWhiteX(byte keyNumber, double keyboardWidth) {
        byte whiteKeyNumber = getWhiteKeyNumber((byte) (keyNumber - NoteNormalizer.SHIFT));
        var whiteKeyWidth = calculateWhiteKeyWidth(keyboardWidth);
        return (whiteKeyNumber - 1) * whiteKeyWidth;
    }

    // TODO make shift to be obvious
    private static byte getWhiteKeyNumber(byte keyNumber) {
        if (keyNumber <= Invariants.KEYS_IN_OCTAVE) {
            return switch (keyNumber) {
            case 1 -> 1;
            case 3 -> 2;
            case 5 -> 3;
            case 6 -> 4;
            case 8 -> 5;
            case 10 -> 6;
            case 12 -> 7;
            default -> throw new RuntimeException("key is black or number is illegal: " + keyNumber);
            };
        }

        var whiteKeyNumberSkippingOctave = getWhiteKeyNumber((byte) (keyNumber - Invariants.KEYS_IN_OCTAVE));
        return (byte) (Invariants.WHITE_KEYS_IN_OCTAVE + whiteKeyNumberSkippingOctave);
    }

    private static double calculateWhiteKeyWidth(double keyboardWidth) {
        return keyboardWidth / Invariants.WHITE_PIANO_KEYS_NUMBER;
    }

    private static double calculateBlackX(byte keyNumber, double keyboardWidth) {
        byte shiftedKeyNumber = (byte)(keyNumber - NoteNormalizer.SHIFT);
        var whiteKeyWidth = calculateWhiteKeyWidth(keyboardWidth);
        if (shiftedKeyNumber <= Invariants.KEYS_IN_OCTAVE) {
            var blackKeyWidth = calculateBlackKeyWidth(keyboardWidth);
            return whiteKeyWidth - (blackKeyWidth / 2);
        }

        return Invariants.WHITE_KEYS_IN_OCTAVE * whiteKeyWidth + calculateBlackX((byte)(keyNumber - Invariants.KEYS_IN_OCTAVE), keyboardWidth);
    }

    private static double calculateBlackKeyWidth(double keyboardWidth) {
        return calculateWhiteKeyWidth(keyboardWidth) * Invariants.BLACK_KEY_WIDTH_RELATIVELY_TO_WHITE_KEY_WIDTH;
    }

    public static double calculateWidth(byte keyNumber, double keyboardWidth) {
        return isWhite(keyNumber) ? calculateWhiteKeyWidth(keyboardWidth) : calculateBlackKeyWidth(keyboardWidth);
    }

    public static double calculateHeight(byte keyNumber, double keyboardHeight) {
        return isWhite(keyNumber) ? keyboardHeight : calculateBlackKeyHeight(keyboardHeight);
    }

    private static double calculateBlackKeyHeight(double whiteKeyHeight) {
        return whiteKeyHeight * Invariants.BLACK_KEY_LENGTH_RELATIVELY_TO_WHITE_KEY_LENGTH;
    }
}

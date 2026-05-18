package org.swetlokognatsk.earking_out.app.desktop.builders;

import java.util.Iterator;
import org.swetlokognatsk.earking_out.app.desktop.components.PianoKey;
import org.swetlokognatsk.earking_out.core.domain.model.music.Invariants;
import org.swetlokognatsk.earking_out.core.ports.music.NoteNormalizer;
import javafx.geometry.Point2D;

// TODO should it be final?
public class PianoKeysBuilder implements Iterator<PianoKey> {
    protected final byte firstNoteNumber = Invariants.FIRST_NOTE_NUMBER;
    protected final double keyboardWidth;
    protected final double keyboardHeight;

    protected final double whiteKeyWidth;
    protected final double whiteKeyHeight;

    protected final double blackKeyWidth;
    protected final double blackKeyHeight;

    protected byte currentKeyIndex = 0;
    protected byte currentWhiteX = 0;

    public PianoKeysBuilder(double keyboardWidth, double keyboardHeight) {
        this.keyboardWidth = keyboardWidth;
        this.keyboardHeight = keyboardHeight;

        whiteKeyWidth = calculateWhiteKeyWidth();
        whiteKeyHeight = calculateWhiteKeyHeight();

        blackKeyWidth = calculateBlackKeyWidth();
        blackKeyHeight = calculateBlackKeyHeight();
    }

    private double calculateWhiteKeyWidth() {
        return keyboardWidth / Invariants.WHITE_PIANO_KEYS_NUMBER;
    }

    private double calculateWhiteKeyHeight() {
        return keyboardHeight;
    }

    private double calculateBlackKeyWidth() {
        return whiteKeyWidth * Invariants.BLACK_KEY_WIDTH_RELATIVELY_TO_WHITE_KEY_WIDTH;
    }

    private double calculateBlackKeyHeight() {
        return whiteKeyHeight * Invariants.BLACK_KEY_LENGTH_RELATIVELY_TO_WHITE_KEY_LENGTH;
    }

    public boolean hasNext() {
        return currentKeyIndex < Invariants.PIANO_KEYS_NUMBER - 1;
    }

    public PianoKey next() {
        var pianoKey = new PianoKey(getCurrentKeyNumber());
        var x = calculateX();
        // TODO why Translate?
        pianoKey.setTranslateX(x);
        // TODO event handler

        var keyWidth = calculateWidth();
        pianoKey.setPrefWidth(keyWidth);

        var keyHeight = calculateHeight();
        pianoKey.setPrefHeight(keyHeight);

        currentKeyIndex++;

        return pianoKey;
    }

    private byte getCurrentKeyNumber() {
        return (byte) (currentKeyIndex + firstNoteNumber);
    }

    private byte getShiftedKeyNumber() {
        return (byte) (getCurrentKeyNumber() - NoteNormalizer.SHIFT);
    }

    private byte getCurrentKeyNumberInOctave() {
        // - 1 and + 1 is math trick to get 12 when getShiftedKeyNumber() == 12
        return (byte) ((getShiftedKeyNumber() - 1) % Invariants.KEYS_IN_OCTAVE + 1);
    }

    private double calculateX() {
        double x = isWhite() ? nextWhiteX() : calculateBlackX();
        return x;
    }

    private boolean isWhite() {
        return switch (getCurrentKeyNumberInOctave()) {
        case 1, 3, 5, 6, 8, 10, 12 -> true;
        case 2, 4, 7, 9, 11 -> false;
        default -> throw new RuntimeException("wrong math for isWhite(): " + getCurrentKeyNumberInOctave());
        };
    }

    private double nextWhiteX() {
        var x = currentWhiteX;
        currentWhiteX += whiteKeyWidth;
        return x;
    }

    private double calculateBlackX() {
        // TODO the only thing remained to do
        byte shiftedKeyNumber = (byte) (getCurrentKeyNumber() - NoteNormalizer.SHIFT);
        // if (shiftedKeyNumber <= Invariants.KEYS_IN_OCTAVE) {
        return whiteKeyWidth - (blackKeyWidth / 2);
        // }

        // return Invariants.WHITE_KEYS_IN_OCTAVE * whiteKeyWidth + calculateBlackX((byte) (keyNumber - Invariants.KEYS_IN_OCTAVE), keyboardWidth);
    }

    private double calculateWidth() {
        return isWhite() ? whiteKeyWidth : blackKeyWidth;
    }

    private double calculateHeight() {
        return isWhite() ? whiteKeyHeight : blackKeyHeight;
    }
}

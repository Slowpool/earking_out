package org.swetlokognatsk.earking_out.app.desktop.services;

import java.io.File;
import java.util.Iterator;
import java.util.Map;
import org.swetlokognatsk.earking_out.app.desktop.components.PianoKey;
import org.swetlokognatsk.earking_out.core.domain.model.music.Invariants;

// TODO should it be final?
public class PianoKeysBuilder implements Iterator<PianoKey> {
    protected final byte firstNoteNumber = Invariants.FIRST_NOTE_NUMBER;
    protected final double keyboardWidth;
    protected final double keyboardHeight;

    protected final Map<Byte, String> keySounds;

    protected final double whiteKeyWidth;
    protected final double whiteKeyHeight;

    protected final double blackKeyWidth;
    protected final double blackKeyHeight;

    protected byte currentKeyIndex = 0;
    protected double currentWhiteX = 0;
    protected double currentBlackX;

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

    private double initBlackX() {
        return whiteKeyWidth - (blackKeyWidth / 2);
    }

    private byte getCurrentKeyNumber() {
        return (byte) (currentKeyIndex + firstNoteNumber);
    }

    private byte getCurrentKeyNumberInOctave() {
        return (byte) (currentKeyIndex % Invariants.KEYS_IN_OCTAVE + 1);
    }

    public PianoKeysBuilder(double keyboardWidth, double keyboardHeight, Map<Byte, String> keySounds) {
        this.keyboardWidth = keyboardWidth;
        this.keyboardHeight = keyboardHeight;
        this.keySounds = keySounds;

        whiteKeyWidth = calculateWhiteKeyWidth();
        whiteKeyHeight = calculateWhiteKeyHeight();

        blackKeyWidth = calculateBlackKeyWidth();
        blackKeyHeight = calculateBlackKeyHeight();

        // it must be here, not in `protected double currentBlackX = ...` line due to the order of other properties assigning
        currentBlackX = initBlackX();
    }

    public boolean hasNext() {
        return currentKeyIndex < Invariants.PIANO_KEYS_NUMBER;
    }

    public PianoKey next() {
        var sound = keySounds.get(getCurrentKeyNumber());
        var soundFile = new File(sound);
        var fileSoundPlayer = new FileSoundPlayer(soundFile);
        var pianoKey = new PianoKey(getCurrentKeyNumber(), isWhite(), fileSoundPlayer);

        calculatePosition(pianoKey);
        calculateDimensions(pianoKey);

        currentKeyIndex++;

        return pianoKey;
    }

    private void calculatePosition(PianoKey pianoKey) {
        var x = calculateX();
        pianoKey.setTranslateX(x);
    }

    private void calculateDimensions(final PianoKey pianoKey) {
        var keyWidth = calculateWidth();
        pianoKey.setPrefWidth(keyWidth);

        var keyHeight = calculateHeight();
        pianoKey.setPrefHeight(keyHeight);
    }

    private double calculateX() {
        double x = isWhite() ? nextWhiteX() : nextBlackX();
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

    private double nextBlackX() {
        var x = currentBlackX;
        switch (getCurrentKeyNumberInOctave()) {
        case 2, 7, 9:
            currentBlackX += whiteKeyWidth;
            break;
        case 4, 11:
            currentBlackX += whiteKeyWidth * 2;
            break;
        default:
            throw new RuntimeException("calling nextBlackX for not black key: " + getCurrentKeyNumberInOctave());
        }
        return x;
    }

    private double calculateWidth() {
        return isWhite() ? whiteKeyWidth : blackKeyWidth;
    }

    private double calculateHeight() {
        return isWhite() ? whiteKeyHeight : blackKeyHeight;
    }
}

package org.swetlokognatsk.earking_out.app.desktop.services;

import static org.swetlokognatsk.earking_out.core.domain.model.music.Invariants.*;
// import java.io.File;
import java.util.Iterator;
import java.util.Map;
import org.apache.commons.lang3.ArrayUtils;
import org.swetlokognatsk.earking_out.app.desktop.components.BlackPianoKey;
import org.swetlokognatsk.earking_out.app.desktop.components.PianoKey;
import org.swetlokognatsk.earking_out.app.desktop.components.WhitePianoKey;
import org.swetlokognatsk.earking_out.core.domain.model.music.Invariants;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyColor;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;
import org.swetlokognatsk.earking_out.core.domain.services.domain.piano.PianoKeyColorService;

public final class PianoKeysBuilder implements Iterator<PianoKey> {
    protected final PianoKeyNumber firstNoteNumber = FIRST_NOTE_NUMBER;
    protected final double keyboardWidth;
    protected final double keyboardHeight;

    protected final PianoKeyNumber[] selectedKeys;
    protected final PianoKeyColorService colorService;
    protected final Map<PianoKeyNumber, String> keySounds;

    protected final double whiteKeyWidth;
    protected final double whiteKeyHeight;

    protected final double blackKeyWidth;
    protected final double blackKeyHeight;

    // -1 is acceptable value for Iterator<?>
    protected byte currentKeyIndex = -1;
    protected double currentWhiteX;
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

    public PianoKeyNumber getCurrentKeyNumber() {
        return firstNoteNumber.add(currentKeyIndex);
    }

    private byte getCurrentKeyNumberInOctave() {
        return getCurrentKeyNumber().octaveScopedKeyNumber;
    }

    public PianoKeysBuilder(final double keyboardWidth, final double keyboardHeight, final PianoKeyNumber[] selectedKeys, final Map<PianoKeyNumber, String> keySounds, final PianoKeyColorService colorService) {
        this.keyboardWidth = keyboardWidth;
        this.keyboardHeight = keyboardHeight;
        this.selectedKeys = selectedKeys;
        this.keySounds = keySounds;
        this.colorService = colorService;

        whiteKeyWidth = calculateWhiteKeyWidth();
        whiteKeyHeight = calculateWhiteKeyHeight();

        blackKeyWidth = calculateBlackKeyWidth();
        blackKeyHeight = calculateBlackKeyHeight();

        currentWhiteX = 0;
        // it must be here, not in `protected double currentBlackX = ...` line due to the order of other properties assigning
        currentBlackX = initBlackX();
    }

    public boolean hasNext() {
        return currentKeyIndex < Invariants.PIANO_KEYS_NUMBER;
    }

    public PianoKey next() {
        currentKeyIndex++;

        // var sound = keySounds.get(getCurrentKeyNumber());
        // var soundFile = new File(sound);
        // var fileSoundPlayer = new FileSoundPlayer(soundFile);
        var isSelected = ArrayUtils.contains(selectedKeys, getCurrentKeyNumber());
        var pianoKey = isWhite() ? new WhitePianoKey(isSelected) : new BlackPianoKey(isSelected);

        calculatePosition(pianoKey);
        calculateDimensions(pianoKey);

        return pianoKey;
    }

    private void calculatePosition(final PianoKey pianoKey) {
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
        return isWhite() ? nextWhiteX() : nextBlackX();
    }

    private boolean isWhite() {
        return colorService.getColor(getCurrentKeyNumber()) == PianoKeyColor.WHITE;
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
            throw new IllegalStateException("calling nextBlackX for not black key: " + getCurrentKeyNumberInOctave());
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

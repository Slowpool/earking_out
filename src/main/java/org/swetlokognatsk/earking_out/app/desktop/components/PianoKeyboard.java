package org.swetlokognatsk.earking_out.app.desktop.components;

import org.swetlokognatsk.earking_out.core.domain.model.music.Invariants;
import javafx.scene.layout.Region;

public class PianoKeyboard extends Region {
    public final PianoKeyboardMode mode;
    protected final PianoKey[] pianoKeys;

    public PianoKeyboard(final PianoKeyboardMode mode, double width, double height) {
        this.mode = mode;
        this.setHeight(height);
        this.setWidth(width);

        this.pianoKeys = buildPianoKeys();
        addPianoKeys();
    }

    private PianoKey[] buildPianoKeys() {
        var pianoKeys = new PianoKey[Invariants.PIANO_KEYS_NUMBER];

        byte keyNumber;
        for (byte i = 0; i < Invariants.PIANO_KEYS_NUMBER; i++) {
            keyNumber = (byte) (Invariants.FIRST_NOTE_NUMBER + i);
            pianoKeys[i] = buildPianoKey(keyNumber);
        }
        return pianoKeys;
    }

    private PianoKey buildPianoKey(byte keyNumber) {
        // TODO how 'bout encapsulation something into PianoKey?
        var pianoKey = new PianoKey(keyNumber);
        var point = PianoKeyboardHelper.calculatePosition(keyNumber, getWidth());
        // TODO why Translate?
        pianoKey.setTranslateX(point.getX());
        pianoKey.setTranslateY(point.getY());
        // TODO event handler

        var keyWidth = PianoKeyboardHelper.calculateWidth(keyNumber, getWidth());
        pianoKey.setPrefWidth(keyWidth);

        var keyHeight = PianoKeyboardHelper.calculateHeight(keyNumber, getHeight());
        pianoKey.setPrefHeight(keyHeight);

        return pianoKey;
    }

    private void addPianoKeys() {
        this.getChildren().addAll(pianoKeys);
    }

}

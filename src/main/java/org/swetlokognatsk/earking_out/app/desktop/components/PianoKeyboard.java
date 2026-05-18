package org.swetlokognatsk.earking_out.app.desktop.components;

import org.swetlokognatsk.earking_out.app.desktop.builders.PianoKeysBuilder;
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

        var pianoKeysBuilder = new PianoKeysBuilder(getWidth(), getHeight());
        for (byte i = 0; pianoKeysBuilder.hasNext(); i++) {
            pianoKeys[i] = pianoKeysBuilder.next();
        }
        return pianoKeys;
    }

    private void addPianoKeys() {
        this.getChildren().addAll(pianoKeys);
    }

}

package org.swetlokognatsk.earking_out.app.desktop.components;

import org.swetlokognatsk.earking_out.app.desktop.builders.PianoKeysBuilder;
import org.swetlokognatsk.earking_out.core.domain.model.music.Invariants;
import javafx.beans.property.SetProperty;
import javafx.beans.property.SimpleSetProperty;
import javafx.collections.FXCollections;
import javafx.scene.layout.Region;

public class PianoKeyboard extends Region {
    private static final int WHITE_KEYS = 0;
    private static final int BLACK_KEYS = 1;

    public final PianoKeyboardMode mode;
    protected final PianoKey[] whitePianoKeys;
    protected final PianoKey[] blackPianoKeys;

    // TODO what's the difference between observable and property?
    protected SetProperty<Byte> selectedKeys = new SimpleSetProperty<>(FXCollections.observableSet());

    public SetProperty<Byte> selectedKeysProperty() {
        return selectedKeys;
    }

    public PianoKeyboard(final PianoKeyboardMode mode, double width, double height) {
        this.mode = mode;
        setHeight(height);
        setWidth(width);

        var pianoKeys = buildPianoKeys();
        whitePianoKeys = pianoKeys[WHITE_KEYS];
        blackPianoKeys = pianoKeys[BLACK_KEYS];
        addPianoKeys();
    }

    private PianoKey[][] buildPianoKeys() {
        var pianoKeys = new PianoKey[2][];
        pianoKeys[WHITE_KEYS] = new PianoKey[Invariants.WHITE_PIANO_KEYS_NUMBER];
        pianoKeys[BLACK_KEYS] = new PianoKey[Invariants.BLACK_PIANO_KEYS_NUMBER];

        var pianoKeysBuilder = new PianoKeysBuilder(getWidth(), getHeight());
        // TODO dirty, dirty code, refactoring
        PianoKey pianoKey;
        int pianoKeyColor;
        int i;
        for (byte whiteI = 0, blackI = 0; pianoKeysBuilder.hasNext();) {
            pianoKey = pianoKeysBuilder.next();
            addEventHandlers(pianoKey);
            pianoKeyColor = pianoKey.isWhite() ? WHITE_KEYS : BLACK_KEYS;
            i = pianoKey.isWhite() ? whiteI++ : blackI++;
            pianoKeys[pianoKeyColor][i] = pianoKey;
        }
        return pianoKeys;
    }

    private void addPianoKeys() {
        getChildren().addAll(whitePianoKeys);
        getChildren().addAll(blackPianoKeys);
    }

    private void addEventHandlers(PianoKey pianoKey) {
        pianoKey.setOnMousePressed(e -> {
            pianoKey.toggleSelection();

            if (pianoKey.isSelected()) {
                selectedKeys.add(pianoKey.keyNumber);
            } else {
                selectedKeys.remove(pianoKey.keyNumber);
            }
        });
    }

}

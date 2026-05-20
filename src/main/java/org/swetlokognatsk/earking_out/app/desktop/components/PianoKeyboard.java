package org.swetlokognatsk.earking_out.app.desktop.components;

import java.util.Arrays;
import java.util.HashSet;
import org.apache.commons.lang3.ArrayUtils;
import org.swetlokognatsk.earking_out.app.desktop.builders.PianoKeysBuilder;
import org.swetlokognatsk.earking_out.core.domain.model.music.Invariants;
import javafx.beans.property.SetProperty;
import javafx.beans.property.SimpleSetProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import javafx.scene.layout.Region;

// TODO add border to white keys, now they are kinda invisible
public class PianoKeyboard extends Region {
    private static final int WHITE_KEYS = 0;
    private static final int BLACK_KEYS = 1;

    public final PianoKeyboardMode mode;
    // TODO is it possible to use either only all keys or only white/black?
    protected final PianoKey[] allPianoKeys;
    protected final PianoKey[] whitePianoKeys;
    protected final PianoKey[] blackPianoKeys;

    // TODO what's the difference between observable and property?
    protected final SetProperty<Byte> selectedKeys;

    public SetProperty<Byte> selectedKeysProperty() {
        return selectedKeys;
    }

    public PianoKeyboard(final PianoKeyboardMode mode, double width, double height, byte[] selectedKeyNumbers) {
        this.mode = mode;
        setHeight(height);
        setWidth(width);

        allPianoKeys = buildPianoKeys();
        var dichotomizedPianoKeys = dichotomize(allPianoKeys);
        whitePianoKeys = dichotomizedPianoKeys[WHITE_KEYS];
        blackPianoKeys = dichotomizedPianoKeys[BLACK_KEYS];

        var selectedKeys = initSelectedKeys(selectedKeyNumbers);
        this.selectedKeys = new SimpleSetProperty<>(selectedKeys);

        addPianoKeys();
    }

    private PianoKey[] buildPianoKeys() {
        var allPianoKeys = new PianoKey[Invariants.PIANO_KEYS_NUMBER];

        var pianoKeysBuilder = new PianoKeysBuilder(getWidth(), getHeight());

        PianoKey pianoKey;
        for (int i = 0; pianoKeysBuilder.hasNext(); i++) {
            pianoKey = pianoKeysBuilder.next();
            addEventHandlers(pianoKey);
            allPianoKeys[i] = pianoKey;
        }
        return allPianoKeys;
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

    private static PianoKey[][] dichotomize(PianoKey[] pianoKeys) {
        // TODO dirty, dirty code, refactoring
        var dichotomizedPianoKeys = new PianoKey[2][];
        dichotomizedPianoKeys[WHITE_KEYS] = new PianoKey[Invariants.WHITE_PIANO_KEYS_NUMBER];
        dichotomizedPianoKeys[BLACK_KEYS] = new PianoKey[Invariants.BLACK_PIANO_KEYS_NUMBER];

        int pianoKeyColor;
        int i;
        byte whiteI = 0;
        byte blackI = 0;
        for (var pianoKey : pianoKeys) {
            pianoKeyColor = pianoKey.isWhite() ? WHITE_KEYS : BLACK_KEYS;
            i = pianoKey.isWhite() ? whiteI++ : blackI++;
            dichotomizedPianoKeys[pianoKeyColor][i] = pianoKey;
        }
        return dichotomizedPianoKeys;
    }

    protected ObservableSet<Byte> initSelectedKeys(byte[] keyNumbers) {
        var boxedKeyNumbers = ArrayUtils.toObject(keyNumbers);
        var set = new HashSet<Byte>(Arrays.asList(boxedKeyNumbers));
        var observableSet = FXCollections.observableSet(set);
        for (var pianoKey : allPianoKeys) {
            boolean shouldBeSelected = ArrayUtils.contains(keyNumbers, pianoKey.keyNumber);
            if (shouldBeSelected) {
                pianoKey.toggleSelection(shouldBeSelected);
            }
        }
        return observableSet;
    }

    private void addPianoKeys() {
        getChildren().addAll(whitePianoKeys);
        getChildren().addAll(blackPianoKeys);
    }
}

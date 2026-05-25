package org.swetlokognatsk.earking_out.app.desktop.components;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import org.apache.commons.lang3.ArrayUtils;
import org.swetlokognatsk.earking_out.app.desktop.services.PianoKeysBuilder;
import org.swetlokognatsk.earking_out.core.domain.model.music.Invariants;
import javafx.beans.property.SetProperty;
import javafx.beans.property.SimpleSetProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;

// TODO add border to white keys, now they are kinda invisible
public class PianoKeyboard extends Region {
    private static final int WHITE_KEYS = 0;
    private static final int BLACK_KEYS = 1;

    public final PianoKeyboardMode mode;
    // TODO is it possible to use either only all keys or only white/black?
    protected final Map<Byte, PianoKey> allPianoKeys;
    protected final PianoKey[] whitePianoKeys;
    protected final PianoKey[] blackPianoKeys;

    // TODO what's the difference between observable and property?
    protected final SetProperty<Byte> selectedKeys;

    public SetProperty<Byte> selectedKeysProperty() {
        return selectedKeys;
    }

    public PianoKeyboard(final PianoKeyboardMode mode, final double width, final double height, final byte[] selectedKeyNumbers) {
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

    private Map<Byte, PianoKey> buildPianoKeys() {
        var allPianoKeys = new HashMap<Byte, PianoKey>(Invariants.PIANO_KEYS_NUMBER);

        var pianoKeysBuilder = new PianoKeysBuilder(getWidth(), getHeight());

        PianoKey pianoKey;
        for (Byte i = 0; pianoKeysBuilder.hasNext(); i++) {
            pianoKey = pianoKeysBuilder.next();
            addEventHandlers(pianoKey);
            allPianoKeys.put(pianoKey.keyNumber, pianoKey);
        }
        return allPianoKeys;
    }

    private void addEventHandlers(PianoKey pianoKey) {
        var pressedHandler = createMousePressedHandler(pianoKey);
        if (pressedHandler != null) {
            pianoKey.setOnMousePressed(pressedHandler);
        }
        var releasedHandler = createMouseReleasedHandler(pianoKey);
        if (releasedHandler != null) {
            pianoKey.setOnMouseReleased(releasedHandler);
        }
    }

    protected EventHandler<? super MouseEvent> createMousePressedHandler(PianoKey pianoKey) {
        return switch (mode) {
        case SEVERAL_KEYS_SELECT -> e -> {
            selectOneOfSeveralKeys(pianoKey);
        };
        case ONE_KEY_SELECT -> e -> {
            selectOneKey(pianoKey);
        };
        case ONE_KEY_TOUCH -> e -> {
            touchOneKey(pianoKey);
        };
        default -> null;
        };
    }

    protected void selectOneOfSeveralKeys(PianoKey pianoKey) {
        pianoKey.playSound();

        toggleSelection(pianoKey);
    }

    protected void selectOneKey(PianoKey pianoKey) {
        pianoKey.playSound();

        if (pianoKey.isSelected()) {
            return;
        }
        tryUnselectPreviouslySelectedKey();
        toggleSelection(pianoKey);
    }

    protected void touchOneKey(PianoKey pianoKey) {
        pianoKey.playSound();

        toggleSelection(pianoKey);
    }

    protected EventHandler<? super MouseEvent> createMouseReleasedHandler(PianoKey pianoKey) {
        return switch (mode) {
        case ONE_KEY_TOUCH -> e -> {
            toggleSelection(pianoKey);
        };
        default -> null;
        };
    }

    protected void toggleSelection(PianoKey pianoKey) {
        pianoKey.toggleSelection();

        if (pianoKey.isSelected()) {
            selectedKeys.add(pianoKey.keyNumber);
        } else {
            selectedKeys.remove(pianoKey.keyNumber);
        }
    }

    /**
     * This method supopse that only one key was selected.
     */
    protected void tryUnselectPreviouslySelectedKey() {
        int numberOfSelectedKeys = selectedKeys.getValue().size();

        // nothing is selected, normal case
        if (numberOfSelectedKeys == 0) {
            return;
        } else if (numberOfSelectedKeys > 1) {
            // TODO how 'bout other exceptions
            throw new RuntimeException("several keys were selected, although only one key was supposed to be selected");
        }
        var selectedKeysIterator = selectedKeys.getValue().iterator();
        var selectedPianoKeyNumber = selectedKeysIterator.next();

        var oldSelectedPianoKey = allPianoKeys.get(selectedPianoKeyNumber);
        selectedKeys.remove(selectedPianoKeyNumber);

        oldSelectedPianoKey.toggleSelection();
    }

    private static PianoKey[][] dichotomize(Map<Byte, PianoKey> pianoKeys) {
        // TODO dirty, dirty code, refactoring
        var dichotomizedPianoKeys = new PianoKey[2][];
        dichotomizedPianoKeys[WHITE_KEYS] = new PianoKey[Invariants.WHITE_PIANO_KEYS_NUMBER];
        dichotomizedPianoKeys[BLACK_KEYS] = new PianoKey[Invariants.BLACK_PIANO_KEYS_NUMBER];

        int pianoKeyColor;
        int i;
        byte whiteI = 0;
        byte blackI = 0;
        for (var pianoKey : pianoKeys.values()) {
            pianoKeyColor = pianoKey.isWhite() ? WHITE_KEYS : BLACK_KEYS;
            i = pianoKey.isWhite() ? whiteI++ : blackI++;
            dichotomizedPianoKeys[pianoKeyColor][i] = pianoKey;
        }
        return dichotomizedPianoKeys;
    }

    protected ObservableSet<Byte> initSelectedKeys(byte[] keyNumbers) {
        validateKeyNumbersToSelect(keyNumbers);

        var boxedKeyNumbers = ArrayUtils.toObject(keyNumbers);
        var set = new HashSet<Byte>(Arrays.asList(boxedKeyNumbers));
        var observableSet = FXCollections.observableSet(set);

        boolean shouldBeSelected;
        for (var pianoKey : allPianoKeys.values()) {
            shouldBeSelected = ArrayUtils.contains(keyNumbers, pianoKey.keyNumber);
            if (shouldBeSelected) {
                pianoKey.toggleSelection(shouldBeSelected);
                if (mode == PianoKeyboardMode.ONE_KEY_SELECT) {
                    break;
                }
            }
        }
        return observableSet;
    }

    protected void validateKeyNumbersToSelect(byte[] keyNumbers) {
        if (!modeAllowsSelecting() && ArrayUtils.isNotEmpty(keyNumbers)) {
            // TODO maybe some other exceptions exist for that?
            throw new IllegalArgumentException("PianoKeyboard keys cannot be selected in this mode");
        }
        if (mode == PianoKeyboardMode.ONE_KEY_SELECT && keyNumbers.length > 1) {
            throw new IllegalArgumentException("this mode does not allow selecting more than 1 key");
        }
    }

    private boolean modeAllowsSelecting() {
        // TODO add any other?
        return !ArrayUtils.contains(new PianoKeyboardMode[] { PianoKeyboardMode.ONE_KEY_TOUCH }, mode);
    }

    private void addPianoKeys() {
        getChildren().addAll(whitePianoKeys);
        getChildren().addAll(blackPianoKeys);
    }
}

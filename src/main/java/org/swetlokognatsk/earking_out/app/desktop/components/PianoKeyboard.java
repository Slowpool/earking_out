package org.swetlokognatsk.earking_out.app.desktop.components;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import org.apache.commons.lang3.ArrayUtils;
import org.swetlokognatsk.earking_out.app.desktop.helpers.PianoKeysHelper;
import org.swetlokognatsk.earking_out.app.desktop.services.KeySoundsService;
import org.swetlokognatsk.earking_out.app.desktop.services.PianoKeysBuilder;
import org.swetlokognatsk.earking_out.core.domain.model.music.Invariants;
import org.swetlokognatsk.earking_out.core.ports.DI;
import javafx.beans.property.SetProperty;
import javafx.beans.property.SimpleSetProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;

public class PianoKeyboard extends Region {

    public final PianoKeyboardMode mode;
    protected final Map<Byte, PianoKey> allPianoKeys;

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

        var selectedKeys = initSelectedKeys(selectedKeyNumbers);
        this.selectedKeys = new SimpleSetProperty<>(selectedKeys);

        addPianoKeys();
    }

    private Map<Byte, PianoKey> buildPianoKeys() {
        var allPianoKeys = new HashMap<Byte, PianoKey>(Invariants.PIANO_KEYS_NUMBER);

        var pianoKeysBuilder = createPianoKeysBuilder();

        PianoKey pianoKey;
        for (Byte i = 0; pianoKeysBuilder.hasNext(); i++) {
            pianoKey = pianoKeysBuilder.next();
            addEventHandlers(pianoKey);
            allPianoKeys.put(pianoKey.keyNumber, pianoKey);
        }
        return allPianoKeys;
    }

    private PianoKeysBuilder createPianoKeysBuilder() {
        var keySoundsService = DI.get(KeySoundsService.class);
        var keySounds = keySoundsService.getMap();
        var pianoKeysBuilder = new PianoKeysBuilder(getWidth(), getHeight(), keySounds);
        return pianoKeysBuilder;
    }

    private void addEventHandlers(final PianoKey pianoKey) {
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
            throw new IllegalStateException("several keys were selected, although only one key was supposed to be selected");
        }
        var selectedKeysIterator = selectedKeys.getValue().iterator();
        var selectedPianoKeyNumber = selectedKeysIterator.next();

        var oldSelectedPianoKey = allPianoKeys.get(selectedPianoKeyNumber);
        selectedKeys.remove(selectedPianoKeyNumber);

        oldSelectedPianoKey.toggleSelection();
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
            throw new IllegalStateException("PianoKeyboard keys cannot be selected in this mode");
        }
        if (mode == PianoKeyboardMode.ONE_KEY_SELECT && keyNumbers.length > 1) {
            throw new IllegalStateException("this mode does not allow selecting more than 1 key");
        }
    }

    private boolean modeAllowsSelecting() {
        return !ArrayUtils.contains(new PianoKeyboardMode[] { PianoKeyboardMode.ONE_KEY_TOUCH }, mode);
    }

    private void addPianoKeys() {
        var dichotomizedPianoKeys = PianoKeysHelper.dichotomize(allPianoKeys);
        var whitePianoKeys = dichotomizedPianoKeys[PianoKeysHelper.WHITE_KEYS];
        var blackPianoKeys = dichotomizedPianoKeys[PianoKeysHelper.BLACK_KEYS];

        // the intricacies of javafx view require white keys to be added first in order to display black keys in front of (above) the white keys. probably more reasonable way exists, but that's frontender's bread
        var children = getChildren();
        children.addAll(whitePianoKeys);
        children.addAll(blackPianoKeys);
    }
}

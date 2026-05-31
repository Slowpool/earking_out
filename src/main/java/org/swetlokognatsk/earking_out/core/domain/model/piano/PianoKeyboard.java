package org.swetlokognatsk.earking_out.core.domain.model.piano;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
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

public class PianoKeyboard {
    public final PianoKeyboardMode mode;
    protected final Map<Byte, PianoKey> pianoKeys;
    protected final Set<PianoKey> selectedKeys = new HashSet<>();

    public byte[] getSelectedKeyNumbers() {
        var ByteSelectedKeys = this.selectedKeys.stream().map(pianoKey -> (byte) pianoKey.keyNumber).toArray(Byte[]::new);
        var byteSelectedKeys = ArrayUtils.toPrimitive(ByteSelectedKeys);
        return byteSelectedKeys;
    }

    protected PianoKey getPianoKey(byte keyNumber) {
        var ByteKeyNumber = Byte.valueOf(keyNumber);
        var pianoKey = pianoKeys.get(ByteKeyNumber);
        return pianoKey;
    }

    public PianoKeyboard(final PianoKeyboardMode mode) {
        this(mode, new byte[0]);
    }

    public PianoKeyboard(final PianoKeyboardMode mode, final byte[] selectedKeyNumbers) {
        this.mode = mode;

        pianoKeys = buildPianoKeys(selectedKeyNumbers);
        // TODO i decided to move this init logic to buildPianoKeys
        // var selectedKeys = initSelectedKeys(selectedKeyNumbers);
    }

    private Map<Byte, PianoKey> buildPianoKeys(final byte[] selectedKeyNumbers) {
        // TODO
        final var pianoKeys = new HashMap<Byte, PianoKey>(Invariants.PIANO_KEYS_NUMBER);

        PianoKeyMode pianoKeyMode = switch (this.mode) {
        case ONE_KEY_TOUCH -> PianoKeyMode.TOUCH;
        case ONE_KEY_TOUCH -> PianoKeyMode.SELECT;
        default -> throw new IllegalArgumentException("unknown pianoKeyboard mode");
        };

        PianoKeysHelper.forEachKey((Byte keyNumber) -> {
            var pianoKey = PianoKeysFactory.create(keyNumber, pianoKeyMode);
            pianoKeys.put(keyNumber, pianoKey);
        });
        return pianoKeys;
    }

    // // TODO yet dunno how to use it
    // protected void selectOneOfSeveralKeys(PianoKey pianoKey) {
    //     pianoKey.playSound();

    //     toggleSelection(pianoKey);
    // }

    // protected void selectOneKey(PianoKey pianoKey) {
    //     pianoKey.playSound();

    //     if (pianoKey.isSelected()) {
    //         return;
    //     }
    //     tryUnselectPreviouslySelectedKey();
    //     toggleSelection(pianoKey);
    // }

    /**
     * This method supposes that only one key was selected.
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

        var oldSelectedPianoKey = pianoKeys.get(selectedPianoKeyNumber);
        selectedKeys.remove(selectedPianoKeyNumber);

        oldSelectedPianoKey.toggleSelection();
    }

    protected ObservableSet<Byte> initSelectedKeys(byte[] keyNumbers) {
        validateKeyNumbersToSelect(keyNumbers);

        var boxedKeyNumbers = ArrayUtils.toObject(keyNumbers);
        var set = new HashSet<Byte>(Arrays.asList(boxedKeyNumbers));
        var observableSet = FXCollections.observableSet(set);

        boolean shouldBeSelected;
        for (var pianoKey : pianoKeys.values()) {
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

    public void touchKey(byte keyNumber) {
        pressKey(keyNumber);
        releaseKey();
    }

    public void pressKey(byte keyNumber) {
        var pianoKey = getPianoKey(keyNumber);
        pianoKey.press();
    }

    public void releaseKey() {
        
    }
}

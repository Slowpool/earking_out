package org.swetlokognatsk.earking_out.core.domain.model.piano;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang3.ArrayUtils;
import org.swetlokognatsk.earking_out.app.desktop.helpers.PianoKeysHelper;
import org.swetlokognatsk.earking_out.core.domain.model.music.Invariants;

public class PianoKeyboard {
    public final PianoKeyboardMode mode;
    protected final Map<Byte, PianoKey> pianoKeys;
    protected final Set<PianoKey> selectedKeys = new HashSet<>();
    protected PianoKey pressedKey;

    public byte[] getSelectedKeyNumbers() {
        var ByteSelectedKeys = this.selectedKeys.stream().map(pianoKey -> (byte) pianoKey.keyNumber).toArray(Byte[]::new);
        var byteSelectedKeys = ArrayUtils.toPrimitive(ByteSelectedKeys);
        return byteSelectedKeys;
    }

    protected PianoKey getPianoKey(byte keyNumber) {
        var ByteKeyNumber = Byte.valueOf(keyNumber);
        var pianoKey = pianoKeys.get(ByteKeyNumber);

        if (pianoKey == null) {
            throw new IllegalArgumentException("there is no such a piano key: " + keyNumber);
        }
        return pianoKey;
    }

    final protected boolean isSelectMode() {
        return mode.isSelectMode();
    }

    final protected boolean isTouchMode() {
        return mode.isTouchMode();
    }

    public PianoKeyboard(final PianoKeyboardMode mode) {
        this(mode, new byte[0]);
    }

    public PianoKeyboard(final PianoKeyboardMode mode, final byte[] selectedKeyNumbers) {
        this.mode = mode;

        pianoKeys = buildPianoKeys(selectedKeyNumbers);
    }

    private Map<Byte, PianoKey> buildPianoKeys(final byte[] selectedKeyNumbers) {
        validateKeyNumbersToSelect(selectedKeyNumbers);

        final var pianoKeys = new HashMap<Byte, PianoKey>(Invariants.PIANO_KEYS_NUMBER);
        final PianoKeyMode pianoKeyMode = getPianoKeyMode();

        PianoKeysHelper.forEachKey((Byte keyNumber) -> {
            final boolean isSelected = ArrayUtils.contains(selectedKeyNumbers, (byte) keyNumber);

            final var pianoKey = PianoKeysFactory.create(keyNumber, pianoKeyMode, isSelected);
            pianoKeys.put(keyNumber, pianoKey);

            tryAddAsSelected(pianoKey);
        });
        return pianoKeys;
    }

    protected void validateKeyNumbersToSelect(byte[] keyNumbers) {
        if (!isSelectMode() && ArrayUtils.isNotEmpty(keyNumbers)) {
            throw new IllegalArgumentException("PianoKeyboard keys cannot be selected in this mode");
        }
        if (mode == PianoKeyboardMode.ONE_KEY_SELECT && keyNumbers.length > 1) {
            throw new IllegalArgumentException("this mode does not allow selecting more than 1 key");
        }
    }

    protected PianoKeyMode getPianoKeyMode() {
        PianoKeyMode pianoKeyMode;
        if (isSelectMode()) {
            pianoKeyMode = PianoKeyMode.SELECT;
        } else if (isTouchMode()) {
            pianoKeyMode = PianoKeyMode.TOUCH;
        } else {
            throw new RuntimeException("unknown pianoKeyboard mode");
        }

        return pianoKeyMode;
    }

    protected void tryAddAsSelected(final PianoKey pianoKey) {
        if (pianoKey.getIsSelected()) {
            selectedKeys.add(pianoKey);
        }
    }

    public void touchKey(byte keyNumber) {
        pressKey(keyNumber);
        releaseKey();
    }

    public void pressKey(byte keyNumber) {
        validatePianoKeyToPress(keyNumber);

        // TODO refactoring (otherwise current method will become cluttered in the end)
        if (mode == PianoKeyboardMode.ONE_KEY_SELECT) {
            if (moreThanOnePianoKeyIsSelected()) {
                throw new IllegalStateException("several keys were selected in one key select mode");
            } else if (onePianoKeyIsSelected()) {
                var selectedPianoKey = selectedKeys.iterator().next();
                selectedPianoKey.setIsSelected(false);
                selectedKeys.remove(selectedPianoKey);
            }
        }

        var pianoKey = getPianoKey(keyNumber);
        pianoKey.press();
        selectedKeys.add(pianoKey);
        pressedKey = pianoKey;
    }

    protected boolean moreThanOnePianoKeyIsSelected() {
        return selectedKeys.size() > 1;
    }

    protected boolean onePianoKeyIsSelected() {
        return selectedKeys.size() == 1;
    }

    protected void validatePianoKeyToPress(byte keyNumber) {
        if (pressedKey != null) {
            throw new IllegalStateException("another key is already pressed");
        }

        try {
            getPianoKey(keyNumber);
        } catch (IllegalArgumentException e) {
            throw new IndexOutOfBoundsException("there is no such a piano key");
        }
    }

    public void releaseKey() {
        validatePianoKeyToRelease();

        pressedKey.release();
        if (isTouchMode()) {
            selectedKeys.remove(pressedKey);
        }

        pressedKey = null;
    }

    protected void validatePianoKeyToRelease() {
        if (pressedKey == null) {
            throw new IllegalStateException("there is no pressed key on piano keyboard");
        }
    }
}

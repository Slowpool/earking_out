package org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang3.ArrayUtils;
import org.swetlokognatsk.earking_out.app.desktop.helpers.PianoKeysHelper;
import org.swetlokognatsk.earking_out.core.domain.model.base.Aggregate;
import org.swetlokognatsk.earking_out.core.domain.model.music.Invariants;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKey;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyMode;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeysFactory;
import org.swetlokognatsk.earking_out.core.ports.DI;

public final class PianoKeyboardAggregate extends Aggregate<PianoKeyboardId> {
    private static final long serialVersionUID = 1L;

    // TODO make all variables immutable for public read-only aggregate state
    protected final PianoKeyboardMode mode;
    protected final Map<PianoKeyNumber, PianoKey> pianoKeys;
    protected final Set<PianoKey> selectedKeys = new HashSet<>();
    protected PianoKey pressedKey;

    public final PianoKeyboardId getPianoKeyboardId() {
        return getId();
    }

    public final PianoKeyboardMode getMode() {
        return mode;
    }

    public final Map<PianoKeyNumber, PianoKey> getPianoKeys() {
        return pianoKeys;
    }

    public final PianoKey getPianoKey(final PianoKeyNumber keyNumber) {
        var pianoKey = pianoKeys.get(keyNumber);
        if (pianoKey == null) {
            throw new IllegalArgumentException("such a pianoKey is not found: " + keyNumber);
        }
        return pianoKey;
    }

    public PianoKeyNumber[] getSelectedKeyNumbers() {
        var selectedKeys = this.selectedKeys.stream().map(pianoKey -> pianoKey.keyNumber).toArray(PianoKeyNumber[]::new);
        return selectedKeys;
    }

    public final PianoKeyNumber getPressedPianoKeyNumber() {
        return pressedKey == null ? null : pressedKey.keyNumber;
    }

    protected boolean isSelectMode() {
        return mode.isSelectMode();
    }

    protected boolean isTouchMode() {
        return mode.isTouchMode();
    }

    public PianoKeyboardAggregate(final PianoKeyboardId id, final PianoKeyNumber[] selectedKeyNumbers) {
        super(id);
        this.mode = getModeById(id);

        pianoKeys = buildPianoKeys(selectedKeyNumbers);
    }

    protected static PianoKeyboardMode getModeById(final PianoKeyboardId id) {
        return switch (id) {
        case ROOT_NOTE_PICKER -> PianoKeyboardMode.ONE_KEY_SELECT;
        case PERFECT_PITCH_NOTES_PICKER -> PianoKeyboardMode.SEVERAL_KEYS_SELECT;
        case PERFECT_PITCH_NOTES_GUESSING -> PianoKeyboardMode.ONE_KEY_TOUCH;
        default -> throw new RuntimeException("unknown piano keyboard id: " + id);
        };
    }

    private Map<PianoKeyNumber, PianoKey> buildPianoKeys(final PianoKeyNumber[] selectedKeyNumbers) {
        validateKeyNumbersToSelect(selectedKeyNumbers);

        final var pianoKeys = new HashMap<PianoKeyNumber, PianoKey>(Invariants.PIANO_KEYS_NUMBER);
        final PianoKeyMode pianoKeyMode = getPianoKeyMode();
        // TODO it seems awkward to get factory from DI here
        var pianoKeysFactory = DI.get(PianoKeysFactory.class);

        PianoKeysHelper.forEachKey((PianoKeyNumber keyNumber) -> {
            final boolean isSelected = ArrayUtils.contains(selectedKeyNumbers, keyNumber);

            final var pianoKey = pianoKeysFactory.create(keyNumber, pianoKeyMode, isSelected);
            pianoKeys.put(keyNumber, pianoKey);

            tryAddAsSelected(pianoKey);
        });
        return pianoKeys;
    }

    protected void validateKeyNumbersToSelect(final PianoKeyNumber[] keyNumbers) {
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

    public void touchKey(final PianoKeyNumber keyNumber) {
        pressKey(keyNumber);
        releaseKey();
    }

    public void pressKey(final PianoKeyNumber keyNumber) {
        validatePianoKeyToPress(keyNumber);

        updateOtherKeysState();
        var pianoKey = getPianoKey(keyNumber);
        pianoKey.press();
        applySelectingLogicAfterPress(pianoKey);

        pressedKey = pianoKey;
    }

    protected void validatePianoKeyToPress(final PianoKeyNumber keyNumber) {
        if (pressedKey != null) {
            throw new IllegalStateException("another key is already pressed");
        }

        try {
            getPianoKey(keyNumber);
        } catch (IllegalArgumentException e) {
            throw new IndexOutOfBoundsException("there is no such a piano key");
        }
    }

    /**
     * e.g. if it's ONE_KEY_SELECT mode, the previously selected key will be
     * unselected.
     */
    protected void updateOtherKeysState() {
        if (mode == PianoKeyboardMode.ONE_KEY_SELECT) {
            if (moreThanOnePianoKeyIsSelected()) {
                throw new IllegalStateException("several keys were selected in one key select mode");
                // TODO what if the same key is pressed?
            } else if (onePianoKeyIsSelected()) {
                unselectPressedKey();
            }
        }
    }

    protected boolean moreThanOnePianoKeyIsSelected() {
        return selectedKeys.size() > 1;
    }

    protected boolean onePianoKeyIsSelected() {
        return selectedKeys.size() == 1;
    }

    protected void applySelectingLogicAfterPress(final PianoKey pianoKey) {
        if (pianoKey.getIsSelected()) {
            switch (mode) {
            case ONE_KEY_SELECT:
                throw new RuntimeException("this key was supposed to already be unselected in `updateOtherKeysState` method");
            case ONE_KEY_TOUCH:
                throw new IllegalStateException("pressed key cannot be already selected in one key touch mode");
            case SEVERAL_KEYS_SELECT:
                unselectKey(pianoKey);
                break;
            default:
                throw new RuntimeException("unknown mode: " + mode);
            }
        } else {
            switch (mode) {
            case ONE_KEY_SELECT:
            case ONE_KEY_TOUCH:
            case SEVERAL_KEYS_SELECT:
                selectKey(pianoKey);
                break;
            default:
                throw new RuntimeException("unknown mode: " + mode);
            }
        }
    }

    protected void unselectPressedKey() {
        var selectedPianoKey = selectedKeys.iterator().next();
        unselectKey(selectedPianoKey);
    }

    public void releaseKey() {
        validatePianoKeyToRelease();

        pressedKey.release();
        if (isTouchMode()) {
            selectedKeys.remove(pressedKey);
        }
        applySelectingLogicAfterRelease(pressedKey);

        pressedKey = null;
    }

    protected void validatePianoKeyToRelease() {
        if (pressedKey == null) {
            throw new IllegalStateException("there is no pressed key on piano keyboard");
        }
    }

    // TODO test it (idk how it turned out to be not tested)
    protected void applySelectingLogicAfterRelease(final PianoKey pianoKey) {
        if (pianoKey.getIsSelected()) {
            switch (mode) {
            case ONE_KEY_SELECT:
                break;
            case ONE_KEY_TOUCH:
                unselectKey(pianoKey);
                break;
            case SEVERAL_KEYS_SELECT:
                break;
            default:
                throw new RuntimeException("unknown mode: " + mode);
            }
        } else {
            switch (mode) {
            case ONE_KEY_SELECT:
            case ONE_KEY_TOUCH:
                throw new RuntimeException("this key was supposed to be selected after press");
            case SEVERAL_KEYS_SELECT:
                break;
            default:
                throw new RuntimeException("unknown mode: " + mode);
            }
        }
    }

    protected void selectKey(final PianoKey pianoKey) {
        selectedKeys.add(pianoKey);
        pianoKey.select();
    }

    protected void unselectKey(final PianoKey pianoKey) {
        selectedKeys.remove(pianoKey);
        pianoKey.unselect();
    }
}

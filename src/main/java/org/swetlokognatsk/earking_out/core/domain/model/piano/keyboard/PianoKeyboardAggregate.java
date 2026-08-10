package org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import org.apache.commons.lang3.ArrayUtils;
import org.swetlokognatsk.earking_out.core.domain.model.base.Aggregate;
import org.swetlokognatsk.earking_out.core.domain.model.base.AggregateRoot;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKey;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyMode;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeysFactory;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.piano.key.PianoKeyDTO;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.piano.key.PianoKeyDTOAssembler;
import static org.swetlokognatsk.earking_out.core.domain.model.music.Constants.*;

public final class PianoKeyboardAggregate extends AggregateRoot<PianoKeyboardId> {
    private static final long serialVersionUID = 1L;

    private final PianoKeyboardMode mode;
    private final Map<PianoKeyNumber, PianoKey> pianoKeys;
    private final Set<PianoKeyNumber> selectedKeys = new HashSet<>();

    private PianoKey pressedKey;

    public final PianoKeyboardId getPianoKeyboardId() {
        return getId();
    }

    public final PianoKeyboardMode getMode() {
        return mode;
    }

    public final Map<PianoKeyNumber, PianoKeyDTO> getPianoKeys() {
        return PianoKeyDTOAssembler.assemble(pianoKeys);
    }

    private final PianoKey getPianoKeyEntity(final PianoKeyNumber keyNumber) {
        var pianoKey = pianoKeys.get(keyNumber);
        if (pianoKey == null) {
            throw new IllegalArgumentException("such a pianoKey is not found: " + keyNumber);
        }
        return pianoKey;
    }

    public final PianoKeyDTO getPianoKey(final PianoKeyNumber keyNumber) {
        var pianoKey = getPianoKeyEntity(keyNumber);
        var pianoKeyDto = PianoKeyDTOAssembler.assemble(pianoKey);
        return pianoKeyDto;
    }

    public PianoKeyNumber[] getSelectedKeyNumbers() {
        return selectedKeys.toArray(PianoKeyNumber[]::new);
    }

    public final PianoKeyNumber getPressedPianoKeyNumber() {
        return pressedKey == null ? null : pressedKey.keyNumber;
    }

    private boolean isSelectMode() {
        return mode.isSelectMode();
    }

    private boolean isTouchMode() {
        return mode.isTouchMode();
    }

    public PianoKeyboardAggregate(final PianoKeyboardId id, final PianoKeyNumber[] selectedKeyNumbers, final PianoKeysFactory pianoKeysFactory) {
        super(id);

        Objects.requireNonNull(selectedKeyNumbers);

        mode = getModeById(id);
        pianoKeys = buildPianoKeys(pianoKeysFactory, selectedKeyNumbers);
    }

    private static PianoKeyboardMode getModeById(final PianoKeyboardId id) {
        return switch (id) {
        case AUDIO_PERFECT_PITCH_ROOT_NOTE_PICKER -> PianoKeyboardMode.ONE_KEY_SELECT;
        case AUDIO_PERFECT_PITCH_NOTES_PICKER -> PianoKeyboardMode.SEVERAL_KEYS_SELECT;
        case AUDIO_PERFECT_PITCH_NOTES_GUESSING -> PianoKeyboardMode.ONE_KEY_TOUCH;
        default -> throw new RuntimeException("unknown piano keyboard id: " + id);
        };
    }

    private Map<PianoKeyNumber, PianoKey> buildPianoKeys(final PianoKeysFactory pianoKeysFactory, final PianoKeyNumber[] selectedKeyNumbers) {
        validateKeyNumbersToSelect(selectedKeyNumbers);

        final var pianoKeys = new HashMap<PianoKeyNumber, PianoKey>(PIANO_KEYS_NUMBER);
        final PianoKeyMode pianoKeyMode = getPianoKeyMode();

        PianoKeyNumber.forEachKey((PianoKeyNumber keyNumber) -> {
            final boolean isSelected = ArrayUtils.contains(selectedKeyNumbers, keyNumber);

            final var pianoKey = pianoKeysFactory.create(keyNumber, pianoKeyMode, isSelected);
            pianoKeys.put(keyNumber, pianoKey);

            tryAddAsSelected(pianoKey);
        });
        return pianoKeys;
    }

    private void validateKeyNumbersToSelect(final PianoKeyNumber[] keyNumbers) {
        if (!isSelectMode() && ArrayUtils.isNotEmpty(keyNumbers)) {
            throw new IllegalArgumentException("PianoKeyboard keys cannot be selected in this mode");
        }
        if (mode == PianoKeyboardMode.ONE_KEY_SELECT && keyNumbers.length > 1) {
            throw new IllegalArgumentException("this mode does not allow selecting more than 1 key");
        }
    }

    private PianoKeyMode getPianoKeyMode() {
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

    private void tryAddAsSelected(final PianoKey pianoKey) {
        if (pianoKey.getIsSelected()) {
            selectedKeys.add(pianoKey.keyNumber);
        }
    }

    public void touchKey(final PianoKeyNumber keyNumber) {
        pressKey(keyNumber);
        releaseKey();
    }

    public void pressKey(final PianoKeyNumber keyNumber) {
        validatePianoKeyToPress(keyNumber);

        updateOtherKeysState();
        var pianoKey = getPianoKeyEntity(keyNumber);
        pianoKey.press();
        applySelectingLogicAfterPress(pianoKey);

        pressedKey = pianoKey;

        var pianoKeyPressedEvent = getDomainEventsFactory().createPianoKeyPressedEvent(getId(), keyNumber);
        addEvent(pianoKeyPressedEvent);
    }

    private void validatePianoKeyToPress(final PianoKeyNumber keyNumber) {
        if (pressedKey != null) {
            throw new IllegalStateException("another key is already pressed");
        }

        try {
            getPianoKeyEntity(keyNumber);
        } catch (IllegalArgumentException e) {
            throw new IndexOutOfBoundsException("there is no such a piano key");
        }
    }

    /**
     * e.g. if it's ONE_KEY_SELECT mode, the previously selected key will be
     * unselected.
     */
    private void updateOtherKeysState() {
        if (mode == PianoKeyboardMode.ONE_KEY_SELECT) {
            if (moreThanOnePianoKeyIsSelected()) {
                throw new IllegalStateException("several keys were selected in one key select mode");
            } else if (onePianoKeyIsSelected()) {
                unselectTheOnlySelectedKey();
            }
        }
    }

    private boolean moreThanOnePianoKeyIsSelected() {
        return selectedKeys.size() > 1;
    }

    private boolean onePianoKeyIsSelected() {
        return selectedKeys.size() == 1;
    }

    private void applySelectingLogicAfterPress(final PianoKey pianoKey) {
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

    private void unselectTheOnlySelectedKey() {
        var selectedPianoKeyNumber = getTheOnlySelectedKey();
        var selectedPianoKey = getPianoKeyEntity(selectedPianoKeyNumber);
        unselectKey(selectedPianoKey);
    }

    private PianoKeyNumber getTheOnlySelectedKey() {
        try {
            // iterator.next is aaaaaaawwwwwwwkkkkkkkwwwwwwwaaaaaaarrrrrrrddddddd. upd: there's no other ways
            return selectedKeys.iterator().next();
        } catch (NoSuchElementException e) {
            throw new IllegalStateException("there're no elements in selectedKeys");
        }
    }

    public void releaseKey() {
        validatePianoKeyToRelease();

        pressedKey.release();
        applySelectingLogicAfterRelease(pressedKey);

        pressedKey = null;
    }

    private void validatePianoKeyToRelease() {
        if (pressedKey == null) {
            throw new IllegalStateException("there is no pressed key on piano keyboard");
        }
    }

    private void applySelectingLogicAfterRelease(final PianoKey pianoKey) {
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

    private void selectKey(final PianoKey pianoKey) {
        selectedKeys.add(pianoKey.keyNumber);
        pianoKey.select();
    }

    private void unselectKey(final PianoKey pianoKey) {
        selectedKeys.remove(pianoKey.keyNumber);
        pianoKey.unselect();
    }
}

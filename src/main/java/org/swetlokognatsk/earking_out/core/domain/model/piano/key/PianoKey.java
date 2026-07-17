package org.swetlokognatsk.earking_out.core.domain.model.piano.key;

import java.util.Objects;
import org.swetlokognatsk.earking_out.core.domain.model.base.Entity;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardSoundMode;
import org.swetlokognatsk.earking_out.core.domain.services.domain.piano.PianoKeyColorService;
import org.swetlokognatsk.earking_out.core.ports.piano.PianoKeySoundsPlayer;

public final class PianoKey extends Entity<PianoKeyNumber> {
    private static final long serialVersionUID = 1L;

    public final PianoKeyNumber keyNumber;
    public final PianoKeyColor color;
    public final PianoKeyMode mode;
    private boolean isSelected;
    private PianoKeyboardSoundMode soundMode;
    private boolean isPressed;

    private final PianoKeySoundsPlayer pianoKeySoundsPlayer;

    public boolean getIsSelected() {
        return isSelected;
    }

    protected void setIsSelected(final boolean isSelected) {
        this.isSelected = isSelected;
    }

    public void setSoundMode(final PianoKeyboardSoundMode soundMode) {
        this.soundMode = soundMode;
    }

    protected PianoKeyboardSoundMode getSoundMode() {
        return soundMode;
    }

    public boolean getIsPressed() {
        return isPressed;
    }

    protected void setIsPressed(final boolean isPressed) {
        this.isPressed = isPressed;
    }

    // TODO refactoring SoundPlayer via PianoKeyPressed domain event
    // injecting SoundPlayer is DDD pure-domain-entity violation. this approach is justified by redandant complexity the domain event would add here. also testability is simpler. also SoundPlayer is not supposed to do any write actions, only read-only ones. proper alternative to make sound on piano key press is via PianoKeyPressed domain event handler.
    public PianoKey(final PianoKeyNumber keyNumber, final PianoKeyMode mode, final boolean isSelected, final PianoKeyboardSoundMode soundMode, final PianoKeyColorService colorService, final PianoKeySoundsPlayer pianoKeySoundsPlayer) {
        super(keyNumber);

        Objects.requireNonNull(colorService);

        this.keyNumber = Objects.requireNonNull(keyNumber);
        this.mode = Objects.requireNonNull(mode);
        setIsSelected(isSelected);
        setSoundMode(Objects.requireNonNull(soundMode));

        this.color = colorService.getColor(keyNumber);
        this.pianoKeySoundsPlayer = Objects.requireNonNull(pianoKeySoundsPlayer);

    }

    public void press() {
        validatePressing();

        setIsPressed(true);
        playSound();
    }

    protected void validatePressing() {
        switch (mode) {
        case TOUCH:
            validatePressingInTouchMode();
            break;
        case SELECT:
            validatePressingInSelectMode();
            break;
        default:
            throw new RuntimeException("unkown piano key mode");
        }
    }

    protected void validatePressingInTouchMode() {
        if (isPressed) {
            throw new IllegalStateException("this key is already pressed");
        }
    }

    protected void validatePressingInSelectMode() {
    }

    public void release() {
        if (!isPressed) {
            throw new IllegalStateException("piano key that is not pressed so it cannot be released");
        }

        this.setIsPressed(false);
    }

    protected void playSound() {
        if (soundMode == PianoKeyboardSoundMode.USUAL) {
            pianoKeySoundsPlayer.stopAndPlay(keyNumber);
        }
    }

    protected void stopSound() {
        if (soundMode == PianoKeyboardSoundMode.USUAL) {
            pianoKeySoundsPlayer.stop(keyNumber);
        }
    }

    public void select() {
        validateSelecting();
        this.setIsSelected(true);
    }

    public void unselect() {
        validateUnselecting();
        this.setIsSelected(false);
    }

    protected void validateSelecting() {
        if (isSelected) {
            throw new IllegalStateException("pianoKey is already selected");
        }
    }

    protected void validateUnselecting() {
        if (!isSelected) {
            throw new IllegalStateException("pianoKey is already unselected");
        }
    }
}

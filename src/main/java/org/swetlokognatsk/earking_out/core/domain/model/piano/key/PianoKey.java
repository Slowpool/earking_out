package org.swetlokognatsk.earking_out.core.domain.model.piano.key;

import org.swetlokognatsk.earking_out.app.desktop.services.SoundPlayerService;
import org.swetlokognatsk.earking_out.core.domain.model.base.Entity;
import org.swetlokognatsk.earking_out.core.domain.services.domain.piano.PianoKeyColorService;

public final class PianoKey extends Entity<Byte> {
    private static final long serialVersionUID = 1L;

    public final byte keyNumber;
    public final PianoKeyColor color;
    private final PianoKeyMode mode;
    private boolean isSelected;
    private boolean isPressed;

    public boolean getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(boolean value) {
        isSelected = value;
    }

    protected void setIsPressed(final boolean value) {
        isPressed = value;
    }

    public boolean getIsPressed() {
        return isPressed;
    }

    public PianoKey(final byte keyNumber, final PianoKeyMode mode, final boolean isSelected, final PianoKeyColorService colorService) {
        super(keyNumber);
        this.keyNumber = keyNumber;
        this.mode = mode;
        this.color = colorService.getColor(keyNumber);

        setIsSelected(isSelected);
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
        if (getIsPressed()) {
            throw new IllegalStateException("this key is already pressed");
        }
    }

    protected void validatePressingInSelectMode() {

    }

    public void release() {
        if (!getIsPressed()) {
            throw new IllegalStateException("piano key that is not pressed so it cannot be released");
        }

        this.setIsPressed(false);
    }

    // TODO yank
    protected void playSound() {
        // soundPlayer.stopAndPlay();
    }

    protected void stopSound() {
        // soundPlayer.stop();
    }
}

package org.swetlokognatsk.earking_out.core.domain.model.piano;

import org.swetlokognatsk.earking_out.app.desktop.services.SoundPlayerService;
import org.swetlokognatsk.earking_out.core.domain.model.base.Entity;
import org.swetlokognatsk.earking_out.core.domain.services.piano.PianoKeyColorService;

public final class PianoKey extends Entity {
    public final byte keyNumber;
    public final PianoKeyColor color;
    private final SoundPlayerService soundPlayer;
    private final PianoKeyMode mode;
    private boolean isSelected;

    public String getId() {
        return String.valueOf(keyNumber);
    }

    public boolean getIsSelected() {
        return isSelected;
    }

    protected void setIsSelected(boolean value) {
        isSelected = value;
    }

    public PianoKey(final byte keyNumber, final PianoKeyMode mode, final boolean isSelected, final PianoKeyColorService colorService, final SoundPlayerService soundPlayer) {
        this.keyNumber = keyNumber;
        this.mode = mode;
        this.color = colorService.getColor(keyNumber);
        this.soundPlayer = soundPlayer;

        setIsSelected(isSelected);
    }

    public void press() {
        validatePressing();

        this.setIsSelected(true);
        this.playSound();
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
        if (getIsSelected()) {
            throw new IllegalStateException("this key is already pressed");
        }
    }

    protected void validatePressingInSelectMode() {

    }

    public void release() {
        if (!getIsSelected()) {
            throw new IllegalStateException("piano key is already released");
        }

        switch (mode) {
        case TOUCH:
            this.setIsSelected(false);
            break;
        case SELECT:
            break;
        default:
            throw new RuntimeException("unkown PianoKeyMode");
        }
    }

    // public void toggleSelection() {
    //     toggleSelection(!isSelected());
    // }

    // public void toggleSelection(boolean value) {
    //     setSelected(value);
    //     toggleColor(value);
    // }

    protected void playSound() {
        soundPlayer.stopAndPlay();
    }

    protected void stopSound() {
        soundPlayer.stop();
    }
}

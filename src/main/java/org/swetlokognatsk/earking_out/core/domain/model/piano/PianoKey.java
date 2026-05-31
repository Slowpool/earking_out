package org.swetlokognatsk.earking_out.core.domain.model.piano;

import org.swetlokognatsk.earking_out.app.desktop.services.SoundPlayerService;
import org.swetlokognatsk.earking_out.core.domain.services.piano.PianoKeyColorService;
import javafx.scene.layout.Background;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

public class PianoKey {
    public final byte keyNumber;
    public final PianoKeyColor color;
    private final SoundPlayerService soundPlayer;
    private final PianoKeyMode mode;
    private boolean isSelected;

    public PianoKey(final byte keyNumber, final PianoKeyMode mode, final PianoKeyColorService colorService, final SoundPlayerService soundPlayer) {
        this.keyNumber = keyNumber;
        this.mode = mode;
        this.color = colorService.getColor(keyNumber);
        this.soundPlayer = soundPlayer;

    }

    public boolean getIsSelected() {
        return isSelected;
    }

    protected void setIsSelected(boolean value) {
        isSelected = value;
    }

    public void toggleSelection() {
        toggleSelection(!isSelected());
    }

    public void toggleSelection(boolean value) {
        setSelected(value);
        toggleColor(value);
    }

    public void playSound() {
        soundPlayer.stopAndPlay();
    }

    public void stopSound() {
        soundPlayer.stop();
    }
}

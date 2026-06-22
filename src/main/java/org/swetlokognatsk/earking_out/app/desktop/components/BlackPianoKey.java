package org.swetlokognatsk.earking_out.app.desktop.components;

import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

public final class BlackPianoKey extends PianoKey {
    protected static final Background selectedBackground = new Background(new BackgroundFill(Color.rgb(0x00, 0x14, 0xA9), CornerRadii.EMPTY, Insets.EMPTY));
    protected static final Background notSelectedBackground = new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY));

    protected Background getSelectedBackground() {
        return selectedBackground;
    }

    protected Background getNotSelectedBackground() {
        return notSelectedBackground;
    }

    public BlackPianoKey(final PianoKeyNumber keyNumber, final boolean isSelected) {
        super(keyNumber, isSelected);

    }
}

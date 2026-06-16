package org.swetlokognatsk.earking_out.app.desktop.components;

import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

public final class WhitePianoKey extends PianoKey {
    protected static final Background selectedBackground = new Background(new BackgroundFill(Color.rgb(0x00, 0xB4, 0xD8), CornerRadii.EMPTY, Insets.EMPTY));
    protected static final Background notSelectedBackground = new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY));

    protected Background getSelectedBackground() {
        return selectedBackground;
    }

    protected Background getNotSelectedBackground() {
        return notSelectedBackground;
    }

    public WhitePianoKey(final boolean isSelected) {
        super(isSelected);

    }
}

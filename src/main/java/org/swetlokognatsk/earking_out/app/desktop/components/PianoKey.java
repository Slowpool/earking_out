package org.swetlokognatsk.earking_out.app.desktop.components;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

public final class PianoKey extends Button {
    // TODO WHAT are insets and corner radii
    protected static final Background selectedWhiteBackground = new Background(new BackgroundFill(Color.rgb(0x00, 0xB4, 0xD8), CornerRadii.EMPTY, Insets.EMPTY));
    protected static final Background selectedBlackBackground = new Background(new BackgroundFill(Color.rgb(0x00, 0x14, 0xA9), CornerRadii.EMPTY, Insets.EMPTY));
    protected static final Background notSelectedWhiteBackground = new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY));
    protected static final Background notSelectedBlackBackground = new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY));

    public final byte keyNumber;
    private final boolean isWhite;
    private boolean isSelected;

    public boolean isSelected() {
        return isSelected;
    }

    protected void setSelected(boolean value) {
        isSelected = value;
    }

    public PianoKey(final byte keyNumber, final boolean isWhite) {
        // TODO for debug
        // super("" + keyNumber);

        this.keyNumber = keyNumber;
        this.isWhite = isWhite;

        var background = isWhite() ? notSelectedWhiteBackground : notSelectedBlackBackground;
        setBackground(background);

        setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));
    }

    public boolean isWhite() {
        return isWhite;
    }

    public void toggleSelection() {
        toggleSelection(!isSelected());
    }

    public void toggleSelection(boolean value) {
        setSelected(value);
        toggleColor(value);
    }

    protected void toggleColor(boolean newIsSelected) {
        Background background;
        if (newIsSelected) {
            if (isWhite()) {
                background = selectedWhiteBackground;
            } else {
                background = selectedBlackBackground;
            }
        } else {
            if (isWhite()) {
                background = notSelectedWhiteBackground;
            } else {
                background = notSelectedBlackBackground;
            }
        }
        setBackground(background);
    }
}

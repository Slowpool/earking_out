package org.swetlokognatsk.earking_out.app.desktop.components;

import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

public sealed abstract class PianoKey extends Button permits WhitePianoKey, BlackPianoKey {
    protected static final Border border = new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1)));

    public PianoKey(final boolean isSelected) {
        var background = workOutBackground(isSelected);
        setBackground(background);

        setBorder(border);
    }

    protected Background workOutBackground(final boolean isSelected) {
        return isSelected ? getSelectedBackground() : getNotSelectedBackground();
    }

    protected abstract Background getSelectedBackground();

    protected abstract Background getNotSelectedBackground();

    protected void toggleColor(boolean newIsSelected) {
        Background background = newIsSelected ? getSelectedBackground() : getNotSelectedBackground();
        setBackground(background);
    }
}

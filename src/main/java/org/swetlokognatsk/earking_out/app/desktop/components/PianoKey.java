package org.swetlokognatsk.earking_out.app.desktop.components;

import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.piano.key.PianoKeyDTO;
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

    protected final PianoKeyNumber keyNumber;
    protected boolean isSelected;

    public PianoKey(final PianoKeyNumber keyNumber, final boolean isSelected) {
        this.keyNumber = keyNumber;
        this.isSelected = isSelected;

        var background = workOutBackground();
        setBackground(background);

        setBorder(border);
    }

    protected Background workOutBackground() {
        return isSelected ? getSelectedBackground() : getNotSelectedBackground();
    }

    protected abstract Background getSelectedBackground();

    protected abstract Background getNotSelectedBackground();

    protected void select() {
        validateSelecting();
        isSelected = true;
        updateBackground();
    }

    protected void validateSelecting() {
        if (isSelected) {
            var message = String.format("key %d is already selected", keyNumber.value);
            throw new IllegalStateException(message);
        }
    }

    protected void unselect() {
        validateUnselecting();
        isSelected = false;
        updateBackground();
    }

    protected void validateUnselecting() {
        if (!isSelected) {
            var message = String.format("key %d is already unselected", keyNumber.value);
            throw new IllegalStateException(message);
        }
    }

    protected void updateBackground() {
        Background background = isSelected ? getSelectedBackground() : getNotSelectedBackground();
        setBackground(background);
    }

    public void hydrateState(final PianoKeyDTO newState) {
        validateStateToHydrate(newState);

        if (isSelected != newState.isSelected()) {
            if (newState.isSelected()) {
                select();
            } else {
                unselect();
            }
        }
    }

    protected void validateStateToHydrate(final PianoKeyDTO newState) {
        if (!keyNumber.equals(newState.keyNumber())) {
            var message = String.format("pianoKeyNumber does not correspond. component keyNumber: %d, dto keyNumber: %d", keyNumber.value, newState.keyNumber().value);
            throw new IllegalArgumentException(message);
        }
    }
}

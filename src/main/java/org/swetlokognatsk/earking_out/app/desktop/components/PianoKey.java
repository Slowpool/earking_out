package org.swetlokognatsk.earking_out.app.desktop.components;

import javafx.scene.control.Button;

// TODO it's not domain model, but then what is it? or it's domain model?
public final class PianoKey extends Button {
    public final byte keyNumber;
    public boolean isBeingPressed;

    public PianoKey(final byte keyNumber) {
        super("" + keyNumber);
        this.keyNumber = keyNumber;
    }

}

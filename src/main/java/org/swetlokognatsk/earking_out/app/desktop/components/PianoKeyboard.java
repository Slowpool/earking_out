package org.swetlokognatsk.earking_out.app.desktop.components;

import java.util.HashMap;
import java.util.Map;
import org.swetlokognatsk.earking_out.app.desktop.events.piano.PianoKeyPressedEvent;
import org.swetlokognatsk.earking_out.app.desktop.events.piano.PianoKeyReleasedEvent;
import org.swetlokognatsk.earking_out.app.desktop.helpers.PianoKeysHelper;
import org.swetlokognatsk.earking_out.app.desktop.services.PianoKeysBuildersFactory;
import org.swetlokognatsk.earking_out.core.domain.model.music.Invariants;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardId;
import javafx.scene.layout.Region;

public final class PianoKeyboard extends Region {
    public final PianoKeyboardId id;
    protected final Map<Byte, PianoKey> pianoKeys = new HashMap<>(Invariants.PIANO_KEYS_NUMBER);

    public PianoKeyboard(final PianoKeyboardId id, final double width, final double height, final byte[] selectedKeys) {
        this.id = id;

        setHeight(height);
        setWidth(width);

        var pianoKeysBuilder = PianoKeysBuildersFactory.create(getWidth(), getHeight(), selectedKeys);

        PianoKey pianoKey;
        Byte pianoKeyNumber;
        for (Byte i = 0; pianoKeysBuilder.hasNext(); i++) {
            pianoKey = pianoKeysBuilder.next();
            pianoKeyNumber = pianoKeysBuilder.getCurrentKeyNumber();
            addEventHandlers(pianoKey, pianoKeyNumber);
            pianoKeys.put(pianoKeyNumber, pianoKey);
        }

        addPianoKeys();
    }

    public PianoKeyboard(final PianoKeyboardId id, final double width, final double height) {
        this(id, width, height, new byte[0]);
    }

    private void addEventHandlers(final PianoKey pianoKey, final Byte keyNumber) {
        // TODO how 'bout pressing keys via key arrows and space/enter/w?
        pianoKey.setOnMousePressed(e -> {
            var event = new PianoKeyPressedEvent(PianoKeyPressedEvent.PIANO_KEY_PRESSED, id, keyNumber);
            fireEvent(event);
        });

        pianoKey.setOnMouseReleased(e -> {
            var event = new PianoKeyReleasedEvent(PianoKeyReleasedEvent.PIANO_KEY_RELEASED, id, keyNumber);
            fireEvent(event);
        });
    }

    private void addPianoKeys() {
        var dichotomizedPianoKeys = PianoKeysHelper.dichotomize(pianoKeys);
        var whitePianoKeys = dichotomizedPianoKeys[PianoKeysHelper.WHITE_KEYS];
        var blackPianoKeys = dichotomizedPianoKeys[PianoKeysHelper.BLACK_KEYS];

        // the intricacies of javafx view require white keys to be added first in order to display black keys in front of (above) the white keys. probably more reasonable way exists, but that's frontender's bread
        var children = getChildren();
        children.addAll(whitePianoKeys);
        children.addAll(blackPianoKeys);
    }

    public void updateState(final PianoKeyboardState newState) {

    }

    // protected void toggleSelection(PianoKey pianoKey) {
    //     pianoKey.toggleSelection();

    //     if (pianoKey.isSelected()) {
    //         selectedKeys.add(pianoKey.keyNumber);
    //     } else {
    //         selectedKeys.remove(pianoKey.keyNumber);
    //     }
    // }

}

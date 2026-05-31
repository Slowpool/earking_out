package org.swetlokognatsk.earking_out.app.desktop.components;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import org.apache.commons.lang3.ArrayUtils;
import org.swetlokognatsk.earking_out.app.desktop.helpers.PianoKeysHelper;
import org.swetlokognatsk.earking_out.app.desktop.services.KeySoundsService;
import org.swetlokognatsk.earking_out.app.desktop.services.PianoKeysBuilder;
import org.swetlokognatsk.earking_out.core.domain.model.music.Invariants;
import org.swetlokognatsk.earking_out.core.ports.DI;
import javafx.beans.property.SetProperty;
import javafx.beans.property.SimpleSetProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;

public final class PianoKeyboard extends Region {
    // TODO sort everything out after refactoring
    public PianoKeyboard(final double width, final double height) {
        setHeight(height);
        setWidth(width);

        var pianoKeysBuilder = createPianoKeysBuilder();

        PianoKey pianoKey;
        for (Byte i = 0; pianoKeysBuilder.hasNext(); i++) {
            pianoKey = pianoKeysBuilder.next();
            addEventHandlers(pianoKey);
            pianoKeys.put(pianoKey.keyNumber, pianoKey);
        }
    }

    private PianoKeysBuilder createPianoKeysBuilder() {
        var keySoundsService = DI.get(KeySoundsService.class);
        var keySounds = keySoundsService.getMap();
        var pianoKeysBuilder = new PianoKeysBuilder(getWidth(), getHeight(), keySounds);
        return pianoKeysBuilder;
    }

    private void addEventHandlers(final PianoKey pianoKey) {
        var pressedHandler = createMousePressedHandler(pianoKey);
        if (pressedHandler != null) {
            pianoKey.setOnMousePressed(pressedHandler);
        }
        var releasedHandler = createMouseReleasedHandler(pianoKey);
        if (releasedHandler != null) {
            pianoKey.setOnMouseReleased(releasedHandler);
        }
    }

    protected EventHandler<? super MouseEvent> createMousePressedHandler(PianoKey pianoKey) {
        return switch (mode) {
        case SEVERAL_KEYS_SELECT -> e -> {
            selectOneOfSeveralKeys(pianoKey);
        };
        case ONE_KEY_SELECT -> e -> {
            selectOneKey(pianoKey);
        };
        case ONE_KEY_TOUCH -> e -> {
            touchOneKey(pianoKey);
        };
        default -> null;
        };
    }
    
}

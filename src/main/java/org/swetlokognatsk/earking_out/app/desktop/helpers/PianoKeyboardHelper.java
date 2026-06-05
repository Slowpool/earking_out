package org.swetlokognatsk.earking_out.app.desktop.helpers;

import org.swetlokognatsk.earking_out.app.desktop.components.PianoKeyboard;
import org.swetlokognatsk.earking_out.app.desktop.events.piano.PianoKeyPressedEvent;
import org.swetlokognatsk.earking_out.app.desktop.events.piano.PianoKeyReleasedEvent;
import org.swetlokognatsk.earking_out.core.domain.services.app.PianoKeyboardService;
import org.swetlokognatsk.earking_out.core.ports.DI;
import javafx.event.EventHandler;

public final class PianoKeyboardHelper {

    private PianoKeyboardHelper() {
    }

    public static void addPianoKeyEventsHandlers(PianoKeyboard pianoKeyboard) {
        pianoKeyboard.addEventHandler(PianoKeyPressedEvent.PIANO_KEY_PRESSED, createPressKeyHandler());
        pianoKeyboard.addEventHandler(PianoKeyReleasedEvent.PIANO_KEY_RELEASED, createReleaseKeyHandler());
    }

    public static EventHandler<PianoKeyPressedEvent> createReleaseKeyHandler() {
        final var pianoKeyboardService = getPianoKeyboardService();
        return e -> {
            pianoKeyboardService.releaseKey(e.pianoKeyboardId, e.keyNumber);
        };
    }

    public static EventHandler<PianoKeyPressedEvent> createPressKeyHandler() {
        final var pianoKeyboardService = getPianoKeyboardService();
        return e -> {
            pianoKeyboardService.pressKey(e.pianoKeyboardId, e.keyNumber);
        };
    }

    protected static PianoKeyboardService getPianoKeyboardService() {
        return DI.get(PianoKeyboardService.class);
    }
}

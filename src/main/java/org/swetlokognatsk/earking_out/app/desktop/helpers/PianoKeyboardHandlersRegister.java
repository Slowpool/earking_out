package org.swetlokognatsk.earking_out.app.desktop.helpers;

import org.swetlokognatsk.earking_out.app.desktop.components.PianoKeyboard;
import org.swetlokognatsk.earking_out.app.desktop.events.piano.PianoKeyPressedEvent;
import org.swetlokognatsk.earking_out.app.desktop.events.piano.PianoKeyReleasedEvent;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardId;
import org.swetlokognatsk.earking_out.core.domain.services.app.PianoKeyboardService;
import org.swetlokognatsk.earking_out.core.domain.services.app.PuzzleConfigService;
import org.swetlokognatsk.earking_out.core.domain.services.app.PuzzleGuessingService;
import org.swetlokognatsk.earking_out.core.ports.DI;
import javafx.event.EventHandler;

// TODO actually everything this class do is responsibilities violation. ConfigPane must just throw PianoKeyboardKeyPressed event on app level, that's it. so, ConfigPane must be just a dummy view that throws events, whereas the code from this class must be somewhere out of configPane
public final class PianoKeyboardHandlersRegister {

    private PianoKeyboardHandlersRegister() {
    }

    public static void addPianoKeyEventsHandlers(PianoKeyboard pianoKeyboard) {
        pianoKeyboard.addEventHandler(PianoKeyPressedEvent.PIANO_KEY_PRESSED, createPressKeyHandler(pianoKeyboard.id));
        pianoKeyboard.addEventHandler(PianoKeyReleasedEvent.PIANO_KEY_RELEASED, createReleaseKeyHandler(pianoKeyboard.id));
    }

    public static EventHandler<PianoKeyReleasedEvent> createReleaseKeyHandler(final PianoKeyboardId pianoKeyboardId) {
        final var pianoKeyboardService = getPianoKeyboardService();
        return e -> {
            pianoKeyboardService.releaseKey(e.pianoKeyboardId);
        };
    }

    public static EventHandler<PianoKeyPressedEvent> createPressKeyHandler(final PianoKeyboardId pianoKeyboardId) {
        EventHandler<PianoKeyPressedEvent> handler = switch (pianoKeyboardId) {
        case ROOT_NOTE_PICKER -> e -> {
            getConfigService().updatePropertyViaPianoKeyPressing(e.pianoKeyboardId, e.keyNumber);
        };
        case PERFECT_PITCH_NOTES_PICKER -> e -> {
            getConfigService().updatePropertyViaPianoKeyPressing(e.pianoKeyboardId, e.keyNumber);
        };
        case PERFECT_PITCH_NOTES_GUESSING -> e -> {
            getPuzzleGuessingService().guessViaPianoKeyPressing(e.pianoKeyboardId, e.keyNumber);
        };
        default -> throw new RuntimeException("unknown piano keyboard id: " + pianoKeyboardId);
        };
        return handler;
    }

    protected static PianoKeyboardService getPianoKeyboardService() {
        return DI.get(PianoKeyboardService.class);
    }

    protected static PuzzleConfigService getConfigService() {
        return DI.get(PuzzleConfigService.class);
    }

    protected static PuzzleGuessingService getPuzzleGuessingService() {
        return DI.get(PuzzleGuessingService.class);
    }
}

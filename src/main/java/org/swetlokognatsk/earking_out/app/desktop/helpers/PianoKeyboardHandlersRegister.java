package org.swetlokognatsk.earking_out.app.desktop.helpers;

import org.swetlokognatsk.earking_out.app.desktop.components.PianoKeyboard;
import org.swetlokognatsk.earking_out.app.desktop.events.piano.PianoKeyPressedEvent;
import org.swetlokognatsk.earking_out.app.desktop.events.piano.PianoKeyReleasedEvent;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardId;
import org.swetlokognatsk.earking_out.core.domain.services.app.PuzzleConfigService;
import org.swetlokognatsk.earking_out.core.ports.DI;
import org.swetlokognatsk.earking_out.core.ports.piano.PianoKeyboardRepository;
import javafx.event.EventHandler;

// TODO responsibilities violation. ConfigPane must just throw PianoKeyboardKeyPressed event on app level, that's it. so, ConfigPane must be just a dummy view that throws events, whereas the code from this class must be somewhere out of configPane
public final class PianoKeyboardHandlersRegister {

    private PianoKeyboardHandlersRegister() {
    }

    public static void addPianoKeyEventsHandlers(final PianoKeyboard pianoKeyboard) {
        pianoKeyboard.addEventHandler(PianoKeyPressedEvent.PIANO_KEY_PRESSED, createPressKeyHandler(pianoKeyboard));
        pianoKeyboard.addEventHandler(PianoKeyReleasedEvent.PIANO_KEY_RELEASED, createReleaseKeyHandler(pianoKeyboard));
    }

    public static EventHandler<PianoKeyPressedEvent> createPressKeyHandler(final PianoKeyboard pianoKeyboard) {
        EventHandler<PianoKeyPressedEvent> handler = switch (pianoKeyboard.id) {
        case ROOT_NOTE_PICKER -> e -> {
            doAndRefreshView(() -> {
                getConfigService().updatePropertyViaPianoKeyPressing(e.pianoKeyboardId, e.keyNumber);
            }, pianoKeyboard);
        };
        case PERFECT_PITCH_NOTES_PICKER -> e -> {
            doAndRefreshView(() -> {
                getConfigService().updatePropertyViaPianoKeyPressing(e.pianoKeyboardId, e.keyNumber);
            }, pianoKeyboard);
        };
        default -> throw new RuntimeException("unknown piano keyboard id: " + pianoKeyboard.id);
        };
        return handler;
    }

    public static EventHandler<PianoKeyReleasedEvent> createReleaseKeyHandler(final PianoKeyboard pianoKeyboard) {
        EventHandler<PianoKeyReleasedEvent> handler = switch (pianoKeyboard.id) {
        case ROOT_NOTE_PICKER -> e -> {
            doAndRefreshView(() -> {
                getConfigService().releasePianoKey(e.pianoKeyboardId);
            }, pianoKeyboard);
        };
        case PERFECT_PITCH_NOTES_PICKER -> e -> {
            doAndRefreshView(() -> {
                getConfigService().releasePianoKey(e.pianoKeyboardId);
            }, pianoKeyboard);
        };
        default -> throw new RuntimeException("unknown piano keyboard id: " + pianoKeyboard.id);
        };
        return handler;
    }

    protected static PuzzleConfigService getConfigService() {
        return DI.get(PuzzleConfigService.class);
    }

    protected static PianoKeyboardRepository getPianoKeyboardRepository() {
        return DI.get(PianoKeyboardRepository.class);
    }

    protected static void doAndRefreshView(final Runnable action, final PianoKeyboard pianoKeyboard) {
        action.run();
        updatePianoKeyboardView(pianoKeyboard);
    }

    public static void updatePianoKeyboardView(final PianoKeyboard pianoKeyboard) {
        // TODO what is correct approach? this approach is hand-made
        var pianoKeyboardView = getPianoKeyboardRepository().getViewDto(pianoKeyboard.id);
        pianoKeyboard.hydrateState(pianoKeyboardView);
    }
}

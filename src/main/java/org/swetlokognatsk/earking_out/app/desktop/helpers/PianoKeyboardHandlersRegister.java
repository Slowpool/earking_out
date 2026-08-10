package org.swetlokognatsk.earking_out.app.desktop.helpers;

import org.swetlokognatsk.earking_out.app.desktop.components.PianoKeyboard;
import org.swetlokognatsk.earking_out.app.desktop.events.piano.PianoKeyPressedEvent;
import org.swetlokognatsk.earking_out.app.desktop.events.piano.PianoKeyReleasedEvent;
import org.swetlokognatsk.earking_out.core.domain.services.app.PianoKeyboardService;
import org.swetlokognatsk.earking_out.core.domain.services.app.PuzzleConfigService;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.piano.keyboard.PianoKeyboardDTO;
import org.swetlokognatsk.earking_out.core.ports.di.DI;
import org.swetlokognatsk.earking_out.core.ports.piano.PianoKeyboardRepository;
import org.swetlokognatsk.earking_out.core.ports.piano.PianoKeyboardRepository;
import javafx.event.EventHandler;

public final class PianoKeyboardHandlersRegister {

    private final PianoKeyboardRepository pianoKeyboardRepository;

    public PianoKeyboardHandlersRegister(final PianoKeyboardRepository pianoKeyboardRepository) {
        this.pianoKeyboardRepository = pianoKeyboardRepository;
    }

    public void addPianoKeyEventsHandlers(final PianoKeyboard pianoKeyboard) {
        pianoKeyboard.addEventHandler(PianoKeyPressedEvent.PIANO_KEY_PRESSED, createPressKeyHandler(pianoKeyboard));
        pianoKeyboard.addEventHandler(PianoKeyReleasedEvent.PIANO_KEY_RELEASED, createReleaseKeyHandler(pianoKeyboard));
    }

    public EventHandler<PianoKeyPressedEvent> createPressKeyHandler(final PianoKeyboard pianoKeyboard) {
        EventHandler<PianoKeyPressedEvent> handler = switch (pianoKeyboard.id) {
        case AUDIO_PERFECT_PITCH_ROOT_NOTE_PICKER -> e -> {
            doAndRefreshView(() -> {
                getPianoKeyboardService().pressPianoKey(e.pianoKeyboardId, e.keyNumber);
            }, pianoKeyboard);
        };
        case AUDIO_PERFECT_PITCH_NOTES_PICKER -> e -> {
            doAndRefreshView(() -> {
                getPianoKeyboardService().pressPianoKey(e.pianoKeyboardId, e.keyNumber);
            }, pianoKeyboard);
        };
        default -> throw new RuntimeException("unknown piano keyboard id: " + pianoKeyboard.id);
        };
        return handler;
    }

    public EventHandler<PianoKeyReleasedEvent> createReleaseKeyHandler(final PianoKeyboard pianoKeyboard) {
        EventHandler<PianoKeyReleasedEvent> handler = switch (pianoKeyboard.id) {
        case AUDIO_PERFECT_PITCH_ROOT_NOTE_PICKER -> e -> {
            doAndRefreshView(() -> {
                getPianoKeyboardService().releasePianoKey(e.pianoKeyboardId);
            }, pianoKeyboard);
        };
        case AUDIO_PERFECT_PITCH_NOTES_PICKER -> e -> {
            doAndRefreshView(() -> {
                getPianoKeyboardService().releasePianoKey(e.pianoKeyboardId);
            }, pianoKeyboard);
        };
        default -> throw new RuntimeException("unknown piano keyboard id: " + pianoKeyboard.id);
        };
        return handler;
    }

    // TODO refactoring
    private PianoKeyboardService getPianoKeyboardService() {
        return DI.get(PianoKeyboardService.class);
    }

    private void doAndRefreshView(final Runnable action, final PianoKeyboard pianoKeyboard) {
        action.run();
        updatePianoKeyboardView(pianoKeyboard);
    }

    // TODO what is correct approach? this approach is hand-made
    public void updatePianoKeyboardView(final PianoKeyboard pianoKeyboard) {
        PianoKeyboardDTO pianoKeyboardView;
        try {
            pianoKeyboardView = pianoKeyboardRepository.getViewDto(pianoKeyboard.id);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("pianoKeyboard not found: " + pianoKeyboard);
        }
        pianoKeyboard.hydrateState(pianoKeyboardView);
    }
}

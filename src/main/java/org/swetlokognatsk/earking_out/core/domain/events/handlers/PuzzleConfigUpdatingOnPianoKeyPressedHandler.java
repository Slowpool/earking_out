package org.swetlokognatsk.earking_out.core.domain.events.handlers;

import org.springframework.stereotype.Service;
import org.swetlokognatsk.earking_out.core.domain.events.pianokeyboard.PianoKeyPressedEvent;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardContext;
import org.swetlokognatsk.earking_out.core.domain.services.app.PuzzleConfigService;

@Service
public final class PuzzleConfigUpdatingOnPianoKeyPressedHandler extends DomainEventHandler<PianoKeyPressedEvent> {

    private final PuzzleConfigService puzzleConfigService;

    public PuzzleConfigUpdatingOnPianoKeyPressedHandler(final PuzzleConfigService puzzleConfigService) {
        this.puzzleConfigService = puzzleConfigService;
    }

    public void handle(final PianoKeyPressedEvent event) {
        if (event.pianoKeyboardId.context == PianoKeyboardContext.PUZZLE_CONFIG) {
            puzzleConfigService.updatePropertyViaPianoKeyPressing(event.pianoKeyboardId, event.pianoKeyNumber);
        }
    }
}

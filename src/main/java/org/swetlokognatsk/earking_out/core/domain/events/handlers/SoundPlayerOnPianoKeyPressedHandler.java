package org.swetlokognatsk.earking_out.core.domain.events.handlers;

import org.swetlokognatsk.earking_out.core.domain.events.pianokeyboard.PianoKeyPressedEvent;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardContext;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardId;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfigAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.AudioPerfectPitchConfigAggregate;
import org.swetlokognatsk.earking_out.core.domain.services.domain.piano.PianoSoundPolicyService;
import org.swetlokognatsk.earking_out.core.ports.config.PuzzleConfigRepository;
import org.swetlokognatsk.earking_out.core.ports.piano.PianoKeySoundsPlayer;
import org.swetlokognatsk.earking_out.core.ports.piano.PianoKeyboardRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public final class SoundPlayerOnPianoKeyPressedHandler extends DomainEventHandler<PianoKeyPressedEvent> {

    private final PianoKeySoundsPlayer pianoKeySoundsPlayer;
    private final PianoKeyboardRepository pianoKeyboardRepository;
    private final PianoSoundPolicyService pianoSoundPolicyService;

    public void handle(final PianoKeyPressedEvent event) {
        if (shouldPlaySound(event.pianoKeyboardId)) {
            pianoKeySoundsPlayer.stopAndPlay(event.pianoKeyNumber);
        }
    }

    private boolean shouldPlaySound(final PianoKeyboardId pianoKeyboardId) {
        // for now, if piano keyboard type is not puzzleConfig, sound should always be played
        boolean shouldPlaySound = pianoKeyboardBelongsToPuzzleConfig(pianoKeyboardId)
                ? true
                : pianoSoundPolicyService.shouldPlaySound(pianoKeyboardId);
        return shouldPlaySound;
    }

    private boolean pianoKeyboardBelongsToPuzzleConfig(final PianoKeyboardId pianoKeyboardId) {
        return pianoKeyboardId.context == PianoKeyboardContext.PUZZLE_CONFIG;
    }
}

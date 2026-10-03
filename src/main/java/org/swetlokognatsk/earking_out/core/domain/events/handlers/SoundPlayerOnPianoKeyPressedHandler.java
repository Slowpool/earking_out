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
        return pianoSoundPolicyService.shouldPlaySound(pianoKeyboardId);
    }
}

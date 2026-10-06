package org.swetlokognatsk.earking_out.core.domain.services.domain.piano;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardContext;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardId;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfigAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.AudioPerfectPitchConfigAggregate;
import org.swetlokognatsk.earking_out.core.ports.config.PuzzleConfigRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class PianoSoundPolicyService {
    private final PuzzleConfigRepository puzzleConfigRepository;

    public boolean shouldPlaySound(final PianoKeyboardId pianoKeyboardId) {
        // for now, if piano keyboard type is not puzzleConfig, sound should always be played
        if (pianoKeyboardBelongsToPuzzleConfig(pianoKeyboardId)) {
            return true;
        }

        var exercise = pianoKeyboardId.exercise;
        PuzzleConfigAggregate<?> puzzleConfig = puzzleConfigRepository.genericGet(exercise);

        var shouldPlaySound = switch (puzzleConfig) {
        case AudioPerfectPitchConfigAggregate audioPerfectPitchPuzzleConfig -> !audioPerfectPitchPuzzleConfig.getSoundlessGuessingPiano();
        default -> throw new IllegalArgumentException("unknown puzzle config: " + puzzleConfig.getClass());
        };
        return shouldPlaySound;
    }

    private boolean pianoKeyboardBelongsToPuzzleConfig(final PianoKeyboardId pianoKeyboardId) {
        return pianoKeyboardId.context == PianoKeyboardContext.PUZZLE_CONFIG;
    }
}

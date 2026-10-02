package org.swetlokognatsk.earking_out.core.domain.services.domain.piano;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardId;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfigAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.AudioPerfectPitchConfigAggregate;
import org.swetlokognatsk.earking_out.core.ports.config.PuzzleConfigRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
@Lazy
public class PianoSoundPolicyService {
    private final PuzzleConfigRepository puzzleConfigRepository;

    public boolean shouldPlaySound(final PianoKeyboardId pianoKeyboardId) {
        var exercise = pianoKeyboardId.exercise;
        PuzzleConfigAggregate<?> puzzleConfig = puzzleConfigRepository.genericGet(exercise);

        var shouldPlaySound = switch (puzzleConfig) {
        case AudioPerfectPitchConfigAggregate audioPerfectPitchPuzzleConfig -> !audioPerfectPitchPuzzleConfig.getSoundlessGuessingPiano();
        default -> throw new IllegalArgumentException("unknown puzzle config: " + puzzleConfig.getClass());
        };
        return shouldPlaySound;
    }

}

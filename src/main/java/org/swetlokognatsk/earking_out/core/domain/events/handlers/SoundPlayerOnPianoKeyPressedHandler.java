package org.swetlokognatsk.earking_out.core.domain.events.handlers;

import org.swetlokognatsk.earking_out.core.domain.events.pianokeyboard.PianoKeyPressedEvent;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardId;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfigAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.AudioPerfectPitchConfigAggregate;
import org.swetlokognatsk.earking_out.core.ports.config.PuzzleConfigRepository;
import org.swetlokognatsk.earking_out.core.ports.piano.PianoKeySoundsPlayer;
import org.swetlokognatsk.earking_out.core.ports.piano.PuzzleConfigPianoKeyboardStorageAdapter;

public final class SoundPlayerOnPianoKeyPressedHandler {
    private final PianoKeySoundsPlayer pianoKeySoundsPlayer;
    private final PuzzleConfigPianoKeyboardStorageAdapter puzzleConfigPianoKeyboardRepository;
    private final PuzzleConfigRepository puzzleConfigRepository;

    public SoundPlayerOnPianoKeyPressedHandler(final PianoKeySoundsPlayer pianoKeySoundsPlayer, final PuzzleConfigPianoKeyboardStorageAdapter pianoKeyboardRepository, final PuzzleConfigRepository puzzleConfigRepository) {
        this.pianoKeySoundsPlayer = pianoKeySoundsPlayer;
        this.puzzleConfigPianoKeyboardRepository = pianoKeyboardRepository;
        this.puzzleConfigRepository = puzzleConfigRepository;
    }

    public void handlePianoKeyPressedEvent(final PianoKeyPressedEvent event) {
        if (shouldPlaySound(event.pianoKeyboardId)) {
            pianoKeySoundsPlayer.stopAndPlay(event.pianoKeyNumber);
        }
    }

    private boolean shouldPlaySound(final PianoKeyboardId pianoKeyboardId) {
        // for now, if piano keyboard type is not puzzleConfig, sound should always be played
        boolean shouldPlaySound = pianoKeyboardBelongsToPuzzleConfig(pianoKeyboardId) ? true : inspectConfigWhetherShouldPianoKeyMakeSound(pianoKeyboardId);
        return shouldPlaySound;
    }

    private boolean pianoKeyboardBelongsToPuzzleConfig(final PianoKeyboardId pianoKeyboardId) {
        boolean pianoKeyboardExists;
        try {
            // check whether it exists or not
            puzzleConfigPianoKeyboardRepository.get(pianoKeyboardId);
            pianoKeyboardExists = true;
        } catch (IllegalArgumentException exception) {
            pianoKeyboardExists = false;
        }
        return pianoKeyboardExists;
    }

    private boolean inspectConfigWhetherShouldPianoKeyMakeSound(final PianoKeyboardId pianoKeyboardId) {
        var exercise = pianoKeyboardId.exercise;
        PuzzleConfigAggregate<?> puzzleConfig = puzzleConfigRepository.genericGet(exercise);

        var shouldPlaySound = switch (puzzleConfig) {
        case AudioPerfectPitchConfigAggregate audioPerfectPitchPuzzleConfig -> !audioPerfectPitchPuzzleConfig.getSoundlessGuessingPiano();
        default -> throw new IllegalArgumentException("unknown puzzle config: " + puzzleConfig.getClass());
        };
        return shouldPlaySound;
    }

}

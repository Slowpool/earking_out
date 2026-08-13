package org.swetlokognatsk.earking_out.core.domain.events.handlers;

import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;
import org.swetlokognatsk.earking_out.core.domain.events.exercises.AudioPerfectPitchExercisePickedEvent;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardId;
import org.swetlokognatsk.earking_out.core.domain.services.app.PianoKeyboardService;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.PuzzleConfigDTOAssembler;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.perfectpitch.AudioPerfectPitchConfigDTO;
import org.swetlokognatsk.earking_out.core.ports.config.PuzzleConfigRepository;
import org.swetlokognatsk.earking_out.core.ports.di.DI;
import org.swetlokognatsk.earking_out.core.ports.piano.PianoKeyboardRepository;

public class ActualizePianoKeyboardsOnAudioPerfectPitchExercisePickedHandler extends DomainEventHandler<AudioPerfectPitchExercisePickedEvent> {

    private final PianoKeyboardRepository pianoKeyboardRepository;
    private final PuzzleConfigDTOAssembler puzzleConfigDtoAssembler;

    public ActualizePianoKeyboardsOnAudioPerfectPitchExercisePickedHandler(final PianoKeyboardRepository pianoKeyboardRepository, final PuzzleConfigRepository puzzleConfigRepository) {
        this.pianoKeyboardRepository = pianoKeyboardRepository;
        // TODO put this logic into repository
        // var puzzleConfig = puzzleConfigRepository.get(new AudioPerfectPitchExercise());
        this.puzzleConfigDtoAssembler = DI.get(PuzzleConfigDTOAssembler.class);
    }

    private AudioPerfectPitchConfigDTO getActualPuzzleConfigDto() {
        return puzzleConfigDtoAssembler.getPuzzleConfigDTO(new AudioPerfectPitchExercise());
    }

    public void handle(final AudioPerfectPitchExercisePickedEvent event) {
        var actualConfig = getActualPuzzleConfigDto();
        actualizePianoKeyboardForNormalizedNotesForPuzzle(actualConfig);
        actualizePianoKeyboardForNormalizedRootNote(actualConfig);
    }

    private void actualizePianoKeyboardForNormalizedNotesForPuzzle(final AudioPerfectPitchConfigDTO config) {
        var pianoKeyboard = pianoKeyboardRepository.get(PianoKeyboardId.AUDIO_PERFECT_PITCH_NOTES_PICKER);
        var normalizedNotes = config.normalizedNotesForPuzzle;

        pianoKeyboard.resetState();
        pianoKeyboard.restoreSelectedKeys(normalizedNotes);

        pianoKeyboardRepository.save(pianoKeyboard);
    }

    private void actualizePianoKeyboardForNormalizedRootNote(final AudioPerfectPitchConfigDTO config) {
        var pianoKeyboard = pianoKeyboardRepository.get(PianoKeyboardId.AUDIO_PERFECT_PITCH_ROOT_NOTE_PICKER);
        var normalizedRootNote = config.normalizedRootNote;

        pianoKeyboard.resetState();
        pianoKeyboard.restoreSelectedKey(normalizedRootNote);

        pianoKeyboardRepository.save(pianoKeyboard);
    }
}

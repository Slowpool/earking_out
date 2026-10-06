package org.swetlokognatsk.earking_out.app.web.views.models.fillers.perfectpitch;

import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.PerfectPitchConfigAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.PerfectPitchInputMode;
import static org.swetlokognatsk.earking_out.core.domain.model.exercises.ExercisesFactory.AUDIO_PERFECT_PITCH_EXERCISE;
import org.swetlokognatsk.earking_out.SpringApp;
import org.swetlokognatsk.earking_out.app.web.views.models.PianoKeyboardViewModelsBuilder;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.perfectpitch.AudioPerfectPitchConfigDTO;
import org.swetlokognatsk.earking_out.core.ports.config.PuzzleConfigRepository;
import lombok.AllArgsConstructor;
import static org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardId.*;
import static org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.PerfectPitchInputMode.*;

@Component
@AllArgsConstructor
@ConditionalOnExpression(SpringApp.IS_WEB_BUILD)
public class AudioPerfectPitchConfigViewFiller {

    private final PuzzleConfigRepository puzzleConfigRepository;
    private final PianoKeyboardViewModelsBuilder pianoKeyboardsBuilder;

    public void fill(final ModelAndView modelAndView) {
        var puzzleConfigDto = (AudioPerfectPitchConfigDTO) puzzleConfigRepository.getPuzzleConfigDTO(AUDIO_PERFECT_PITCH_EXERCISE);

        modelAndView.addObject("targetNumberOfPuzzlesProp", PerfectPitchConfigAggregate.TARGET_NUMBER_OF_PUZZLES_PROP);
        modelAndView.addObject("targetNumberOfPuzzles", puzzleConfigDto.targetNumberOfPuzzles);

        modelAndView.addObject("statsRecordingProp", PerfectPitchConfigAggregate.STATS_RECORDING_PROP);
        modelAndView.addObject("statsRecording", puzzleConfigDto.statsRecording);

        var notesPickerPianoKeyboardModel = pianoKeyboardsBuilder.build(AUDIO_PERFECT_PITCH_NOTES_PICKER);
        modelAndView.addObject("notesPickerPianoKeyboardModel", notesPickerPianoKeyboardModel);

        // TODO pass it conditionally, if input mode is KEYBOARD_AS_PIANO
        var rootNotePickerPianoKeyboardModel = pianoKeyboardsBuilder.build(AUDIO_PERFECT_PITCH_ROOT_NOTE_PICKER);
        modelAndView.addObject("rootNotePickerPianoKeyboardModel", rootNotePickerPianoKeyboardModel);

        modelAndView.addObject("inputModes", new PerfectPitchInputMode[] { PIANO_ON_SCREEN, NOTES_AS_TEXT });
        modelAndView.addObject("inputModeProp", PerfectPitchConfigAggregate.INPUT_MODE_PROP);
        modelAndView.addObject("inputMode", puzzleConfigDto.inputMode);

        modelAndView.addObject("guessingPianoIsSoundlessProp", PerfectPitchConfigAggregate.SOUNDLESS_GUESSING_PIANO_PROP);
        modelAndView.addObject("guessingPianoIsSoundless", puzzleConfigDto.soundlessGuessingPiano);
    }

}

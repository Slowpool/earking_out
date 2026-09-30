package org.swetlokognatsk.earking_out.app.web.controllers;

import static org.swetlokognatsk.earking_out.core.domain.model.music.Constants.PIANO_KEYS_NUMBER;
import static org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber.FIRST_NOTE_NUMBER;
import java.util.ArrayList;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.swetlokognatsk.earking_out.app.web.views.models.PianoKeyViewModel;
import org.swetlokognatsk.earking_out.app.web.views.models.PianoKeyboardViewModel;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardId;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.PerfectPitchInputMode;
import org.swetlokognatsk.earking_out.core.domain.services.domain.piano.PianoKeyColorService;
import org.swetlokognatsk.earking_out.core.ports.di.DI;
import static org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardId.*;
import static org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.PerfectPitchInputMode.*;

// TODO all code here is yet a draft, for experiments
@RestController
@RequestMapping("/api/v1")
public class ApiController {

    @GetMapping("/exercise/{exerciseName}/{exerciseType}")
    public ModelAndView exercise(@PathVariable final String exerciseName, @PathVariable final String exerciseType) {
        var view = getExerciseView(exerciseName, exerciseType);
        var modelAndView = new ModelAndView(view);

        modelAndView.addObject("targetNumberOfPuzzles", 1937);

        modelAndView.addObject("statsRecording", true);

        var notesPickerPianoKeyboardModel = buildPianoKeyboardViewModel(AUDIO_PERFECT_PITCH_NOTES_PICKER);
        modelAndView.addObject("notesPickerPianoKeyboardModel", notesPickerPianoKeyboardModel);

        // TODO pass it conditionally, if input mode is KEYBOARD_AS_PIANO
        var rootNotePickerPianoKeyboardModel = buildPianoKeyboardViewModel(AUDIO_PERFECT_PITCH_ROOT_NOTE_PICKER);
        modelAndView.addObject("rootNotePickerPianoKeyboardModel", rootNotePickerPianoKeyboardModel);

        modelAndView.addObject("inputModes", PerfectPitchInputMode.values());
        modelAndView.addObject("inputMode", KEYBOARD_AS_PIANO);

        modelAndView.addObject("guessingPianoIsSoundless", true);

        modelAndView.addObject("exerciseName", exerciseName);
        modelAndView.addObject("exerciseType", exerciseType);

        return modelAndView;
    }

    private String getExerciseView(final String exerciseName, final String exerciseType) {
        // TODO getExerciseView
        return "puzzles/configs/perfect_pitch/audio_perfect_pitch_puzzle_config";
    }

    @GetMapping("/session/start/{exerciseName}/{exerciseType}")
    public ModelAndView startSession(@PathVariable final String exerciseName, @PathVariable final String exerciseType) {
        var view = getSessionView(exerciseName, exerciseType);
        var modelAndView = new ModelAndView(view);

        modelAndView.addObject("numberOfCompletedPuzzles", 5);

        modelAndView.addObject("targetNumberOfPuzzles", 100);

        var guessingPianoKeyboardViewModel = buildPianoKeyboardViewModel(AUDIO_PERFECT_PITCH_NOTES_GUESSING);
        modelAndView.addObject("guessingPianoKeyboardModel", guessingPianoKeyboardViewModel);

        modelAndView.addObject("exerciseName", exerciseName);
        modelAndView.addObject("exerciseType", exerciseType);

        return modelAndView;
    }

    private String getSessionView(final String exerciseName, final String exerciseType) {
        // TODO getSessionView
        return "puzzles/perfect_pitch/audio_perfect_pitch_puzzle";
    }

    private PianoKeyboardViewModel buildPianoKeyboardViewModel(final PianoKeyboardId pianoKeyboardId) {
        var pianoKeys = buildPianoKeyViewModels(pianoKeyboardId);
        return new PianoKeyboardViewModel(pianoKeyboardId, pianoKeys);
    }

    private PianoKeyViewModel[] buildPianoKeyViewModels(final PianoKeyboardId pianoKeyboardId) {
        // TODO obviously, refactoring, use pianoKeyboardId
        final var models = new ArrayList<PianoKeyViewModel>(PIANO_KEYS_NUMBER);
        PianoKeyNumber.forEachKey((PianoKeyNumber keyNumber) -> {
            var colorService = DI.get(PianoKeyColorService.class);
            var pianoKeyModel = new PianoKeyViewModel(keyNumber, colorService.getColor(keyNumber), false, keyNumber.equals(FIRST_NOTE_NUMBER));
            models.add(pianoKeyModel);
        });
        return models.toArray(PianoKeyViewModel[]::new);
    }

    @GetMapping("/session/finish/{exerciseName}/{exerciseType}")
    public ModelAndView finishSession(@PathVariable final String exerciseName, @PathVariable final String exerciseType) {
        var view = getStatsSessionView(exerciseName, exerciseType);
        var modelAndView = new ModelAndView(view);
        modelAndView.addObject("puzzlesCompletedPerfectly", 5);
        modelAndView.addObject("numberOfCompletedPuzzles", 50);
        modelAndView.addObject("perfectlyCompletedPuzzlesRate", 0.1);

        modelAndView.addObject("exerciseName", exerciseName);
        modelAndView.addObject("exerciseType", exerciseType);

        return modelAndView;
    }

    private String getStatsSessionView(final String exerciseName, final String exerciseType) {
        // TODO getStatsSessionView
        return "stats/session/perfect_pitch/audio_perfect_pitch_session_stats";
    }
}

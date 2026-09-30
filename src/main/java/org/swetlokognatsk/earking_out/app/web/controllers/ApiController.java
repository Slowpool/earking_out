package org.swetlokognatsk.earking_out.app.web.controllers;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.PerfectPitchInputMode;

@RestController
@RequestMapping("/api/v1")
public class ApiController {

    @GetMapping("/exercise/{exerciseName}/{exerciseType}")
    public ModelAndView exercise(@PathVariable final String exerciseName, @PathVariable final String exerciseType) {
        var view = getView(exerciseName, exerciseType);
        var modelAndView = new ModelAndView(view);
        modelAndView.addObject("targetNumberOfPuzzles", 1937);
        modelAndView.addObject("statsRecording", true);
        // modelAndView.addObject("notes", );
        // modelAndView.addObject("rootNote", );
        modelAndView.addObject("inputModes", PerfectPitchInputMode.values());
        modelAndView.addObject("inputMode", PerfectPitchInputMode.KEYBOARD_AS_PIANO);
        modelAndView.addObject("guessingPianoIsSoundless", true);

        modelAndView.addObject("exerciseName", exerciseName);
        modelAndView.addObject("exerciseType", exerciseType);

        return modelAndView;
    }

    private String getView(final String exerciseName, final String exerciseType) {
        // TODO mapToView
        return "puzzle_configs/audio_perfect_pitch";
    }
}

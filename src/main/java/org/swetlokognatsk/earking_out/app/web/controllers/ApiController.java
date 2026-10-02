package org.swetlokognatsk.earking_out.app.web.controllers;

import java.util.List;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.swetlokognatsk.earking_out.app.web.models.requests.PuzzleConfigPianoKeyboardActionRequest;
import org.swetlokognatsk.earking_out.app.web.models.responses.PuzzleConfigPianoKeyPressingResponse;
import org.swetlokognatsk.earking_out.app.web.models.responses.PuzzleConfigPianoKeyReleasingResponse;
import org.swetlokognatsk.earking_out.app.web.services.SimplePuzzleConfigPropertiesCaster;
import org.swetlokognatsk.earking_out.app.web.views.models.PianoKeyboardViewModelsBuilder;
import org.swetlokognatsk.earking_out.app.web.views.models.fillers.PuzzleConfigViewModelFiller;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardId;
import org.swetlokognatsk.earking_out.core.domain.services.app.ExerciseService;
import org.swetlokognatsk.earking_out.core.domain.services.app.PianoKeyboardService;
import org.swetlokognatsk.earking_out.core.domain.services.app.PuzzleConfigService;
import org.swetlokognatsk.earking_out.core.ports.di.DI;
import org.swetlokognatsk.earking_out.core.ports.piano.PianoKeyboardRepository;
import org.springframework.web.bind.annotation.RequestMethod;

// TODO all code here is yet a draft, for experiments
/**
 * I gave this controller an `Api` name because it never returns entire html
 * documents - it returns only an html fragments without html/head/body tags,
 * json objects and etc.
 */
@RestController
@RequestMapping("/api/v1")
public class ApiController {

    @GetMapping("/exercise/{exerciseName}/{exerciseType}")
    public ModelAndView exercise(@PathVariable final String exerciseName, @PathVariable final String exerciseType, final Exercise exercise) {
        var exerciseService = DI.get(ExerciseService.class);
        exerciseService.pickExercise(exercise);
        
        var view = getExerciseView(exercise);
        var modelAndView = new ModelAndView(view);

        var puzzleConfigViewModelFiller = DI.get(PuzzleConfigViewModelFiller.class);
        puzzleConfigViewModelFiller.fill(exercise, modelAndView);

        return modelAndView;
    }

    private String getExerciseView(final Exercise exercise) {
        // TODO getExerciseView
        return "puzzles/configs/perfect_pitch/audio_perfect_pitch_puzzle_config";
    }

    @GetMapping("/session/start/{exerciseName}/{exerciseType}")
    public ModelAndView startSession(@PathVariable final String exerciseName, @PathVariable final String exerciseType, final Exercise exercise) {
        var view = getSessionView(exercise);
        var modelAndView = new ModelAndView(view);

        modelAndView.addObject("numberOfCompletedPuzzles", 5);

        modelAndView.addObject("targetNumberOfPuzzles", 100);

        // var guessingPianoKeyboardViewModel = buildPianoKeyboardViewModel(AUDIO_PERFECT_PITCH_NOTES_GUESSING);
        // modelAndView.addObject("guessingPianoKeyboardModel", guessingPianoKeyboardViewModel);

        modelAndView.addObject("exerciseName", exerciseName);
        modelAndView.addObject("exerciseType", exerciseType);

        return modelAndView;
    }

    private String getSessionView(final Exercise exercise) {
        // TODO getSessionView
        return "puzzles/perfect_pitch/audio_perfect_pitch_puzzle";
    }

    @GetMapping("/session/finish/{exerciseName}/{exerciseType}")
    public ModelAndView finishSession(@PathVariable final String exerciseName, @PathVariable final String exerciseType, final Exercise exercise) {
        var view = getStatsSessionView(exercise);
        var modelAndView = new ModelAndView(view);
        modelAndView.addObject("puzzlesCompletedPerfectly", 5);
        modelAndView.addObject("numberOfCompletedPuzzles", 50);
        modelAndView.addObject("perfectlyCompletedPuzzlesRate", 0.1);

        modelAndView.addObject("exerciseName", exerciseName);
        modelAndView.addObject("exerciseType", exerciseType);

        return modelAndView;
    }

    private String getStatsSessionView(final Exercise exercise) {
        // TODO getStatsSessionView
        return "stats/session/perfect_pitch/audio_perfect_pitch_session_stats";
    }

    // TODO the difference between @RequestParam/RequestBody? why not Map<String,String>?
    @PatchMapping("/puzzle/config/{exerciseName}/{exerciseType}/update/{propertyName}")
    public ResponseEntity<?> updatePuzzleConfigProperty(final Exercise exercise, @PathVariable final String propertyName, @RequestParam final MultiValueMap<String, String> body) {
        var puzzleConfigService = DI.get(PuzzleConfigService.class);
        var simplePuzzleConfigPropertiesCaster = DI.get(SimplePuzzleConfigPropertiesCaster.class);
        var newValueList = body.get(propertyName);
        var newValue = newValueList == null || newValueList.isEmpty()
                ? null
                : newValueList.getFirst();
        try {
            var castedNewValue = simplePuzzleConfigPropertiesCaster.cast(exercise, propertyName, newValue);
            puzzleConfigService.updateProperty(exercise, propertyName, castedNewValue);
            return ResponseEntity.ok().body(null);
        } catch (IllegalArgumentException e) {
            // TODO add detailed info
            return ResponseEntity.badRequest().body("bad request");
        }
    }

    // TODO create new PianoKey view model, with only variable piano key data (isPressed, isSelected), without color and octaveScopedKeyNumber
    // TODO should ResponseEntity<?> remain or should it return PuzzleConfigPianoKeyPressingResult
    @PostMapping("/puzzle/config/{exerciseName}/{exerciseType}/piano-keyboard/press-key")
    public ResponseEntity<PuzzleConfigPianoKeyPressingResponse> pressPuzzleConfigPianoKey(final Exercise exercise, @RequestBody final PuzzleConfigPianoKeyboardActionRequest body) {
        var pianoKeyboardService = DI.get(PianoKeyboardService.class);
        pianoKeyboardService.pressPianoKey(body.pianoKeyboardId, body.pianoKeyNumber);

        var pianoKeyboardBuilder = DI.get(PianoKeyboardViewModelsBuilder.class);
        var pianoKeyboardViewModel = pianoKeyboardBuilder.build(body.pianoKeyboardId);
        var response = new PuzzleConfigPianoKeyPressingResponse(pianoKeyboardViewModel);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/puzzle/config/{exerciseName}/{exerciseType}/piano-keyboard/release-key")
    public ResponseEntity<PuzzleConfigPianoKeyReleasingResponse> releasePuzzleConfigPianoKey(final Exercise exercise, @RequestBody final PuzzleConfigPianoKeyboardActionRequest body) {
        var pianoKeyboardService = DI.get(PianoKeyboardService.class);
        pianoKeyboardService.releasePianoKey(body.pianoKeyboardId);

        var pianoKeyboardBuilder = DI.get(PianoKeyboardViewModelsBuilder.class);
        var pianoKeyboardViewModel = pianoKeyboardBuilder.build(body.pianoKeyboardId);
        var response = new PuzzleConfigPianoKeyReleasingResponse(pianoKeyboardViewModel);

        return ResponseEntity.ok(response);
    }
}

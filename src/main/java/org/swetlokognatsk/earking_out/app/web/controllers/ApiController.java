package org.swetlokognatsk.earking_out.app.web.controllers;

import static org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise.unknownExercise;
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
import org.swetlokognatsk.earking_out.app.web.models.requests.PianoKeyboardActionRequest;
import org.swetlokognatsk.earking_out.app.web.models.responses.PuzzleConfigPianoKeyPressingResponse;
import org.swetlokognatsk.earking_out.app.web.models.responses.PuzzleConfigPianoKeyReleasingResponse;
import org.swetlokognatsk.earking_out.app.web.models.responses.PuzzlePianoKeyPressingResponse;
import org.swetlokognatsk.earking_out.app.web.models.responses.PuzzlePianoKeyReleasingResponse;
import org.swetlokognatsk.earking_out.app.web.services.PuzzlePianoKeyPressingResponseBuilder;
import org.swetlokognatsk.earking_out.app.web.services.PuzzlePianoKeyReleasingResponseBuilder;
import org.swetlokognatsk.earking_out.app.web.services.SimplePuzzleConfigPropertiesCaster;
import org.swetlokognatsk.earking_out.app.web.views.models.PianoKeyboardViewModelsBuilder;
import org.swetlokognatsk.earking_out.app.web.views.models.fillers.PuzzleConfigViewFiller;
import org.swetlokognatsk.earking_out.app.web.views.models.fillers.PuzzleViewFiller;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.ExerciseTypes;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardId;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionId;
import org.swetlokognatsk.earking_out.core.domain.services.app.ExerciseService;
import org.swetlokognatsk.earking_out.core.domain.services.app.PianoKeyboardService;
import org.swetlokognatsk.earking_out.core.domain.services.app.PuzzleConfigService;
import org.swetlokognatsk.earking_out.core.domain.services.app.session.SessionService;
import org.swetlokognatsk.earking_out.core.domain.services.app.session.perfectpitch.AudioPerfectPitchSessionService;
import org.swetlokognatsk.earking_out.core.domain.services.domain.puzzles.configs.exceptions.InvalidPuzzleConfigException;
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
    public ModelAndView exercise(final Exercise exercise) {
        var exerciseService = DI.get(ExerciseService.class);
        exerciseService.pickExercise(exercise);

        var view = getExerciseView(exercise);
        var modelAndView = new ModelAndView(view);

        var puzzleConfigViewFiller = DI.get(PuzzleConfigViewFiller.class);
        puzzleConfigViewFiller.fill(exercise, modelAndView);

        return modelAndView;
    }

    private String getExerciseView(final Exercise exercise) {
        // TODO getExerciseView
        return "puzzles/configs/perfect_pitch/audio_perfect_pitch_puzzle_config";
    }

    @PostMapping("/session/{exerciseName}/{exerciseType}/start")
    public ModelAndView startSession(final Exercise exercise) {
        var sessionService = DI.get(SessionService.class);
        try {
            sessionService.start();
        } catch (InvalidPuzzleConfigException e) {
            // TODO display errors
            return exercise(exercise);
        }

        var view = getSessionView(exercise);
        var modelAndView = new ModelAndView(view);

        var puzzleViewFiller = DI.get(PuzzleViewFiller.class);
        puzzleViewFiller.fill(exercise, modelAndView);

        return modelAndView;
    }

    private String getSessionView(final Exercise exercise) {
        // TODO getSessionView
        return "puzzles/perfect_pitch/audio_perfect_pitch_puzzle";
    }

    @GetMapping("/session/{exerciseName}/{exerciseType}/finish")
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
        var simplePuzzleConfigPropertiesCaster = DI.get(SimplePuzzleConfigPropertiesCaster.class);
        var newValueList = body.get(propertyName);
        var newValue = newValueList == null || newValueList.isEmpty()
                ? null
                : newValueList.getFirst();

        try {
            var castedNewValue = simplePuzzleConfigPropertiesCaster.cast(exercise, propertyName, newValue);
            DI.get(PuzzleConfigService.class)
                    .updateProperty(exercise, propertyName, castedNewValue);
            return ResponseEntity.ok().body(null);
        } catch (IllegalArgumentException e) {
            // TODO add detailed info
            return ResponseEntity.badRequest().body("bad request");
        }
    }

    // TODO create new PianoKey view model, with only variable piano key data (isPressed, isSelected), without color and octaveScopedKeyNumber
    // TODO should ResponseEntity<?> remain or should it return PuzzleConfigPianoKeyPressingResult
    @PostMapping("/puzzle/config/{exerciseName}/{exerciseType}/piano-keyboard/press-key")
    public ResponseEntity<PuzzleConfigPianoKeyPressingResponse> pressPuzzleConfigPianoKey(final Exercise exercise, @RequestBody final PianoKeyboardActionRequest body) {
        DI.get(PianoKeyboardService.class)
                .pressPianoKey(body.pianoKeyboardId, body.pianoKeyNumber);

        var pianoKeyboardViewModel = DI.get(PianoKeyboardViewModelsBuilder.class)
                .build(body.pianoKeyboardId);
        var response = new PuzzleConfigPianoKeyPressingResponse(pianoKeyboardViewModel);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/puzzle/config/{exerciseName}/{exerciseType}/piano-keyboard/release-key")
    public ResponseEntity<PuzzleConfigPianoKeyReleasingResponse> releasePuzzleConfigPianoKey(final Exercise exercise, @RequestBody final PianoKeyboardActionRequest body) {
        DI.get(PianoKeyboardService.class)
                .releasePianoKey(body.pianoKeyboardId);

        var pianoKeyboardViewModel = DI.get(PianoKeyboardViewModelsBuilder.class)
                .build(body.pianoKeyboardId);
        var response = new PuzzleConfigPianoKeyReleasingResponse(pianoKeyboardViewModel);

        return ResponseEntity.ok(response);
    }

    // // TODO this endpoint is for other types of input, at least in perfect pitch user can also input plain text as guess.
    // @PostMapping("/session/{exerciseName}/{exerciseType}/guess")
    // public ResponseEntity<PuzzleConfigPianoKeyReleasingResponse> guess(final Exercise exercise, @RequestBody final PuzzleConfigPianoKeyboardActionRequest body) {
    //     var pianoKeyboardService = DI.get(PianoKeyboardService.class);
    //     pianoKeyboardService.releasePianoKey(body.pianoKeyboardId);

    //     var pianoKeyboardBuilder = DI.get(PianoKeyboardViewModelsBuilder.class);
    //     var pianoKeyboardViewModel = pianoKeyboardBuilder.build(body.pianoKeyboardId);
    //     var response = new PuzzleConfigPianoKeyReleasingResponse(pianoKeyboardViewModel);

    //     return ResponseEntity.ok(response);
    // }

    // why to receive sessionId only on pressing? to prevent from case when the guess was last and we obtain the updated session info via getActiveSession(), which fails in that case. schematically: frontend---press key-->backend---update domain state-->responseBuilder-->update domain state via `getActiveSession()`, but wait, it'll throw exception beause it just was finished! well, then, let's get session by it's id - no exception, good!
    @PostMapping("/puzzle/{exerciseName}/{exerciseType}/piano-keyboard/press-key")
    public ResponseEntity<? extends PuzzlePianoKeyPressingResponse> pressPuzzlePianoKey(final Exercise exercise, @RequestBody final PianoKeyboardActionRequest body, @RequestParam final SessionId sessionId) {
        DI.get(PianoKeyboardService.class)
                .pressPianoKey(body.pianoKeyboardId, body.pianoKeyNumber);

        var response = DI.get(PuzzlePianoKeyPressingResponseBuilder.class)
                .build(exercise, sessionId);
        return ResponseEntity.ok(response);
    }

    // why no sessionId? because either the session is open, either it's closed and ui must not send any release commands after session is closed. such a convention.
    @PostMapping("/puzzle/{exerciseName}/{exerciseType}/piano-keyboard/release-key")
    public ResponseEntity<? extends PuzzlePianoKeyReleasingResponse> releasePuzzlePianoKey(final Exercise exercise, @RequestBody final PianoKeyboardActionRequest body) {
        var pianoKeyboardService = DI.get(PianoKeyboardService.class);
        pianoKeyboardService.releasePianoKey(body.pianoKeyboardId);

        var response = DI.get(PuzzlePianoKeyReleasingResponseBuilder.class)
                .build(exercise);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/puzzle/{exerciseName}/{exerciseType}/hear-hint-again")
    public ResponseEntity<?> hearHintAgain(final Exercise exercise) {
        if (exerciseDoesNotSupportHintReplaying(exercise)) {
            return ResponseEntity.badRequest()
                    .body("this exercise does not support hint replaying");
        }

        switch (exercise) {
        case AudioPerfectPitchExercise appe:
            DI.get(AudioPerfectPitchSessionService.class)
                    .hearAgain();
            break;
        default:
            throw new RuntimeException(unknownExercise(exercise));
        }
        return ResponseEntity.ok().body(null);
    }

    private static boolean exerciseDoesNotSupportHintReplaying(final Exercise exercise) {
        return exercise.type != ExerciseTypes.AUDIO;
    }
}

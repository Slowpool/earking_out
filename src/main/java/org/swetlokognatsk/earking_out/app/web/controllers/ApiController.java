package org.swetlokognatsk.earking_out.app.web.controllers;

import static org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise.unknownExercise;
import static org.swetlokognatsk.earking_out.core.domain.model.exercises.ExercisesFactory.AUDIO_PERFECT_PITCH_EXERCISE;
import static org.springframework.http.ResponseEntity.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
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
import org.swetlokognatsk.earking_out.app.web.models.requests.perfectpitch.AudioPerfectPitchGuessRequest;
import org.swetlokognatsk.earking_out.app.web.models.responses.PuzzleConfigPianoKeyPressingResponse;
import org.swetlokognatsk.earking_out.app.web.models.responses.PuzzleConfigPianoKeyReleasingResponse;
import org.swetlokognatsk.earking_out.app.web.models.responses.PuzzlePianoKeyPressingResponse;
import org.swetlokognatsk.earking_out.app.web.models.responses.PuzzlePianoKeyReleasingResponse;
import org.swetlokognatsk.earking_out.app.web.services.SimplePuzzleConfigPropertiesCaster;
import org.swetlokognatsk.earking_out.app.web.services.renderers.perfectpitch.AudioPerfectPitchStatsRenderer;
import org.swetlokognatsk.earking_out.app.web.services.responsebuilders.PuzzleGuessingResponseBuilder;
import org.swetlokognatsk.earking_out.app.web.services.responsebuilders.PuzzlePianoKeyPressingResponseBuilder;
import org.swetlokognatsk.earking_out.app.web.services.responsebuilders.PuzzlePianoKeyReleasingResponseBuilder;
import org.swetlokognatsk.earking_out.app.web.views.models.PianoKeyboardViewModelsProjector;
import org.swetlokognatsk.earking_out.app.web.views.models.fillers.PuzzleConfigViewFiller;
import org.swetlokognatsk.earking_out.app.web.views.models.fillers.PuzzleViewFiller;
import org.swetlokognatsk.earking_out.core.domain.helpers.SessionRepositoryDelegator;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.ExerciseTypes;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionId;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionStates;
import org.swetlokognatsk.earking_out.core.domain.model.session.exceptions.InvalidTextNoteException;
import org.swetlokognatsk.earking_out.core.domain.model.session.exceptions.OutOfRangeTextNoteException;
import org.swetlokognatsk.earking_out.core.domain.services.app.ExerciseService;
import org.swetlokognatsk.earking_out.core.domain.services.app.PianoKeyboardService;
import org.swetlokognatsk.earking_out.core.domain.services.app.PuzzleConfigService;
import org.swetlokognatsk.earking_out.core.domain.services.app.session.SessionService;
import org.swetlokognatsk.earking_out.core.domain.services.app.session.perfectpitch.AudioPerfectPitchSessionService;
import org.swetlokognatsk.earking_out.core.domain.services.domain.puzzles.configs.exceptions.InvalidPuzzleConfigException;
import org.swetlokognatsk.earking_out.core.ports.di.DI;

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
        DI.get(PuzzleConfigViewFiller.class)
                .fill(exercise, modelAndView);

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
        DI.get(PuzzleViewFiller.class)
                .fill(exercise, modelAndView);

        return modelAndView;
    }

    private String getSessionView(final Exercise exercise) {
        // TODO getSessionView
        return "puzzles/perfect_pitch/audio_perfect_pitch_puzzle";
    }

    @PostMapping("/session/{exerciseName}/{exerciseType}/abort")
    public ModelAndView abortSession(final Exercise exercise) {
        var view = getStatsSessionView(exercise);
        var modelAndView = new ModelAndView(view);
        modelAndView.addObject("puzzlesCompletedPerfectly", 5);
        modelAndView.addObject("numberOfCompletedPuzzles", 50);
        modelAndView.addObject("perfectlyCompletedPuzzlesRate", 0.1);

        return modelAndView;
    }

    private String getStatsSessionView(final Exercise exercise) {
        // TODO getStatsSessionView
        return "stats/session/perfect_pitch/audio_perfect_pitch_session_stats";
    }

    @PatchMapping("/puzzle/config/{exerciseName}/{exerciseType}/update/{propertyName}")
    public ResponseEntity<?> updatePuzzleConfigProperty(final Exercise exercise, @PathVariable final String propertyName, @RequestParam final Map<String, String> body) {
        var simplePuzzleConfigPropertiesCaster = DI.get(SimplePuzzleConfigPropertiesCaster.class);
        var newValue = body.get(propertyName);

        try {
            var castedNewValue = simplePuzzleConfigPropertiesCaster.cast(exercise, propertyName, newValue);
            DI.get(PuzzleConfigService.class)
                    .updateProperty(exercise, propertyName, castedNewValue);
            return ok().body(null);
        } catch (IllegalArgumentException e) {
            // TODO add detailed info
            return badRequest().body("bad request");
        }
    }

    // TODO create new PianoKey view model, with only variable piano key data (isPressed, isSelected), without color and octaveScopedKeyNumber
    @PostMapping("/puzzle/config/{exerciseName}/{exerciseType}/piano-keyboard/press-key")
    public ResponseEntity<PuzzleConfigPianoKeyPressingResponse> pressPuzzleConfigPianoKey(final Exercise exercise, @RequestBody final PianoKeyboardActionRequest body) {
        DI.get(PianoKeyboardService.class)
                .pressPianoKey(body.pianoKeyboardId, body.pianoKeyNumber);

        var pianoKeyboardViewModel = DI.get(PianoKeyboardViewModelsProjector.class)
                .build(body.pianoKeyboardId);
        var response = new PuzzleConfigPianoKeyPressingResponse(pianoKeyboardViewModel);

        return ok(response);
    }

    @PostMapping("/puzzle/config/{exerciseName}/{exerciseType}/piano-keyboard/release-key")
    public ResponseEntity<PuzzleConfigPianoKeyReleasingResponse> releasePuzzleConfigPianoKey(final Exercise exercise, @RequestBody final PianoKeyboardActionRequest body) {
        DI.get(PianoKeyboardService.class)
                .releasePianoKey(body.pianoKeyboardId);

        var pianoKeyboardViewModel = DI.get(PianoKeyboardViewModelsProjector.class)
                .build(body.pianoKeyboardId);
        var response = new PuzzleConfigPianoKeyReleasingResponse(pianoKeyboardViewModel);

        return ok(response);
    }

    // why to receive sessionId only on pressing? to prevent from case when the guess was last and we obtain the updated session info via getActiveSession(), which fails in that case. schematically: frontend---press key-->backend---update domain state-->responseBuilder-->update domain state via `getActiveSession()`, but wait, it'll throw exception beause it just was finished! well, then, let's get session by it's id - no exception, good!
    @PostMapping("/puzzle/{exerciseName}/{exerciseType}/piano-keyboard/press-key")
    public ResponseEntity<? extends PuzzlePianoKeyPressingResponse> pressPuzzlePianoKey(final Exercise exercise, @RequestBody final PianoKeyboardActionRequest body, @RequestParam(required = true) final SessionId sessionId) {
        DI.get(PianoKeyboardService.class)
                .pressPianoKey(body.pianoKeyboardId, body.pianoKeyNumber);

        persistQueuedEventLogs(sessionId);
        var response = DI.get(PuzzlePianoKeyPressingResponseBuilder.class)
                .build(exercise, sessionId);

        return ok(response);
    }

    // TODO now this is a temporary hack that executes all globally queued tasks. why to do it? events a logged asynchronously, and if user finishes the session before all events are logged, stats will be based on partial event logs and consequently they will be incorrect. debugging i figured out it's still imperfect, - there's still a possibility of wrong stats, but it's much lower
    private void persistQueuedEventLogs(final SessionId sessionId) {
        var sessionDto = DI.get(SessionRepositoryDelegator.class)
                .getSessionAggregateDTO(sessionId);

        if (sessionDto.state == SessionStates.COMPLETED || sessionDto.state == SessionStates.ABORTED) {
            executeAllTasksSynchronously();
        }
    }

    private void executeAllTasksSynchronously() {
        var taskExecutor = DI.get(ThreadPoolTaskExecutor.class);

        var remainedTasks = new LinkedList<Runnable>();
        taskExecutor.getThreadPoolExecutor()
                .getQueue()
                .drainTo(remainedTasks);

        for (var task : remainedTasks) {
            try {
                task.run();
            } catch (Throwable e) {
                // TODO log
                System.out.println("failed to execute task: %s".formatted(e.getMessage()));
            }
        }
    }

    // why no sessionId? because either the session is open, either it's closed and ui must not send any release commands after session is closed. such a convention.
    @PostMapping("/puzzle/{exerciseName}/{exerciseType}/piano-keyboard/release-key")
    public ResponseEntity<? extends PuzzlePianoKeyReleasingResponse> releasePuzzlePianoKey(final Exercise exercise, @RequestBody final PianoKeyboardActionRequest body) {
        var pianoKeyboardService = DI.get(PianoKeyboardService.class);
        pianoKeyboardService.releasePianoKey(body.pianoKeyboardId);

        var response = DI.get(PuzzlePianoKeyReleasingResponseBuilder.class)
                .build(exercise);
        return ok(response);
    }

    @PostMapping("/puzzle/{exerciseName}/{exerciseType}/hear-hint-again")
    public ResponseEntity<?> hearHintAgain(final Exercise exercise) {
        if (exerciseDoesNotSupportHintReplaying(exercise)) {
            return badRequest()
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
        return ok().body(null);
    }

    private static boolean exerciseDoesNotSupportHintReplaying(final Exercise exercise) {
        return exercise.type != ExerciseTypes.AUDIO;
    }

    @PostMapping("/session/{exerciseName}/{exerciseType}/abort/{sessionId}")
    public ResponseEntity<?> abortSession(final Exercise exercise, @PathVariable SessionId sessionId) {
        DI.get(SessionService.class)
                .abort(sessionId);

        var body = DI.get(AudioPerfectPitchStatsRenderer.class)
                .renderPage(sessionId);

        return ok(body);
    }

    @PostMapping("/puzzle/perfect-pitch/audio/guess")
    public ResponseEntity<?> guess(@RequestBody final AudioPerfectPitchGuessRequest body, @RequestParam(required = true) final SessionId sessionId) {
        try {
            DI.get(AudioPerfectPitchSessionService.class)
                    .guessViaTextNote(body.note());
        } catch (InvalidTextNoteException e) {
            // TODO InvalidTextNoteException
            return badRequest().body("InvalidTextNoteException");
        } catch (OutOfRangeTextNoteException e) {
            // TODO OutOfRangeTextNoteException
            return badRequest().body("OutOfRangeTextNoteException");
        }

        persistQueuedEventLogs(sessionId);
        var response = DI.get(PuzzleGuessingResponseBuilder.class)
                .build(AUDIO_PERFECT_PITCH_EXERCISE, sessionId);

        return ok(response);
    }
}

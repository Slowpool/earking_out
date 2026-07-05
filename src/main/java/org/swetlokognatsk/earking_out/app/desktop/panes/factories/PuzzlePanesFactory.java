package org.swetlokognatsk.earking_out.app.desktop.panes.factories;

import java.util.UUID;
import org.swetlokognatsk.earking_out.app.desktop.panes.PuzzlePane;
import org.swetlokognatsk.earking_out.app.desktop.panes.perfectpitch.puzzle.AudioPerfectPitchPane;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.perfectpitch.AudioPerfectPitchConfigDTO;
import org.swetlokognatsk.earking_out.core.ports.DI;
import org.swetlokognatsk.earking_out.core.ports.session.SessionRepository;

public final class PuzzlePanesFactory {
    protected final SessionRepository sessionRepository;

    public PuzzlePanesFactory() {
        sessionRepository = DI.get(SessionRepository.class);
    }

    public PuzzlePane<?> create(final UUID sessionId, final double width, final double height) {
        var session = sessionRepository.get(sessionId);
        var puzzleConfigDto = session.getPuzzleConfig();
        var exercise = puzzleConfigDto.exercise;

        var puzzlePane = switch (exercise) {
        // // TODO it's too cumbersome, how 'bout other ways
        // case VisualPerfectPitchExercise e -> {
        //     var pane = new VisualPerfectPitchPane(sessionId, width, height);
        //     var castedPane = (PuzzlePane<PCDTO>) pane;
        //     yield castedPane;
        // }
        // TODO move `new AudioClipHintPlayer()` in the business logic
        case AudioPerfectPitchExercise e -> new AudioPerfectPitchPane(sessionId, (AudioPerfectPitchConfigDTO) puzzleConfigDto, width, height);
        default -> throw new RuntimeException("unkown exercise: " + exercise);
        };

        return puzzlePane;
    }
}

package org.swetlokognatsk.earking_out.app.desktop.panes.factories;

import org.swetlokognatsk.earking_out.app.desktop.panes.PuzzlePane;
import org.swetlokognatsk.earking_out.app.desktop.panes.perfectpitch.puzzle.AudioPerfectPitchPane;
import org.swetlokognatsk.earking_out.app.desktop.panes.perfectpitch.puzzle.VisualPerfectPitchPane;
import org.swetlokognatsk.earking_out.app.desktop.services.AudioClipHintPlayer;
import org.swetlokognatsk.earking_out.core.domain.model.Session;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.VisualPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.PuzzleConfigDTO;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.perfectpitch.AudioPerfectPitchConfigDTO;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.perfectpitch.VisualPerfectPitchConfigDTO;

public final class PuzzlePanesFactory {

    private PuzzlePanesFactory() {
    }

    public static <PCDTO extends PuzzleConfigDTO<?>> PuzzlePane<?, PCDTO, ?, ?, ?> create(Session<PCDTO> session, double width, double height) {
        var exercise = session.puzzleConfigDto().exercise;
        var puzzlePane = switch (exercise) {
        // TODO it's too cumbersome, how 'bout other ways
        case VisualPerfectPitchExercise e -> {
            var castedSession = (Session<VisualPerfectPitchConfigDTO>) session;
            var pane = new VisualPerfectPitchPane(castedSession, width, height);
            var castedPane = (PuzzlePane<?, PCDTO, ?, ?, ?>) pane;
            yield castedPane;
        }
        case AudioPerfectPitchExercise e -> {
            var castedSession = (Session<AudioPerfectPitchConfigDTO>) session;
            var pane = new AudioPerfectPitchPane(castedSession, width, height, new AudioClipHintPlayer());
            var castedPane = (PuzzlePane<?, PCDTO, ?, ?, ?>) pane;
            yield castedPane;
        }
        default -> throw new RuntimeException("unkown exercise: " + exercise);
        };

        return puzzlePane;
    }
}

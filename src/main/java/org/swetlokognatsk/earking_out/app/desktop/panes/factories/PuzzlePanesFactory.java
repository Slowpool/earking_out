package org.swetlokognatsk.earking_out.app.desktop.panes.factories;

import org.swetlokognatsk.earking_out.app.desktop.panes.PuzzlePane;
import org.swetlokognatsk.earking_out.app.desktop.panes.perfectpitch.puzzle.AudioPerfectPitchPane;
import org.swetlokognatsk.earking_out.app.desktop.panes.perfectpitch.puzzle.VisualPerfectPitchPane;
import org.swetlokognatsk.earking_out.app.desktop.services.AudioClipHintPlayer;
import org.swetlokognatsk.earking_out.core.domain.model.Session;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.VisualPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfigAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.AudioPerfectPitchConfigAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.VisualPerfectPitchConfigAggregate;

public final class PuzzlePanesFactory {

    private PuzzlePanesFactory() {
    }

    public static <PC extends PuzzleConfigAggregate<?>> PuzzlePane<?, PC, ?, ?, ?> create(Session<PC> session, double width, double height) {
        var exercise = session.puzzleConfig().exercise;
        var puzzlePane = switch (exercise) {
        // TODO it's too cumbersome, how 'bout other ways
        case VisualPerfectPitchExercise e -> {
            var castedSession = (Session<VisualPerfectPitchConfigAggregate>) session;
            var pane = new VisualPerfectPitchPane(castedSession, width, height);
            var castedPane = (PuzzlePane<?, PC, ?, ?, ?>) pane;
            yield castedPane;
        }
        case AudioPerfectPitchExercise e -> {
            var castedSession = (Session<AudioPerfectPitchConfigAggregate>) session;
            var pane = new AudioPerfectPitchPane(castedSession, width, height, new AudioClipHintPlayer());
            var castedPane = (PuzzlePane<?, PC, ?, ?, ?>) pane;
            yield castedPane;
        }
        default -> throw new RuntimeException("unkown exercise: " + exercise);
        };

        return puzzlePane;
    }
}

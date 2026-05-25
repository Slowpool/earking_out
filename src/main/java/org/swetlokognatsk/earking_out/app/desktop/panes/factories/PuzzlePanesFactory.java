package org.swetlokognatsk.earking_out.app.desktop.panes.factories;

import org.swetlokognatsk.earking_out.app.desktop.panes.PuzzlePane;
import org.swetlokognatsk.earking_out.app.desktop.panes.perfectpitch.puzzle.AudioPerfectPitchPane;
import org.swetlokognatsk.earking_out.app.desktop.panes.perfectpitch.puzzle.VisualPerfectPitchPane;
import org.swetlokognatsk.earking_out.app.desktop.services.AudioclipHintPlayer;
import org.swetlokognatsk.earking_out.core.domain.model.Session;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfig;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.typed.AudioPerfectPitchConfig;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.typed.VisualPerfectPitchConfig;

public final class PuzzlePanesFactory {
    public static <PC extends PuzzleConfig<?>> PuzzlePane<?, PC, ?, ?, ?> create(Session<PC> session, double width, double height) {
        var exercise = session.puzzleConfig().exercise;

        var puzzlePane = switch (exercise.name) {
        case PERFECT_PITCH -> switch (exercise.type) {
        // TODO it's too cumbersome, how 'bout other ways
        case VISUAL -> {
            var castedSession = (Session<VisualPerfectPitchConfig>) session;
            var pane = new VisualPerfectPitchPane(castedSession, width, height);
            var castedPane = (PuzzlePane<?, PC, ?, ?, ?>) pane;
            yield castedPane;
        }
        case AUDIO -> {
            var castedSession = (Session<AudioPerfectPitchConfig>) session;
            var pane = new AudioPerfectPitchPane(castedSession, width, height, new AudioclipHintPlayer());
            var castedPane = (PuzzlePane<?, PC, ?, ?, ?>) pane;
            yield castedPane;
        }
        };
        default -> throw new RuntimeException("unkown exercise for puzzle pane" + exercise.name + " " + exercise.type);
        };

        return puzzlePane;
    }
}

package org.swetlokognatsk.earking_out.app.desktop.panes.factories;

import org.swetlokognatsk.earking_out.app.desktop.panes.PuzzlePane;
import org.swetlokognatsk.earking_out.app.desktop.panes.perfectpitch.puzzle.AudioPerfectPitchPane;
import org.swetlokognatsk.earking_out.app.desktop.panes.perfectpitch.puzzle.VisualPerfectPitchPane;
import org.swetlokognatsk.earking_out.core.domain.model.Session;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfig;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.typed.AudioPerfectPitchConfig;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.typed.VisualPerfectPitchConfig;

public final class PuzzlePanesFactory {
    public static <PC extends PuzzleConfig<?>, PP extends PuzzlePane<PC>> PP create(Session<PC> session, double width, double height) {
        var exercise = session.puzzleConfig().exercise;

        var puzzlePane =  switch (exercise.name) {
        case PERFECT_PITCH -> switch (exercise.type) {
        // TODO what to do with warning
        case VISUAL ->  new VisualPerfectPitchPane((Session<VisualPerfectPitchConfig>) session, width, height);
        case AUDIO -> new AudioPerfectPitchPane((Session<AudioPerfectPitchConfig>) session, width, height);
        };
        default -> throw new RuntimeException("unkown exercise for puzzle pane" + exercise.name + " " + exercise.type);
        };
        
        return (PP)puzzlePane;
    }
}

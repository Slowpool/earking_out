package org.swetlokognatsk.earking_out.app.desktop.panes.factories;

import org.swetlokognatsk.earking_out.app.desktop.panes.ConfigPane;
import org.swetlokognatsk.earking_out.app.desktop.panes.perfectpitch.config.AudioPerfectPitchConfigPane;
import org.swetlokognatsk.earking_out.app.desktop.panes.perfectpitch.config.VisualPerfectPitchConfigPane;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfig;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.typed.AudioPerfectPitchConfig;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.typed.VisualPerfectPitchConfig;

public final class ConfigPanesFactory {
    public static <E extends Exercise, PC extends PuzzleConfig<E>, CP extends ConfigPane<E, PC>> CP create(final PC puzzleConfig, final double width, final double height) {
        var exercise = puzzleConfig.exercise;
        return switch (exercise.name) {
        case PERFECT_PITCH -> switch (exercise.type) {
        case VISUAL -> (CP) new VisualPerfectPitchConfigPane((VisualPerfectPitchConfig) puzzleConfig, width, height);
        case AUDIO -> (CP) new AudioPerfectPitchConfigPane((AudioPerfectPitchConfig) puzzleConfig, width, height);
        };
        // case MELODIC_INTERVALS -> switch (exercise.type) {
        //     case VISUAL -> new VisualPerfectPitchConfigPane(puzzleConfig, width, height);
        //     case AUDIO -> new VisualPerfectPitchConfigPane(puzzleConfig, width, height);
        // };
        default -> throw new RuntimeException("unknown exercise for config pane: " + exercise.name);
        };
    }
}

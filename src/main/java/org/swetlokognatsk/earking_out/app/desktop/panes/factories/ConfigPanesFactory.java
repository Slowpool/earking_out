package org.swetlokognatsk.earking_out.app.desktop.panes.factories;

import org.swetlokognatsk.earking_out.app.desktop.panes.ConfigPane;
import org.swetlokognatsk.earking_out.app.desktop.panes.perfectpitch.config.AudioPerfectPitchConfigPane;
import org.swetlokognatsk.earking_out.app.desktop.panes.perfectpitch.config.VisualPerfectPitchConfigPane;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.VisualPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfig;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.AudioPerfectPitchConfig;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.VisualPerfectPitchConfig;

public final class ConfigPanesFactory {

    private ConfigPanesFactory() {
    }

    public static <E extends Exercise, PC extends PuzzleConfig<E>, CP extends ConfigPane<E, PC>> CP create(final PC puzzleConfig, final double width, final double height) {
        var exercise = puzzleConfig.exercise;
        return switch (exercise) {
            case VisualPerfectPitchExercise e -> (CP) new VisualPerfectPitchConfigPane((VisualPerfectPitchConfig) puzzleConfig, width, height);
        case AudioPerfectPitchExercise e -> (CP) new AudioPerfectPitchConfigPane((AudioPerfectPitchConfig) puzzleConfig, width, height);
        default -> throw new RuntimeException("unknown exercise: " + exercise);
        };
        // case MELODIC_INTERVALS -> switch (exercise.type) {
        //     case VISUAL -> new VisualPerfectPitchConfigPane(puzzleConfig, width, height);
        //     case AUDIO -> new VisualPerfectPitchConfigPane(puzzleConfig, width, height);
        // };
    }
}

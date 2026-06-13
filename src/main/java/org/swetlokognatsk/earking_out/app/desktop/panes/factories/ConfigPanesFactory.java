package org.swetlokognatsk.earking_out.app.desktop.panes.factories;

import org.swetlokognatsk.earking_out.app.desktop.panes.ConfigPane;
import org.swetlokognatsk.earking_out.app.desktop.panes.perfectpitch.config.AudioPerfectPitchConfigPane;
import org.swetlokognatsk.earking_out.app.desktop.panes.perfectpitch.config.VisualPerfectPitchConfigPane;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.VisualPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfigAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.AudioPerfectPitchConfigAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.VisualPerfectPitchConfigAggregate;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.PuzzleConfigDTO;

public final class ConfigPanesFactory {

    private ConfigPanesFactory() {
    }

    public static <E extends Exercise, PCDTO extends PuzzleConfigDTO<E>, CP extends ConfigPane<E, PCDTO>> CP create(final PCDTO puzzleConfigDTO, final double width, final double height) {
        var exercise = puzzleConfigDTO.exercise;
        return switch (exercise) {
            case VisualPerfectPitchExercise e -> (CP) new VisualPerfectPitchConfigPane((VisualPerfectPitchConfigAggregate) puzzleConfigDTO, width, height);
        case AudioPerfectPitchExercise e -> (CP) new AudioPerfectPitchConfigPane((AudioPerfectPitchConfigAggregate) puzzleConfigDTO, width, height);
        default -> throw new RuntimeException("unknown exercise: " + exercise);
        };
        // case MELODIC_INTERVALS -> switch (exercise.type) {
        //     case VISUAL -> new VisualPerfectPitchConfigPane(puzzleConfig, width, height);
        //     case AUDIO -> new VisualPerfectPitchConfigPane(puzzleConfig, width, height);
        // };
    }
}

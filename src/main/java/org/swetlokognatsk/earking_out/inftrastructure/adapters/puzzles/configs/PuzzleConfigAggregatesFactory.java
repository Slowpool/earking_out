package org.swetlokognatsk.earking_out.inftrastructure.adapters.puzzles.configs;

import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfig;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.aggregates.PuzzleConfigAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.aggregates.perfectpitch.AudioPerfectPitchConfigAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.aggregates.perfectpitch.VisualPerfectPitchConfigAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.AudioPerfectPitchConfig;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.VisualPerfectPitchConfig;
import org.swetlokognatsk.earking_out.core.ports.DI;
import org.swetlokognatsk.earking_out.core.ports.piano.PianoKeyboardRepository;

public final class PuzzleConfigAggregatesFactory {

    private PuzzleConfigAggregatesFactory() {
    }

    public static <PCA extends PuzzleConfigAggregate<?>, PC extends PuzzleConfig<?>> PCA create(PC puzzleConfig) {
        var exercise = puzzleConfig.exercise;
        var pianoKeyboardRepository = DI.get(PianoKeyboardRepository.class);
        var aggregate = switch (exercise.name) {
        case PERFECT_PITCH -> switch (exercise.type) {
        case VISUAL -> new VisualPerfectPitchConfigAggregate((VisualPerfectPitchConfig) puzzleConfig, pianoKeyboardRepository);
        case AUDIO -> new AudioPerfectPitchConfigAggregate((AudioPerfectPitchConfig) puzzleConfig, pianoKeyboardRepository);
        default -> throw new RuntimeException("unknown exercise type of puzzleConfig: " + exercise.type);
        };
        default -> throw new RuntimeException("unknown exercise of puzzleConfig: " + exercise.name);
        };
        return (PCA) aggregate;
    }

}

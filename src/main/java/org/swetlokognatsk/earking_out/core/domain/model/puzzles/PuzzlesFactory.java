package org.swetlokognatsk.earking_out.core.domain.model.puzzles;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.VisualPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfig;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.AudioPerfectPitchConfig;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.VisualPerfectPitchConfig;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.perfectpitch.AudioPerfectPitchPuzzle;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.perfectpitch.VisualPerfectPitchPuzzle;
import org.swetlokognatsk.earking_out.core.ports.puzzles.PuzzleGenerator;
import org.swetlokognatsk.earking_out.core.ports.puzzles.generators.perfectpitch.AudioPerfectPitchPuzzleGenerator;
import org.swetlokognatsk.earking_out.core.ports.puzzles.generators.perfectpitch.VisualPerfectPitchPuzzleGenerator;

public final class PuzzlesFactory {

    private PuzzlesFactory() {
    }

    public static <E extends Exercise, PC extends PuzzleConfig<E>, PG extends PuzzleGenerator, P extends Puzzle<E, PC, ?, PG>> P create(PC puzzleConfig, PG puzzleGenerator) {
        var exercise = puzzleConfig.exercise;
        var puzzle = switch (exercise.name) {
        case PERFECT_PITCH -> switch (exercise.type) {
        case VISUAL -> new VisualPerfectPitchPuzzle((VisualPerfectPitchConfig) puzzleConfig, (VisualPerfectPitchPuzzleGenerator) puzzleGenerator);
        case AUDIO -> new AudioPerfectPitchPuzzle((AudioPerfectPitchConfig) puzzleConfig, (AudioPerfectPitchPuzzleGenerator) puzzleGenerator);
        default -> throw new RuntimeException("unknown exercise type for puzzle: " + exercise.type);
        };
        default -> throw new RuntimeException("unknown exercise for puzzle: " + exercise.name);
        };
        return (P) puzzle;
    }
}

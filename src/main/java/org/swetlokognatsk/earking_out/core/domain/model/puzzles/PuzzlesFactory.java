package org.swetlokognatsk.earking_out.core.domain.model.puzzles;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.VisualPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfigAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.AudioPerfectPitchConfigAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.VisualPerfectPitchConfigAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.perfectpitch.AudioPerfectPitchPuzzle;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.perfectpitch.VisualPerfectPitchPuzzle;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.PuzzleConfigDTO;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.perfectpitch.AudioPerfectPitchConfigDTO;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.perfectpitch.VisualPerfectPitchConfigDTO;
import org.swetlokognatsk.earking_out.core.ports.puzzles.PuzzleGenerator;
import org.swetlokognatsk.earking_out.core.ports.puzzles.generators.perfectpitch.AudioPerfectPitchPuzzleGenerator;
import org.swetlokognatsk.earking_out.core.ports.puzzles.generators.perfectpitch.VisualPerfectPitchPuzzleGenerator;

public final class PuzzlesFactory {

    private PuzzlesFactory() {
    }

    public static <E extends Exercise, PCDTO extends PuzzleConfigDTO<E>, PG extends PuzzleGenerator, P extends Puzzle<E, PCDTO, ?, PG>> P create(PCDTO puzzleConfig, PG puzzleGenerator) {
        var exercise = puzzleConfig.exercise;
        var puzzle = switch (exercise) {
        case VisualPerfectPitchExercise e -> new VisualPerfectPitchPuzzle((VisualPerfectPitchConfigDTO) puzzleConfig, (VisualPerfectPitchPuzzleGenerator) puzzleGenerator);
        case AudioPerfectPitchExercise e -> new AudioPerfectPitchPuzzle((AudioPerfectPitchConfigDTO) puzzleConfig, (AudioPerfectPitchPuzzleGenerator) puzzleGenerator);
        default -> throw new RuntimeException("unknown exercise for puzzle: " + exercise);
        };
        return (P) puzzle;
    }
}

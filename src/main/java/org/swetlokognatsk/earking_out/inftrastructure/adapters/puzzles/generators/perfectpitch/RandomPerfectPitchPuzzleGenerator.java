package org.swetlokognatsk.earking_out.inftrastructure.adapters.puzzles.generators.perfectpitch;

import java.util.Arrays;
import java.util.Random;
import org.apache.commons.lang3.ArrayUtils;
import org.swetlokognatsk.earking_out.core.domain.model.Solution;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.perfectpitch.PerfectPitchConfigDTO;
import org.swetlokognatsk.earking_out.core.domain.services.domain.puzzles.generators.ConfigBasedPuzzleGenerator;
import org.swetlokognatsk.earking_out.core.ports.puzzles.generators.perfectpitch.PerfectPitchPuzzleGenerator;

public abstract class RandomPerfectPitchPuzzleGenerator<PCDTO extends PerfectPitchConfigDTO<?>> extends ConfigBasedPuzzleGenerator<PCDTO> implements PerfectPitchPuzzleGenerator<PCDTO> {
    protected final Random random;
    protected final Solution[] possibleSolutions;

    public RandomPerfectPitchPuzzleGenerator(final PCDTO puzzleConfig) {
        super(puzzleConfig);

        random = new Random();
        possibleSolutions = buildPossibleSolutions();
    }

    protected Solution[] buildPossibleSolutions() {
        var stream = Arrays.stream(puzzleConfigDto.normalizedNotesForPuzzle);
        Solution[] possibleSolutions = stream.map(possibleNote -> new Solution(String.valueOf(possibleNote.value))).toArray(Solution[]::new);
        return possibleSolutions;
    }

    // TODO actually all logic in current class (except this method) is core logic, whereas this method should be implemented via infrastructure service
    public Solution generateSolution() {
        var solutionValue = random.nextInt(0, possibleSolutions.length);
        var solution = possibleSolutions[solutionValue];
        return solution;
    }
}

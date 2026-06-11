package org.swetlokognatsk.earking_out.inftrastructure.adapters.puzzles.generators.perfectpitch;

import java.util.Arrays;
import java.util.Random;
import org.apache.commons.lang3.ArrayUtils;
import org.swetlokognatsk.earking_out.core.domain.model.Solution;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.PerfectPitchConfigAggregate;
import org.swetlokognatsk.earking_out.core.domain.services.domain.puzzles.generators.ConfigBasedPuzzleGenerator;
import org.swetlokognatsk.earking_out.core.ports.puzzles.generators.perfectpitch.PerfectPitchPuzzleGenerator;

public abstract class RandomPerfectPitchPuzzleGenerator<PC extends PerfectPitchConfigAggregate<?>> extends ConfigBasedPuzzleGenerator<PC> implements PerfectPitchPuzzleGenerator<PC> {
    protected final Random random;
    protected final Solution[] possibleSolutions;

    public RandomPerfectPitchPuzzleGenerator(final PC puzzleConfig) {
        super(puzzleConfig);

        random = new Random();
        possibleSolutions = buildPossibleSolutions();
    }

    protected Solution[] buildPossibleSolutions() {
        Byte[] ByteNotes = ArrayUtils.toObject(puzzleConfig.normalizedNotesForPuzzle);
        Solution[] possibleSolutions = Arrays.stream(ByteNotes).map(possibleNote -> new Solution(String.valueOf(possibleNote))).toArray(Solution[]::new);
        return possibleSolutions;
    }

    // TODO actually all logic in current class (except this method) is core logic, whereas this method should be implemented via infrastructure service
    public Solution generateSolution() {
        var solutionValue = random.nextInt(0, possibleSolutions.length);
        var solution = possibleSolutions[solutionValue];
        return solution;
    }
}

package org.swetlokognatsk.earking_out.inftrastructure.adapters.puzzles.generators.perfectpitch;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.Stream;
import org.apache.commons.lang3.ArrayUtils;
import org.swetlokognatsk.earking_out.core.domain.model.Solution;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.PerfectPitchConfig;
import org.swetlokognatsk.earking_out.core.domain.services.puzzles.generators.ConfigBasedPuzzleGenerator;
import org.swetlokognatsk.earking_out.core.ports.puzzles.generators.perfectpitch.PerfectPitchPuzzleGenerator;

public abstract class RandomPerfectPitchPuzzleGenerator<PC extends PerfectPitchConfig<?>> extends ConfigBasedPuzzleGenerator<PC> implements PerfectPitchPuzzleGenerator<PC> {
    protected final Random random;
    protected final Solution[] possibleSolutions;

    public RandomPerfectPitchPuzzleGenerator(final PC puzzleConfig) {
        super(puzzleConfig);

        random = new Random();
        possibleSolutions = buildPossibleSolutions();
    }

    protected Solution[] buildPossibleSolutions() {
        // TODO weweeeel., sosuch a cumbresome
        Byte[] ByteNotes = ArrayUtils.toObject(puzzleConfig.normalizedNotesForPuzzle);
        Stream<Byte> stream = Arrays.stream(ByteNotes);
        Stream<Solution> map = stream.map(possibleNote -> new Solution(String.valueOf(possibleNote)));
        Solution[] possibleSolutions = map.toArray(Solution[]::new);
        return possibleSolutions;
    }

    public Solution generateSolution() {
        var solutionValue = random.nextInt(0, possibleSolutions.length);
        var solution = possibleSolutions[solutionValue];
        return solution;
    }
}

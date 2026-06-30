package org.swetlokognatsk.earking_out.inftrastructure.adapters.puzzles.generators.perfectpitch;

import java.util.Arrays;
import java.util.Random;
import java.util.function.IntFunction;

import org.apache.commons.lang3.ArrayUtils;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;
import org.swetlokognatsk.earking_out.core.domain.model.solutions.Solution;
import org.swetlokognatsk.earking_out.core.domain.model.solutions.perfectpitch.PerfectPitchSolution;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.perfectpitch.PerfectPitchConfigDTO;
import org.swetlokognatsk.earking_out.core.domain.services.domain.puzzles.generators.ConfigBasedSolutionGenerator;
import org.swetlokognatsk.earking_out.core.ports.puzzles.generators.perfectpitch.PerfectPitchSolutionGenerator;

public abstract class RandomPerfectPitchSolutionGenerator<S extends PerfectPitchSolution, PCDTO extends PerfectPitchConfigDTO<?>> extends ConfigBasedSolutionGenerator<S, PCDTO> implements PerfectPitchSolutionGenerator<S, PCDTO> {
    protected final Random random;
    protected final S[] possibleSolutions;

    protected abstract S buildPossibleSolution(final PianoKeyNumber keyNumber);
    protected abstract IntFunction<S[]> getArrayConstructor();

    public RandomPerfectPitchSolutionGenerator(final PCDTO puzzleConfig) {
        super(puzzleConfig);

        random = new Random();
        possibleSolutions = buildPossibleSolutions();
    }

    protected S[] buildPossibleSolutions() {
        var stream = Arrays.stream(puzzleConfigDto.normalizedNotesForPuzzle);
        Solution[] possibleSolutions = stream.map(possibleNote -> buildPossibleSolution(possibleNote)).toArray(getArrayConstructor());
        return (S[]) possibleSolutions;
    }

    // TODO actually all logic in current class (except this method) is core logic, whereas this method should be implemented via infrastructure service
    public S generate() {
        var solutionValue = random.nextInt(0, possibleSolutions.length);
        var solution = possibleSolutions[solutionValue];
        return (S) solution;
    }
}

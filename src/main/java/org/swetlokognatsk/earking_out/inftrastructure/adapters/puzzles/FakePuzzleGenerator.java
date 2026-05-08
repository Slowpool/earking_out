package org.swetlokognatsk.earking_out.inftrastructure.adapters.puzzles;

import org.swetlokognatsk.earking_out.core.domain.model.Solution;
import org.swetlokognatsk.earking_out.core.ports.puzzles.IPuzzleGenerator;

public class FakePuzzleGenerator implements IPuzzleGenerator {
    public static String fakeSolution;

    public Solution generateSolution() {
        return new Solution(fakeSolution);
    }
}

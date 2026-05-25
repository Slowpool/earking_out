package org.swetlokognatsk.earking_out.inftrastructure.adapters.puzzles.generators;

import org.swetlokognatsk.earking_out.core.domain.model.Solution;
import org.swetlokognatsk.earking_out.core.ports.puzzles.PuzzleGenerator;

public abstract class FakePuzzleGenerator implements PuzzleGenerator {
    public static String fakeSolution = "";

    public Solution generateSolution() {
        return new Solution(fakeSolution);
    }
}

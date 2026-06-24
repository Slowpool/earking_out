package org.swetlokognatsk.earking_out.inftrastructure.adapters.puzzles.generators;

import org.swetlokognatsk.earking_out.core.domain.model.Solution;
import org.swetlokognatsk.earking_out.core.ports.puzzles.SolutionGenerator;

public abstract class FakeSolutionGenerator implements SolutionGenerator {
    public static String fakeSolution = "";

    public Solution generate() {
        return new Solution(fakeSolution);
    }
}

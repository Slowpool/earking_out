package org.swetlokognatsk.earking_out.core.ports.puzzles;

import org.swetlokognatsk.earking_out.core.domain.model.Solution;

public class TestPuzzleGenerator implements PuzzleGenerator {
    public Solution generateSolution() {
        return new Solution("");
    }
}

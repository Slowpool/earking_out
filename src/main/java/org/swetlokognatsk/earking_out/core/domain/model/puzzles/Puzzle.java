package org.swetlokognatsk.earking_out.core.domain.model.puzzles;

import org.swetlokognatsk.earking_out.core.domain.model.Guess;
import org.swetlokognatsk.earking_out.core.domain.model.Solution;
import org.swetlokognatsk.earking_out.core.domain.model.PuzzleConfig;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.ports.puzzles.IPuzzleGenerator;

public abstract class Puzzle {
    protected final Solution solution;
    public final Exercise exercise;
    public final PuzzleConfig config;

    public Puzzle(Exercise exercise, PuzzleConfig config, IPuzzleGenerator puzzleGenerator) {
        this.exercise = exercise;
        this.config = config;
        this.solution = puzzleGenerator.generateSolution();
        // this.hint = attachHint();
    }

    public boolean guess(Guess guess) {
        return guess.equals(solution);
    }
}

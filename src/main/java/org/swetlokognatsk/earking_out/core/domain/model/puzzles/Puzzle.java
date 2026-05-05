package org.swetlokognatsk.earking_out.core.domain.model.puzzles;

import org.swetlokognatsk.earking_out.core.domain.model.Guess;
import org.swetlokognatsk.earking_out.core.domain.model.Solution;
import org.swetlokognatsk.earking_out.core.domain.model.UserRestrictions;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.ports.puzzles.IPuzzleGenerator;

public abstract class Puzzle {
    final Solution solution;
    public final Exercise exercise;
    public final UserRestrictions restrictions;

    public Puzzle(Exercise exercise, UserRestrictions restrictions, IPuzzleGenerator puzzleGenerator) {
        this.exercise = exercise;
        this.restrictions = restrictions;
        this.solution = puzzleGenerator.generateSolution();
    }

    public boolean guess(Guess guess) {
        return guess.equals(solution);
    }
}

package org.swetlokognatsk.earking_out.core.domain.model.puzzles;

import org.swetlokognatsk.earking_out.core.domain.model.Solution;
import org.swetlokognatsk.earking_out.core.domain.model.UserRestrictions;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.ports.puzzles.IPuzzleGenerator;

public abstract class Puzzle {
    final Solution solution;
    private final IPuzzleGenerator puzzleGenerator;
    public final Exercise exercise;
    public final UserRestrictions restrictions;

    public Puzzle(Exercise exercise, UserRestrictions restrictions, IPuzzleGenerator puzzleGenerator) {
        this.exercise = exercise;
        this.restrictions = restrictions;
        // TODO should it be assigned to class variable if it is needed only in constructor
        this.puzzleGenerator = puzzleGenerator;
        this.solution = puzzleGenerator.generateSolution();
    }


}

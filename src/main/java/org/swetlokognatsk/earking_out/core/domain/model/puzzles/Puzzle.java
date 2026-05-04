package org.swetlokognatsk.earking_out.core.domain.model.puzzles;

import org.swetlokognatsk.earking_out.core.domain.model.Solution;
import org.swetlokognatsk.earking_out.core.domain.model.UserRestrictions;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;

public abstract class Puzzle {
    final Solution solution;
    public final Exercise exercise;
    public final UserRestrictions restrictions;

    public Puzzle(Exercise exercise, UserRestrictions restrictions) {
        this.exercise = exercise;
        this.restrictions = restrictions;
        this.solution = generateSolution();
    }

    protected abstract Solution generateSolution();
}

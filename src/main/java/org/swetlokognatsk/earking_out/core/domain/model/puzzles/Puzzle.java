package org.swetlokognatsk.earking_out.core.domain.model.puzzles;

import java.io.Serializable;
import java.util.Objects;
import org.swetlokognatsk.earking_out.core.domain.model.base.ValueObject;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.solutions.Solution;

public abstract class Puzzle<E extends Exercise, S extends Solution> extends ValueObject implements Serializable {
    private static final long serialVersionUID = 1L;

    public final E exercise;
    public final S solution;

    public Puzzle(final E exercise, final S solution) {
        this.exercise = Objects.requireNonNull(exercise, "exercise cannot be null");
        this.solution = Objects.requireNonNull(solution, "solution cannot be null");
    }

    public boolean guess(final S guess) {
        return guess.equals(solution);
    }

    public int hashCode() {
        return exercise.hashCode() + solution.hashCode();
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Puzzle)) {
            return false;
        }
        var other = (Puzzle<?, ?>) obj;
        return exercise == other.exercise && solution.equals(other.solution);
    }

}

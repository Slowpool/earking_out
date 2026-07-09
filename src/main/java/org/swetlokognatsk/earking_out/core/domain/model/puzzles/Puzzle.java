package org.swetlokognatsk.earking_out.core.domain.model.puzzles;

import java.io.Serializable;
import java.util.Objects;

import org.swetlokognatsk.earking_out.core.domain.model.base.ValueObject;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.solutions.Solution;

public abstract class Puzzle<E extends Exercise, S extends Solution> extends ValueObject {
    public final E exercise;
    public final S solution;

    // TODO hashCode, equals
    public Puzzle(final E exercise, final S solution) {
        Objects.requireNonNull(exercise, "exercise cannot be null");
        Objects.requireNonNull(solution, "solution cannot be null");

        this.exercise = exercise;
        this.solution = solution;
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

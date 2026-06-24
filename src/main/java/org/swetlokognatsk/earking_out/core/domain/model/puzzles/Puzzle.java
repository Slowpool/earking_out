package org.swetlokognatsk.earking_out.core.domain.model.puzzles;

import java.util.Objects;
import org.swetlokognatsk.earking_out.core.domain.model.Guess;
import org.swetlokognatsk.earking_out.core.domain.model.Solution;
import org.swetlokognatsk.earking_out.core.domain.model.base.ValueObject;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.hints.Hint;

public abstract class Puzzle<E extends Exercise, H extends Hint> extends ValueObject {
    public final E exercise;
    public final Solution solution;
    public final H hint;

    // TODO hashCode, equals
    public Puzzle(final E exercise, final Solution solution, final H hint) {
        Objects.requireNonNull(exercise, "exercise cannot be null");
        Objects.requireNonNull(solution, "solution cannot be null");
        Objects.requireNonNull(hint, "solution cannot be null");

        this.exercise = exercise;
        this.solution = solution;
        this.hint = hint;
    }

    public boolean guess(final Guess guess) {
        return guess.equals(solution);
    }

    public int hashCode() {
        return exercise.hashCode() + solution.hashCode() + hint.hashCode();
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Puzzle)) {
            return false;
        }
        var other = (Puzzle<?, ?>) obj;
        return exercise == other.exercise && solution.equals(other.solution) && hint.equals(other.hint);
    }

}

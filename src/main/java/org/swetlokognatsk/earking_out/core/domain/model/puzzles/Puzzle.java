package org.swetlokognatsk.earking_out.core.domain.model.puzzles;

import java.util.Objects;

import org.swetlokognatsk.earking_out.core.domain.model.Guess;
import org.swetlokognatsk.earking_out.core.domain.model.Hint;
import org.swetlokognatsk.earking_out.core.domain.model.Solution;
import org.swetlokognatsk.earking_out.core.domain.model.PuzzleConfig;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.ports.DI;
import org.swetlokognatsk.earking_out.core.ports.hints.IHintFinder;
import org.swetlokognatsk.earking_out.core.ports.puzzles.IPuzzleGenerator;

public abstract class Puzzle<E extends Exercise, PC extends PuzzleConfig, H extends Hint> {
    public final Solution solution;
    public final E exercise;
    public final PC config;
    public final H hint;

    public Puzzle(E exercise, PC config, IPuzzleGenerator puzzleGenerator) {
        Objects.requireNonNull(exercise, "Exercise cannot be null");
        Objects.requireNonNull(puzzleGenerator, "PuzzleGenerator cannot be null");
        this.exercise = exercise;
        this.config = config;
        this.solution = puzzleGenerator.generateSolution();
        // TODO it should be guaranted by underlying IHintFinder.find(this), then supress warning
        this.hint = (H)findHint();
    }

    private Hint findHint() {
        var hintFinder = DI.get(IHintFinder.class);
        return hintFinder.find(this);
    }

    public boolean guess(Guess guess) {
        return guess.equals(solution);
    }
}

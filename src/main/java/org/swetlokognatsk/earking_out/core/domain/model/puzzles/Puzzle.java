package org.swetlokognatsk.earking_out.core.domain.model.puzzles;

import java.util.Objects;

import org.swetlokognatsk.earking_out.core.domain.model.Guess;
import org.swetlokognatsk.earking_out.core.domain.model.Solution;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.hints.Hint;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfig;
import org.swetlokognatsk.earking_out.core.ports.DI;
import org.swetlokognatsk.earking_out.core.ports.hints.HintFinder;
import org.swetlokognatsk.earking_out.core.ports.puzzles.PuzzleGenerator;

public abstract class Puzzle<E extends Exercise, PC extends PuzzleConfig<E>, H extends Hint> {
    public final Solution solution;
    public final E exercise;
    public final PC config;
    public final H hint;

    public Puzzle(E exercise, PC config, PuzzleGenerator puzzleGenerator) {
        Objects.requireNonNull(exercise, "Exercise cannot be null");
        Objects.requireNonNull(puzzleGenerator, "PuzzleGenerator cannot be null");
        this.exercise = exercise;
        this.config = config;
        this.solution = puzzleGenerator.generateSolution();
        // TODO it should be guaranted by underlying IHintFinder.find(this), then supress warning
        this.hint = (H)findHint();
    }

    private Hint findHint() {
        var hintFinder = DI.get(HintFinder.class);
        return hintFinder.find(this);
    }

    public boolean guess(Guess guess) {
        return guess.equals(solution);
    }
}

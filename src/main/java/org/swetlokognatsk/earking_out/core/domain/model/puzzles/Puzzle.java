package org.swetlokognatsk.earking_out.core.domain.model.puzzles;

import org.swetlokognatsk.earking_out.core.domain.model.Guess;
import org.swetlokognatsk.earking_out.core.domain.model.Hint;
import org.swetlokognatsk.earking_out.core.domain.model.Solution;
import org.swetlokognatsk.earking_out.core.domain.model.PuzzleConfig;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.ports.DI;
import org.swetlokognatsk.earking_out.core.ports.hints.IHintMapper;
import org.swetlokognatsk.earking_out.core.ports.puzzles.IPuzzleGenerator;

public abstract class Puzzle<E extends Exercise, PC extends PuzzleConfig, H extends Hint> {
    protected final Solution solution;
    public final E exercise;
    public final PC config;
    protected final H hint;

    public Puzzle(E exercise, PC config, IPuzzleGenerator puzzleGenerator) {
        this.exercise = exercise;
        this.config = config;
        this.solution = puzzleGenerator.generateSolution();
        this.hint = findHint();
    }

    private H findHint() {
        var hintMapper = DI.get(IHintMapper.class);
        return hintMapper.map(this);
    }

    public boolean guess(Guess guess) {
        return guess.equals(solution);
    }

    public H getHint() {
        return hint;
    }
}

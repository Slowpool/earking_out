package org.swetlokognatsk.earking_out.inftrastructure.adapters.puzzles.configs;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfig;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfigAggregate;
import org.swetlokognatsk.earking_out.core.ports.config.PuzzleConfigAggregateRepository;

public class InMemoryPuzzleConfigAggregateRepository implements PuzzleConfigAggregateRepository {
    protected final InMemoryWritePuzzleConfigService writeService;
    protected final InMemoryReadPuzzleConfigService readService;

    public InMemoryPuzzleConfigAggregateRepository(final InMemoryWritePuzzleConfigService writeService, final InMemoryReadPuzzleConfigService readService) {
        this.writeService = writeService;
        this.readService = readService;
    }

    public <E extends Exercise, PCA extends PuzzleConfigAggregate<? extends PuzzleConfig<E>>> PCA get(E exercise) {
        var puzzleConfig = readService.fetch(exercise.getClass(), exercise);
        var puzzleConfigAggregate = new PuzzleConfigAggregate<>(puzzleConfig);
        return (PCA) puzzleConfigAggregate;
    }

    // TODO transaction stuff?
    public void save(PuzzleConfigAggregate<?> puzzleConfigAggregate) {
        var puzzleConfig = puzzleConfigAggregate.getPuzzleConfig();
        writeService.save(puzzleConfig);
    }
}

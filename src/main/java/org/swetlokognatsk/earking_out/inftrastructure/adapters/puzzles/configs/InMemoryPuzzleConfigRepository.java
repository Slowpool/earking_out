package org.swetlokognatsk.earking_out.inftrastructure.adapters.puzzles.configs;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfig;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.aggregates.PuzzleConfigAggregate;
import org.swetlokognatsk.earking_out.core.ports.config.PuzzleConfigRepository;

public class InMemoryPuzzleConfigRepository implements PuzzleConfigRepository {
    protected final InMemoryWritePuzzleConfigService writeService;
    protected final InMemoryReadPuzzleConfigService readService;

    public InMemoryPuzzleConfigRepository(final InMemoryWritePuzzleConfigService writeService, final InMemoryReadPuzzleConfigService readService) {
        this.writeService = writeService;
        this.readService = readService;
    }

    public <E extends Exercise, PCA extends PuzzleConfigAggregate<? extends PuzzleConfig<E>>> PCA get(E exercise) {
        var puzzleConfig = readService.fetch(exercise.getClass(), exercise);
        var puzzleConfigAggregate = PuzzleConfigAggregatesFactory.create(puzzleConfig);
        return (PCA) puzzleConfigAggregate;
    }

    // TODO transaction stuff?
    public void save(PuzzleConfigAggregate<?> puzzleConfigAggregate) {
        var puzzleConfig = puzzleConfigAggregate.getPuzzleConfig();
        writeService.save(puzzleConfig);
    }
}

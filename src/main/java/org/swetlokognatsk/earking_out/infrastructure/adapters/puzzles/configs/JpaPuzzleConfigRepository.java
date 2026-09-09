package org.swetlokognatsk.earking_out.infrastructure.adapters.puzzles.configs;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfigAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.factories.AbstractPuzzleConfigAggregatesFactory;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.PuzzleConfigDTO;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.PuzzleConfigDTOAssembler;
import org.swetlokognatsk.earking_out.core.ports.config.PuzzleConfigJsonSerializer;

public final class JpaPuzzleConfigRepository extends PersistentPuzzleConfigRepository {

    public JpaPuzzleConfigRepository(final AbstractPuzzleConfigAggregatesFactory abstractPuzzleConfigAggregatesFactory, final PuzzleConfigJsonSerializer puzzleConfigJsonSerializer, final PuzzleConfigDTOAssembler dtoAssembler, final InMemoryPuzzleConfigRepository cacheRepository) {
        super(abstractPuzzleConfigAggregatesFactory, puzzleConfigJsonSerializer, dtoAssembler, cacheRepository);
    }

    public void genericSave(final PuzzleConfigAggregate<?> puzzleConfigAggregate) {

    }

    public <E extends Exercise, PCDTO extends PuzzleConfigDTO<E>> PCDTO getPuzzleConfigDTO(E exercise) {

    }

    protected String getOrCreatePuzzleConfigJson(final Exercise exercise) {

    }

}

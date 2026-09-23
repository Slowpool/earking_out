package org.swetlokognatsk.earking_out.infrastructure.adapters.puzzles.configs;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.ExercisesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfigAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.factories.PuzzleConfigAggregatesFactoryResolver;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.factories.perfectpitch.AudioPerfectPitchConfigAggregatesFactory;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.PuzzleConfigDTO;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.PuzzleConfigDTOAssembler;
import org.swetlokognatsk.earking_out.core.ports.config.PuzzleConfigJsonSerializer;
import org.swetlokognatsk.earking_out.core.ports.config.PuzzleConfigRepository;
import static org.swetlokognatsk.earking_out.core.domain.model.exercises.ExercisesFactory.*;

abstract class PersistentPuzzleConfigRepository implements PuzzleConfigRepository {

    protected final PuzzleConfigAggregatesFactoryResolver puzzleConfigAggregatesFactoryResolver;
    protected final PuzzleConfigJsonSerializer puzzleConfigJsonSerializer;
    protected final PuzzleConfigDTOAssembler dtoAssembler;
    protected final InMemoryPuzzleConfigRepository cacheRepository;

    protected abstract String getOrCreatePuzzleConfigJson(final Exercise exercise);

    protected PuzzleConfigJsonSerializer getPuzzleConfigJsonSerializer() {
        return puzzleConfigJsonSerializer;
    }

    public PersistentPuzzleConfigRepository(final PuzzleConfigAggregatesFactoryResolver puzzleConfigAggregatesFactoryResolver, final PuzzleConfigJsonSerializer puzzleConfigJsonSerializer, final PuzzleConfigDTOAssembler dtoAssembler, final InMemoryPuzzleConfigRepository cacheRepository) {
        this.puzzleConfigAggregatesFactoryResolver = puzzleConfigAggregatesFactoryResolver;
        this.puzzleConfigJsonSerializer = puzzleConfigJsonSerializer;
        this.dtoAssembler = dtoAssembler;

        actualizeCache(cacheRepository);
        this.cacheRepository = cacheRepository;
    }

    protected final void actualizeCache(final InMemoryPuzzleConfigRepository cacheRepository) {
        for (var exercise : ExercisesFactory.getAll()) {
            var aggregate = genericGet(exercise);
            cacheRepository.save(aggregate);
        }
    }

    public final <E extends Exercise, PCA extends PuzzleConfigAggregate<E>> PCA genericGet(final E exercise) {
        var puzzleConfigJson = getOrCreatePuzzleConfigJson(exercise);
        return mapJsonToAggregate(exercise, puzzleConfigJson);
    }

    private <E extends Exercise, PCA extends PuzzleConfigAggregate<E>> PCA mapJsonToAggregate(final E exercise, final String puzzleConfigJson) {
        var aggregate = getPuzzleConfigJsonSerializer()
                .deserializePuzzleConfig(exercise, puzzleConfigJson);
        return (PCA) aggregate;
    }

    public <E extends Exercise, PCDTO extends PuzzleConfigDTO<E>> PCDTO getPuzzleConfigDTO(E exercise) {
        var puzzleConfig = genericGet(exercise);
        return dtoAssembler.assemble(puzzleConfig);
    }

    public PuzzleConfigAggregate<Exercise> get(final Exercise exercise) {
        // puzzle configs are always loaded in constructor of `this`, so there's no need in asking for them from database
        return cacheRepository.get(exercise);
    }

    public void save(final PuzzleConfigAggregate<Exercise> aggregate) {
        // no consistency because in-memory value does not matter after power outage
        genericSave(aggregate);
        cacheRepository.save(aggregate);
    }

    protected final void createAndSaveDefaultConfig(final Exercise exercise) {
        // TODO crutch
        AudioPerfectPitchConfigAggregatesFactory factory = puzzleConfigAggregatesFactoryResolver.resolveFactory(AUDIO_PERFECT_PITCH_EXERCISE);
        var newPuzzleConfig = factory.createDefault();
        genericSave(newPuzzleConfig);
    }
}

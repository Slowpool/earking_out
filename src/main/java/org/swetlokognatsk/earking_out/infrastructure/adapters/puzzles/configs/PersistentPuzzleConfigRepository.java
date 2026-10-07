package org.swetlokognatsk.earking_out.infrastructure.adapters.puzzles.configs;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.ExercisesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfigAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.factories.PuzzleConfigAggregatesFactoryResolver;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.factories.perfectpitch.AudioPerfectPitchConfigAggregatesFactory;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.piano.asdf;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.PuzzleConfigDTO;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.PuzzleConfigDTOAssembler;
import org.swetlokognatsk.earking_out.core.ports.config.PuzzleConfigJsonSerializer;
import org.swetlokognatsk.earking_out.core.ports.config.PuzzleConfigRepository;
import org.swetlokognatsk.earking_out.core.ports.puzzles.PuzzleConfigNotFoundException;
import lombok.AccessLevel;
import lombok.Getter;
import static org.swetlokognatsk.earking_out.core.domain.model.exercises.ExercisesFactory.*;
import org.springframework.dao.InvalidDataAccessApiUsageException;

@Getter(AccessLevel.PROTECTED)
abstract class PersistentPuzzleConfigRepository implements PuzzleConfigRepository {

    protected final PuzzleConfigAggregatesFactoryResolver puzzleConfigAggregatesFactoryResolver;
    protected final PuzzleConfigJsonSerializer puzzleConfigJsonSerializer;
    protected final PuzzleConfigDTOAssembler dtoAssembler;
    protected final InMemoryPuzzleConfigRepository cacheRepository;

    protected abstract String getOrCreatePuzzleConfigJson(final Exercise exercise);

    protected abstract void saveImpl(final PuzzleConfigAggregate<?> aggregate);

    public PersistentPuzzleConfigRepository(final PuzzleConfigAggregatesFactoryResolver puzzleConfigAggregatesFactoryResolver, final PuzzleConfigJsonSerializer puzzleConfigJsonSerializer, final PuzzleConfigDTOAssembler dtoAssembler, final InMemoryPuzzleConfigRepository cacheRepository) {
        this.puzzleConfigAggregatesFactoryResolver = puzzleConfigAggregatesFactoryResolver;
        this.puzzleConfigJsonSerializer = puzzleConfigJsonSerializer;
        this.dtoAssembler = dtoAssembler;

        this.cacheRepository = cacheRepository;
    }

    public final void actualizeCache() {
        for (var exercise : ExercisesFactory.getAll()) {
            var aggregate = genericGet(exercise);
            getCacheRepository()
                    .save(aggregate);
        }
    }

    public final <E extends Exercise, PCA extends PuzzleConfigAggregate<E>> PCA genericGet(final E exercise) {
        try {
            return getCached(exercise);
        } catch (PuzzleConfigNotFoundException e) {
            var puzzleConfigJson = getOrCreatePuzzleConfigJson(exercise);
            PCA puzzleConfig = mapJsonToAggregate(exercise, puzzleConfigJson);
            // yes, in case it was just created, it is already saved and that's the second save, but this is not as serious overhead to care about it right now.
            saveToCache((PuzzleConfigAggregate<Exercise>) puzzleConfig);
            return puzzleConfig;
        }
    }

    private <E extends Exercise, PCA extends PuzzleConfigAggregate<E>> PCA getCached(final E exercise) {
        return getCacheRepository()
                .genericGet(exercise);
    }

    private void saveToCache(final PuzzleConfigAggregate<Exercise> puzzleConfig) {
        getCacheRepository()
                .save(puzzleConfig);
    }

    private <E extends Exercise, PCA extends PuzzleConfigAggregate<E>> PCA mapJsonToAggregate(final E exercise, final String puzzleConfigJson) {
        var aggregate = getPuzzleConfigJsonSerializer()
                .deserializePuzzleConfig(exercise, puzzleConfigJson);
        return (PCA) aggregate;
    }

    public <E extends Exercise, PCDTO extends PuzzleConfigDTO<E>> PCDTO getPuzzleConfigDTO(E exercise) {
        var puzzleConfig = genericGet(exercise);
        return getDtoAssembler()
                .assemble(puzzleConfig);
    }

    /** Use {@link #genericGet(E exercise)} instead */
    @Deprecated
    public PuzzleConfigAggregate<Exercise> get(final Exercise exercise) {
        return genericGet(exercise);
    }

    public void save(final PuzzleConfigAggregate<Exercise> aggregate) {
        // no consistency because in-memory value does not matter after power outage
        saveImpl(aggregate);
        saveToCache(aggregate);
    }

    protected final void createAndSaveDefaultConfig(final Exercise exercise) {
        // TODO crutch
        AudioPerfectPitchConfigAggregatesFactory factory = getPuzzleConfigAggregatesFactoryResolver()
                .resolveFactory(AUDIO_PERFECT_PITCH_EXERCISE);
        PuzzleConfigAggregate<?> newPuzzleConfig = factory.createDefault();
        save((PuzzleConfigAggregate<Exercise>) newPuzzleConfig);
    }
}

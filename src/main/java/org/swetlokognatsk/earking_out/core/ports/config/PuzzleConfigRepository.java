package org.swetlokognatsk.earking_out.core.ports.config;

import org.swetlokognatsk.earking_out.core.domain.model.base.AggregateRoot;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfigAggregate;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.PuzzleConfigDTO;
import org.swetlokognatsk.earking_out.core.ports.base.PolymorphicAggregateRepository;

public interface PuzzleConfigRepository extends PolymorphicAggregateRepository<Exercise, PuzzleConfigAggregate<? extends Exercise>> {
    /**
     * Use {@link #getPuzzleConfig() } instead to get typed puzzle config aggregate
     * object.
     */
    @Override
    @Deprecated(forRemoval = false)
    <E extends Exercise, PCA extends AggregateRoot<E>> PCA get(E exercise);

    /**
     * more precise (by type) version of get(). the problem of
     * PolymorphicAggregateRepository is that we can either narrow the return type
     * (e.g. to <? extends PuzzleConfigAggregate<E>>), either we can guarantee the
     * genericity of method (accepts E, then returns PuzzleConfigAggregate<E>), not
     * both simultaneously. So, getPuzzleConfig should use get() under the hood with
     * cast
     */
    @SuppressWarnings("unchecked")
    default <E extends Exercise, PCA extends PuzzleConfigAggregate<E>> PCA getPuzzleConfig(E exercise) {
        return (PCA) get(exercise);
    }

    <E extends Exercise, PCDTO extends PuzzleConfigDTO<E>> PCDTO getPuzzleConfigDTO(E exercise);

    void actualizeCache();
}

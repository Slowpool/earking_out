package org.swetlokognatsk.earking_out.infrastructure.adapters;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import org.swetlokognatsk.earking_out.core.domain.model.base.AggregateRoot;
import org.swetlokognatsk.earking_out.core.ports.base.AggregateRepository;
import org.swetlokognatsk.earking_out.core.ports.base.PolymorphicAggregateRepository;
import org.swetlokognatsk.earking_out.core.ports.base.TypedAggregateRepository;

public abstract class InMemoryRepositoryTest<ID, A extends AggregateRoot<ID>, AR extends AggregateRepository> {

    protected abstract A getSomeAggregate();

    protected abstract AR getRepository();

    protected abstract void makeMinorChange(final A aggregate);

    protected abstract void assertAreDifferentByMinorChange(final A freshman, final A suspect);

    public void ensureGetMethodGivesCopyWithoutSave() {
        var aggregate1 = getSomeAggregate();
        var aggregate2 = getSomeAggregate();
        assertNotEquals(aggregate1, aggregate2);
    }

    private void saveToRepository(final A aggregate) {
        var repository = getRepository();
        switch (repository) {
        case TypedAggregateRepository typedRepository:
            typedRepository.save(aggregate);
            break;
        case PolymorphicAggregateRepository polymorphicRepository:
            polymorphicRepository.save(aggregate);
            break;
        default:
            throw new RuntimeException("unknown repository");
        }
    }

    public void ensureGetMethodGivesCopyAfterSave() {
        var aggregate1 = getSomeAggregate();
        saveToRepository(aggregate1);
        var aggregate2 = getSomeAggregate();
        assertNotEquals(aggregate1, aggregate2);
    }

    // TODO add it to abstract InMemoryRepositoryTest class, derive current class from it, apply polymorphism to makeMinorChange and assertDoesNotHaveMinorChange, derive other InMemoryRepositoryTests from it
    /**
     * During save(someAggregate) all in-memory repositories must make copy of
     * someAggregate and save namely it, not someAggregate, so that editing
     * someAggregate from client code wouldn't cause any immediate changes in
     * in-memory aggregate
     */
    public void ensureSaveMethodPersistsCopy() {
        var suspect = getSomeAggregate();

        saveToRepository(suspect);
        makeMinorChange(suspect);

        var freshman = getSomeAggregate();
        assertAreDifferentByMinorChange(freshman, suspect);
    }

}

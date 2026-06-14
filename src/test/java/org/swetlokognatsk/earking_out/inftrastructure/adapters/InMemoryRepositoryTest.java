package org.swetlokognatsk.earking_out.inftrastructure.adapters;

import static org.junit.Assert.*;
import org.junit.*;
import org.swetlokognatsk.earking_out.core.domain.model.base.Aggregate;
import org.swetlokognatsk.earking_out.core.ports.base.AggregateRepository;

public abstract class InMemoryRepositoryTest<ID, A extends Aggregate<ID>, AR extends AggregateRepository<ID, A>> {

    protected abstract A getSomeAggregate();

    protected abstract AR getRepository();

    protected abstract void makeMinorChange(final A aggregate);

    protected abstract void assertAreDifferentByMinorChange(final A sourceAggregate, final A editedAggregate);

    @Test
    public void ensureGetMethodGivesCopyWithoutSave() {
        var aggregate1 = getSomeAggregate();
        var aggregate2 = getSomeAggregate();
        assertNotEquals(aggregate1, aggregate2);
    }

    @Test
    public void ensureGetMethodGivesCopyAfterSave() {
        var aggregate1 = getSomeAggregate();
        getRepository().save(aggregate1);
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
    @Test
    public void ensureSaveMethodPersistsCopy() {
        var aggregate1 = getSomeAggregate();

        getRepository().save(aggregate1);
        makeMinorChange(aggregate1);

        var aggregate2 = getSomeAggregate();
        assertAreDifferentByMinorChange(aggregate1, aggregate2);
    }

}

package org.swetlokognatsk.earking_out.learning_tests;

import java.io.UncheckedIOException;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;
import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedGenerator;
import fr.maif.eventsourcing.EventProcessor;
import fr.maif.eventsourcing.EventProcessorImpl;
import fr.maif.eventsourcing.EventStore;
import fr.maif.eventsourcing.ReadConcurrencyStrategy;
import fr.maif.eventsourcing.TransactionManager;
import fr.maif.reactor.eventsourcing.DefaultAggregateStore;
import fr.maif.reactor.eventsourcing.InMemoryEventStore;
import io.vavr.Tuple;
import io.vavr.Tuple0;
import io.vavr.collection.List;

public class Bank {
    private final EventProcessor<String, Account, BankCommand, BankEvent, InMemoryEventStore.Transaction, Tuple0, Tuple0, Tuple0> eventProcessor;
    private static final TimeBasedGenerator UUIDGenerator = Generators.timeBasedGenerator();

    public Bank(CustomActorSystem actorSystem, BankCommandHandler commandHandler, BankEventHandler eventHandler) {
        // TODO why passing actorSystem? actorSystem is EventEnvelope, isn't it awkward?
        var eventStore = (EventStore<InMemoryEventStore.Transaction<BankEvent, Tuple0, Tuple0>, BankEvent, Tuple0, Tuple0>)InMemoryEventStore.create(actorSystem);
        TransactionManager<InMemoryEventStore.Transaction<BankEvent, Tuple0, Tuple0>> transactionManager = noOpTransactionManager();
        ExecutorService executor = Executors.newCachedThreadPool();
        this.eventProcessor = new EventProcessorImpl<String, Account, BankCommand, BankEvent, InMemoryEventStore.Transaction, Tuple0, Tuple0, Tuple0>(
            eventStore,
            transactionManager,
            new DefaultAggregateStore<Account, BankEvent, Tuple0, Tuple0, InMemoryEventStore.Transaction<BankEvent, Tuple0, Tuple0>>(eventStore, eventHandler, transactionManager, ReadConcurrencyStrategy.NO_STRATEGY),
            commandHandler,
            eventHandler,
            List.empty()
        );
    }

    private TransactionManager<InMemoryEventStore.Transaction<BankEvent, Tuple0, Tuple0>> noOpTransactionManager() {
        return new TransactionManager<>() {
            @Override
            public <T> CompletionStage<T> withTransaction(Function<InMemoryEventStore.Transaction<BankEvent, Tuple0, Tuple0>, CompletionStage<T>> function) {
                return function.apply(InMemoryEventStore.Transaction.newTx());
            }
        };
    }
}

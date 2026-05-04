package org.swetlokognatsk.earking_out.learning_tests;

// import fr.maif.eventsourcing.*;
import static io.vavr.control.Either.*;
import io.vavr.Tuple0;
import io.vavr.collection.List;
import io.vavr.control.Either;
// import io.vavr.control.Either;
import io.vavr.control.Option;

import java.math.BigDecimal;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import org.swetlokognatsk.earking_out.learning_tests.BankCommand.OpenAccount;
import org.swetlokognatsk.earking_out.learning_tests.BankEvent.AccountOpened;

import fr.maif.eventsourcing.blocking.CommandHandler;
import fr.maif.eventsourcing.EventPublisher;
import fr.maif.eventsourcing.Events;

public class BankCommandHandler implements CommandHandler<String, Account, BankCommand, BankEvent, Tuple0, Tuple0> {
    // @Override
    // public CompletionStage<Either<String, Events<BankEvent, List<String>>>>
    // handleCommand(
    // TxCtx transactionContext,
    // Option<Account> previousState,
    // BankCommand command) {
    // return switch (command) {
    // case Withdraw withdraw -> this.handleWithdraw(previousState, withdraw);
    // case Deposit deposit -> this.handleDeposit(previousState, deposit);
    // case OpenAccount openAccount -> this.handleOpening(openAccount);
    // case CloseAccount close -> this.handleClosing(previousState, close);
    // default -> null;
    // };
    // }

    // CompletionStage<Either<String, Events<BankEvent, List<String>>>>
    // handleWithdraw(Option<Account> previousState, Withdraw withdraw) {
    // return new CompletableFuture();
    // }

    // CompletionStage<Either<String, Events<BankEvent, List<String>>>>
    // handleDeposit(Option<Account> previousState, Deposit deposit) {
    // return new CompletableFuture();
    // }

    // CompletionStage<Either<String, Events<BankEvent, List<String>>>>
    // handleOpening(OpenAccount openAccount) {
    // return new CompletableFuture();
    // }

    // CompletionStage<Either<String, Events<BankEvent, List<String>>>>
    // handleClosing(Option<Account> previousState, CloseAccount close) {
    // return new CompletableFuture();
    // }

    public Either<String, Events<BankEvent, Tuple0>> handleCommand(Tuple0 transactionContext, Option<Account> previousState, BankCommand command) {
        return switch (command) {
        case BankCommand.OpenAccount openAccount -> this.handleOpening(openAccount);
        default -> null;
        };
    }

    private Either<String, Events<BankEvent, Tuple0>> handleOpening(BankCommand.OpenAccount opening) {
        // var accountOpened = new AccountOpened();
        if (opening.initialBalance().compareTo(BigDecimal.ZERO) < 0) {
            return left("Initial balance cannot be negative");
        }

        String newId = opening.id().get();
        List<BankEvent> events = List.of(new BankEvent.AccountOpened(newId));
        if (opening.initialBalance().compareTo(BigDecimal.ZERO) > 0) {
            events = events.push(new BankEvent.MoneyDeposited(newId, opening.initialBalance()));
        }

        return right(Events.events(events));
    }
}

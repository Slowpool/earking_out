package org.swetlokognatsk.earking_out.learning_tests;

import java.math.BigDecimal;

import org.swetlokognatsk.earking_out.learning_tests.BankEvent.AccountOpened;
import org.swetlokognatsk.earking_out.learning_tests.BankEvent.MoneyDeposited;

import fr.maif.eventsourcing.EventHandler;
import io.vavr.control.Option;

public class BankEventHandler implements EventHandler<Account, BankEvent> {
    public Option<Account> applyEvent(Option<Account> previousState, BankEvent event) {
        return switch (event) {
        // convincing idea to make these method static cuz from the point of input/output parameter(s) it's a pure function
        case AccountOpened accountOpened -> handleAccountOpened(accountOpened);
        case MoneyDeposited deposit -> handleMoneyDeposited(previousState, deposit);
        };
    }

    private static Option<Account> handleAccountOpened(AccountOpened accountOpened) {
        var account = new Account();
        account.id = accountOpened.accountId();
        account.balance = BigDecimal.ZERO;
        return Option.some(account);
    }

    private static Option<Account> handleMoneyDeposited(Option<Account> previousState, MoneyDeposited deposit) {
        return previousState.map(state -> {
            state.balance = state.balance.add(deposit.amount());
            return state;
        });
    }

}

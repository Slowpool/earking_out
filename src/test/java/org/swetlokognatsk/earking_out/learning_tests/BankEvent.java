package org.swetlokognatsk.earking_out.learning_tests;

import java.math.BigDecimal;

import fr.maif.eventsourcing.*;

public sealed interface BankEvent extends Event {
    Type<AccountOpened> AccountOpenedV1 = Type.create(AccountOpened.class, 1L);
    Type<MoneyDeposited> MoneyDepositedV1 = Type.create(MoneyDeposited.class, 1L);

    String accountId();

    default String entityId() {
        return accountId();
    }

    record AccountOpened(String accountId) implements BankEvent {
        @Override
        public Type<AccountOpened> type() {
            return AccountOpenedV1;
        }
    }

    record MoneyDeposited(String accountId, BigDecimal amount) implements BankEvent {
        @Override
        public Type<MoneyDeposited> type() {
            return MoneyDepositedV1;
        }
    }

}

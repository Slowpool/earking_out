package org.swetlokognatsk.earking_out.test_fakes;

import java.math.BigDecimal;

import fr.maif.eventsourcing.SimpleCommand;
import io.vavr.Lazy;

public sealed interface BankCommand extends SimpleCommand {
    record OpenAccount(Lazy<String> id, BigDecimal initialBalance) implements BankCommand {
        @Override
        public Lazy<String> entityId() {
            return id;
        }

        @Override
        public Boolean hasId() {
            return false;
        }
    }

}

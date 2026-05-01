package org.swetlokognatsk.earking_out.test_fakes;

import java.math.BigDecimal;

import fr.maif.eventsourcing.AbstractState;

public class Account extends AbstractState<Account> {
    public String id;
    public BigDecimal balance;
    
    public String entityId() {
        return id;
    }
}

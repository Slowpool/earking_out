package org.swetlokognatsk.earking_out.learning_tests;

import fr.maif.eventsourcing.State;

public class WeirdAccount implements State<WeirdAccount> {
    public String entityId() {
        return "";
    }

    public Long sequenceNum() {
        return 0L;
    }

    public WeirdAccount withSequenceNum(Long sequenceNum) {
        return this;
    }
}

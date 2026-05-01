package org.swetlokognatsk.earking_out.test_fakes;

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

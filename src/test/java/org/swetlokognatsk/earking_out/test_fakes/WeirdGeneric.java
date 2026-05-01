package org.swetlokognatsk.earking_out.test_fakes;

import fr.maif.eventsourcing.State;

public class WeirdGeneric<S extends State<S>> {
    public S something;
}
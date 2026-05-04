package org.swetlokognatsk.earking_out.learning_tests;

import fr.maif.eventsourcing.State;

public class WeirdGeneric<S extends State<S>> {
    public S something;
}
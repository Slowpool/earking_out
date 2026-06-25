package org.swetlokognatsk.earking_out.app.desktop.events.session;

import org.swetlokognatsk.earking_out.core.domain.model.Guess;
import javafx.event.Event;
import javafx.event.EventType;

public final class GuessEvent extends Event {
    public static final EventType<GuessEvent> GUESS_EVENT = new EventType<>("GUESS_EVENT");

    public final Guess guess;

    public GuessEvent(final EventType<?> eventType, final Guess guess) {
        super(eventType);
        this.guess = guess;
    }
}

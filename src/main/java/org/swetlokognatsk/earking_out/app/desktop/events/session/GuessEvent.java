package org.swetlokognatsk.earking_out.app.desktop.events.session;

import org.swetlokognatsk.earking_out.core.domain.model.solutions.Solution;
import javafx.event.Event;
import javafx.event.EventType;

@Deprecated
public final class GuessEvent extends Event {
    public static final EventType<GuessEvent> GUESS_EVENT = new EventType<>("GUESS_EVENT");

    public final Solution guess;

    public GuessEvent(final EventType<?> eventType, final Solution guess) {
        super(eventType);
        this.guess = guess;
    }
}

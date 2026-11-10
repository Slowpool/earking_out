package org.swetlokognatsk.earking_out.app.desktop.events.session;

import javafx.event.Event;
import javafx.event.EventType;

// TODO can i use it or shouldn't i?
public final class HearAgainEvent extends Event {
    public static final EventType<HearAgainEvent> HEAR_AGAIN_EVENT = new EventType<>("HEAR_AGAIN_EVENT");

    public HearAgainEvent(final EventType<?> eventType) {
        super(eventType);
    }
}

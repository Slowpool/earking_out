package org.swetlokognatsk.earking_out.app.desktop.events;

import javafx.event.Event;
import javafx.event.EventType;

public final class PopupRequestEvent extends Event {
    public static final EventType<PopupRequestEvent> POPUP_REQUEST_EVENT = new EventType<PopupRequestEvent>("POPUP_REQUEST_EVENT");

    public final String message;

    public PopupRequestEvent(final EventType<?> eventType, final String message) {
        super(eventType);
        this.message = message;
    }
}

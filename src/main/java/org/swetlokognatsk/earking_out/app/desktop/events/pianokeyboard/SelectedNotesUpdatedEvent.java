package org.swetlokognatsk.earking_out.app.desktop.events.pianokeyboard;

import javafx.event.Event;
import javafx.event.EventType;

public class SelectedNotesUpdatedEvent extends Event {
    public static final EventType<SelectedNotesUpdatedEvent> SELECTED_KEYS_UPDATED = new EventType<>("SELECTED_NOTES_UPDATED_EVENT");

    public SelectedNotesUpdatedEvent(final EventType<?> eventType) {
        super(eventType);

    }
}

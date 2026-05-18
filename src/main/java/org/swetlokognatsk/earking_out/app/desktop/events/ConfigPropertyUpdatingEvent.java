package org.swetlokognatsk.earking_out.app.desktop.events;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;

import javafx.event.Event;
import javafx.event.EventType;

public class ConfigPropertyUpdatingEvent extends Event {
    public static final EventType<ConfigPropertyUpdatingEvent> CONFIG_PROPERTY_UPDATING = new EventType<ConfigPropertyUpdatingEvent>("CONFIG_PROPERTY_UPDATING");

    public final Exercise exercise;
    public final String configProperty;
    public final String newValue;

    public ConfigPropertyUpdatingEvent(final EventType<?> eventType, Exercise exercise, String configProperty, String newValue) {
        super(eventType);

        this.exercise = exercise;
        this.configProperty = configProperty;
        this.newValue = newValue;
    }
}

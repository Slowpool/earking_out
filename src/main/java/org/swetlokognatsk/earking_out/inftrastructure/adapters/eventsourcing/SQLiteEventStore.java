package org.swetlokognatsk.earking_out.inftrastructure.adapters.eventsourcing;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.swetlokognatsk.earking_out.core.ports.events.DomainEventJsonSerializer;
import org.swetlokognatsk.earking_out.core.ports.eventsourcing.EventStore;
import org.swetlokognatsk.earking_out.inftrastructure.eventsourcing.EventStream;

// jdbc implementation for fun instead of orm using. to learn jdbc api a bit. 
public final class SQLiteEventStore implements EventStore {

    private final DomainEventJsonSerializer domainEventJsonSerializer;

    public SQLiteEventStore(final DomainEventJsonSerializer domainEventJsonSerializer) {
        this.domainEventJsonSerializer = domainEventJsonSerializer;
    }

    // // TODO use it
    // public void append(final EventStream<?> eventStream) throws EventSavingException {
    public void append(final EventStream<?> eventStream) {
        // TODO move it to config
        var db = "sample.db";
        var connectionString = String.format("jdbc:sqlite:%s", db);
        var eventSourcingEventsTable = "event_sourcing_events";

        // TODO hint: all columns are TEXT
        var createCommand = ("INSERT INTO `?` (`id`, `type`, `created_on`, `payload`) VALUES (?, ?, ?, ?)");
        try (Connection connection = DriverManager.getConnection(connectionString); var statement = connection.prepareStatement(createCommand);) {
            // // TODO Iterable/Iterator is enough to use it?
            // for (var event : eventStream) {
            for (var event : eventStream.events()) {
                statement.setString(1, eventSourcingEventsTable);

                statement.setString(2, eventStream.id().toString());
                statement.setString(3, event.getClass().toString());
                statement.setString(4, getCreatedOn());
                statement.setString(5, domainEventJsonSerializer.serializeDomainEvent(event));

                int countOfInsertedRows = statement.executeUpdate(createCommand);
                if (countOfInsertedRows != 1) {
                    // TODO use it
                    // throw new EventSavingException("failed to append event", e);
                    throw new RuntimeException("insert went wrong. expected countOfInsertedRows: 1. actual: " + countOfInsertedRows);
                }
            }
        } catch (SQLException e) {
            // TODO use it
            // throw new EventSavingException("failed to append event", e);
            throw new RuntimeException("failed to append event", e);
        }

    }

    private String getCreatedOn() {
        return LocalDateTime.now().format(SQLiteConfig.dateTimeFormat);
    }

    // TODO refactoring
    private class SQLiteConfig {
        public static final DateTimeFormatter dateTimeFormat = DateTimeFormatter.ISO_DATE_TIME;
    }
}

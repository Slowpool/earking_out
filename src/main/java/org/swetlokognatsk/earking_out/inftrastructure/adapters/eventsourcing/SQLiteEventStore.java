package org.swetlokognatsk.earking_out.inftrastructure.adapters.eventsourcing;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.swetlokognatsk.earking_out.core.ports.eventsourcing.EventStore;
import org.swetlokognatsk.earking_out.inftrastructure.EventSavingException;
import org.swetlokognatsk.earking_out.inftrastructure.eventsourcing.EventStream;

// jdbc implementation for fun instead of orm using. to learn jdbc api a bit. 
public final class SQLiteEventStore implements EventStore {

    // // TODO use it
    // public void append(final EventStream<?> eventStream) throws EventSavingException {
    public void append(final EventStream<?> eventStream) {
        // TODO move it to config
        var db = "sample.db";
        var connectionString = String.format("jdbc:sqlite:%s", db);
        var eventSourcingEventsTable = "event_sourcing_events";

        // // TODO Iterable/Iterator is enough to use it?
        // for (var event : eventStream) {
            
        try (Connection connection = DriverManager.getConnection(connectionString); var statement = connection.createStatement();) {
            var createCommandBuilder = new StringBuilder("INSERT INTO `%s` (`id`, `type`, `created_on`, `payload`) VALUES (");
            var createCommand = createCommandBuilder.toString();
            
            for (var event : eventStream.events()) {
                createCommandBuilder.append();
            }
            // TODO bind params
            // statement.
            statement.executeUpdate(createCommand);
        } catch (SQLException e) {
            // TODO is it checked or uncheked if it inherits from Exception?
            throw new EventSavingException("failed to append event", e);
        }

    }
}

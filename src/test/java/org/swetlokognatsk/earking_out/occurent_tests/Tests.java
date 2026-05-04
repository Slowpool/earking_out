package org.swetlokognatsk.earking_out.occurent_tests;

import org.junit.Test;
import org.occurrent.application.service.blocking.generic.GenericApplicationService;
import org.occurrent.eventstore.api.blocking.EventStore;
import org.occurrent.eventstore.mongodb.nativedriver.EventStoreConfig;
import org.occurrent.eventstore.mongodb.nativedriver.MongoEventStore;
import org.occurrent.mongodb.timerepresentation.TimeRepresentation;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

import io.cloudevents.CloudEvent;
import io.cloudevents.core.builder.CloudEventBuilder;

import static org.junit.Assert.*;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.stream.Stream;

public class Tests {
    @Test
    public void mongoConnectionTest() {
    }

    @Test
    public void test() {
        var mongoClient = getMongoClient();
        var eventStoreConfig = new EventStoreConfig(TimeRepresentation.RFC_3339_STRING);
        var eventStore = new MongoEventStore(mongoClient, "earking_out", "perfect_pitch", eventStoreConfig);
        // GenericApplicationService service = new GenericApplicationService<>(eventStore, null);

        CloudEvent event = CloudEventBuilder.v1()
            .withId("1937")
            .withSource(URI.create("someSource"))
            .withType("someType")
            .withTime(LocalDateTime.now().atOffset(ZoneOffset.UTC))
            .withSubject("someSubject")
            .withDataContentType("application/json")
            .withData("{\"messsage\": \"hello\"}".getBytes(StandardCharsets.UTF_8))
            .build() ;
        
        var streamId = "streamId";
        eventStore.write(streamId, Stream.of(event));

        // var eventStream = eventStore.read(streamId);
        // eventStream.
    }

    MongoClient getMongoClient() {
        return MongoClients.create("mongodb://root:password@localhost:27017");
    }

}

package org.swetlokognatsk.earking_out.infrastructure.adapters.eventsourcing;

import static java.util.Objects.requireNonNull;
import jakarta.persistence.*;

@Entity
@Table(name = "event_sourcing_events")
public class EventSourcingEventEntity {

    @Id
    // TODO use UUID instead
    private String id;

    // TODO use UUID instead
    private String streamId;

    private String type;

    // TODO use LocalDateTime instead
    @Column(name = "created_on")
    private String createdOn;

    private String payload;

    public String getId() {
        return id;
    }

    public String getStreamId() {
        return streamId;
    }

    public String getType() {
        return type;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public String getPayload() {
        return payload;
    }

    public EventSourcingEventEntity() {

    }

    public EventSourcingEventEntity(final String id, final String streamId, final String type, final String createdOn, final String payload) {
        this.id = requireNonNull(id);
        this.streamId = requireNonNull(streamId);
        this.type = requireNonNull(type);
        this.createdOn = requireNonNull(createdOn);
        this.payload = requireNonNull(payload);
    }
}

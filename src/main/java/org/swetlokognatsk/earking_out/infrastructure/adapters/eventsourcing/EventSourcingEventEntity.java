package org.swetlokognatsk.earking_out.infrastructure.adapters.eventsourcing;

import static java.util.Objects.requireNonNull;
import org.swetlokognatsk.earking_out.core.domain.model.identity.UserId;
import jakarta.persistence.*;

@Entity
@Table(name = "event_sourcing_events")
public class EventSourcingEventEntity {

    @Id
    // TODO use UUID instead
    private String id;

    // TODO use UUID instead
    private String streamId;

    // TODO use UUID instead
    private int userId;

    private String type;

    // TODO use LocalDateTime instead
    @Column(name = "created_on")
    private String createdOn;

    private String payload;

    private int version;

    public String getId() {
        return id;
    }

    public String getStreamId() {
        return streamId;
    }

    public int getUserId() {
        return userId;
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

    public int getVersion() {
        return version;
    }

    public EventSourcingEventEntity() {

    }

    public EventSourcingEventEntity(final String id, final String streamId, final int userId, final String type, final String createdOn, final String payload, final int version) {
        this.id = requireNonNull(id);
        this.streamId = requireNonNull(streamId);
        this.userId = requireNonNull(userId);
        this.type = requireNonNull(type);
        this.createdOn = requireNonNull(createdOn);
        this.payload = requireNonNull(payload);
        this.version = requireNonNull(version);
    }
}

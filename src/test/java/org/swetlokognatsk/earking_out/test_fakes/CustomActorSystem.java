package org.swetlokognatsk.earking_out.test_fakes;

import java.time.LocalDateTime;
import fr.maif.eventsourcing.EventEnvelope;
import io.vavr.Tuple0;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class CustomActorSystem extends EventEnvelope<BankEvent, Tuple0, Tuple0> {
    public CustomActorSystem(UUID id, Long sequenceNum, String eventType, LocalDateTime emissionDate, String transactionId, Tuple0 metadata, BankEvent event, Tuple0 context, Long version, Boolean published, Integer totalMessageInTransaction, Integer numMessageInTransaction, String entityId, String userId, String systemId) {
        super(id, sequenceNum, eventType, emissionDate, transactionId, metadata, event, context, version, published, totalMessageInTransaction, numMessageInTransaction, entityId, userId, systemId);
    }
}

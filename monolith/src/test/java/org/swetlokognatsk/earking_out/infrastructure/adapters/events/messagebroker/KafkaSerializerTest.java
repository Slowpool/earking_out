package org.swetlokognatsk.earking_out.infrastructure.adapters.events.messagebroker;

import java.time.LocalDateTime;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.swetlokognatsk.earking_out.core.domain.events.session.NewPuzzleCreatedEvent;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.ExercisesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.identity.UserId;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.perfectpitch.AudioPerfectPitchPuzzle;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionId;
import org.swetlokognatsk.earking_out.core.domain.model.solutions.perfectpitch.AudioPerfectPitchSolution;
import org.swetlokognatsk.earking_out.infrastructure.adapters.serialization.JacksonJsonSerializer;
import lombok.Cleanup;
import static org.junit.jupiter.api.Assertions.*;
import static org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber.FIRST_NOTE_NUMBER;

public class KafkaSerializerTest {

    private KafkaSerializer serializer;

    @BeforeEach
    public void setup() {
        serializer = new KafkaSerializer(new JacksonJsonSerializer());
    }

    @AfterEach
    public void cleanup() {
        serializer.close();
    }

    @Test
    public void serializeDomainEvent() {
        var pianoKey = FIRST_NOTE_NUMBER;
        var currentDateTime = LocalDateTime.parse("2026-10-22T14:34:04.147064557");
        var sessionId = "188f4b50-1fe1-475a-b7ca-307c20ebf320";
        var domainEvent = new NewPuzzleCreatedEvent(new UserId(1), currentDateTime, new SessionId(sessionId), new AudioPerfectPitchPuzzle(ExercisesFactory.AUDIO_PERFECT_PITCH_EXERCISE, new AudioPerfectPitchSolution(pianoKey)));

        var serializedDomainEvent = new String(serializer.serialize("domain-events", domainEvent));

        var expectedSerializedEvent = """
                {"type":"NewPuzzleCreatedEvent","payload":{"userId":1,"timestamp":"2026-10-22T14:34:04.147064557","sessionId":{"id":"188f4b50-1fe1-475a-b7ca-307c20ebf320"},"puzzle":{"exercise":{"name":"PERFECT_PITCH","type":"AUDIO"},"solution":{"exercise":{"name":"PERFECT_PITCH","type":"AUDIO"},"keyNumber":4}}}}
            """.strip();
        assertEquals(expectedSerializedEvent, serializedDomainEvent);
    }
}

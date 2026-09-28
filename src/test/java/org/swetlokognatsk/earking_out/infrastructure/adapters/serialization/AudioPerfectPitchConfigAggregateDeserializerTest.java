package org.swetlokognatsk.earking_out.infrastructure.adapters.serialization;

import static org.junit.Assert.*;
import static org.swetlokognatsk.earking_out.core.domain.model.exercises.ExercisesFactory.AUDIO_PERFECT_PITCH_EXERCISE;
import java.util.ArrayList;
import java.util.List;
import org.junit.*;
import org.junit.Assert.*;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.AudioPerfectPitchConfigAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.PerfectPitchInputMode;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.databind.module.SimpleModule;

public class AudioPerfectPitchConfigAggregateDeserializerTest {

    private ObjectMapper objectMapper;

    @Before
    public void setup() {
        var module = new SimpleModule()
                .addDeserializer(AudioPerfectPitchConfigAggregate.class, new AudioPerfectPitchConfigAggregateDeserializer());

        objectMapper = JsonMapper.builder()
                .addModule(module)
                .build();
    }

    @Test
    public void deserialize() {
        var serializedConfig = """
                {
                    "targetNumberOfPuzzles": 5,
                    "statsRecording": true,
                    "normalizedNotesForPuzzle": [
                        52,
                        53
                    ],
                    "normalizedRootNote": 52,
                    "inputMode": "KEYBOARD_AS_PIANO",
                    "soundlessGuessingPiano": true,
                    "errors": [],
                    "id": {
                        "name": "PERFECT_PITCH",
                        "type": "AUDIO"
                    }
                }
                """;
        var puzzleConfig = objectMapper.readValue(serializedConfig, AudioPerfectPitchConfigAggregate.class);

        assertEquals(puzzleConfig.getId(), AUDIO_PERFECT_PITCH_EXERCISE);
        assertEquals(puzzleConfig.getTargetNumberOfPuzzles(), 5);
        assertEquals(puzzleConfig.getStatsRecording(), true);
        var pianoKeyNumbers = new PianoKeyNumber[] { PianoKeyNumber.valueOf(52), PianoKeyNumber.valueOf(53) };
        assertArrayEquals(puzzleConfig.getNormalizedNotesForPuzzle(), pianoKeyNumbers);
        assertEquals(puzzleConfig.getNormalizedRootNote(), PianoKeyNumber.valueOf(52));
        assertEquals(puzzleConfig.getInputMode(), PerfectPitchInputMode.KEYBOARD_AS_PIANO);
        assertEquals(puzzleConfig.getSoundlessGuessingPiano(), true);
        assertEquals(0, puzzleConfig.getErrors().size());
    }

    // in prod i'd write a one more test with ortogonal property values to ensure that deserializer indeed parses the values, excluding any possibility of the default values setting
}

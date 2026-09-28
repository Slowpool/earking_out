package org.swetlokognatsk.earking_out.infrastructure.adapters.serialization;

import static org.junit.Assert.*;
import static org.swetlokognatsk.earking_out.core.domain.model.exercises.ExercisesFactory.AUDIO_PERFECT_PITCH_EXERCISE;
import org.junit.*;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.AudioPerfectPitchConfigAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.PerfectPitchInputMode;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.perfectpitch.AudioPerfectPitchConfigDTO;
import tools.jackson.databind.module.SimpleModule;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.json.JsonMapper;

public class AudioPerfectPitchConfigDTODeserializerTest {

    private ObjectMapper objectMapper;

    @Before
    public void setup() {
        var module = new SimpleModule()
                .addDeserializer(AudioPerfectPitchConfigDTO.class, new AudioPerfectPitchConfigDTODeserializer());

        objectMapper = JsonMapper.builder()
                .addModule(module)
                .build();
    }

    @Test
    public void deserialize() {
        var serializedConfigDto = """
                {
                    "exercise": {
                        "name": "PERFECT_PITCH",
                        "type": "AUDIO"
                    },
                    "targetNumberOfPuzzles": 5,
                    "statsRecording": true,
                    "normalizedNotesForPuzzle": [
                        52,
                        53
                    ],
                    "normalizedRootNote": 52,
                    "inputMode": "KEYBOARD_AS_PIANO",
                    "soundlessGuessingPiano": true
                }
                """;

        var puzzleConfig = objectMapper.readValue(serializedConfigDto, AudioPerfectPitchConfigDTO.class);

        assertEquals(puzzleConfig.exercise, AUDIO_PERFECT_PITCH_EXERCISE);
        assertEquals(puzzleConfig.targetNumberOfPuzzles, 5);
        assertEquals(puzzleConfig.statsRecording, true);
        var pianoKeyNumbers = new PianoKeyNumber[] { PianoKeyNumber.valueOf(52), PianoKeyNumber.valueOf(53) };
        assertArrayEquals(puzzleConfig.normalizedNotesForPuzzle, pianoKeyNumbers);
        assertEquals(puzzleConfig.normalizedRootNote, PianoKeyNumber.valueOf(52));
        assertEquals(puzzleConfig.inputMode, PerfectPitchInputMode.KEYBOARD_AS_PIANO);
        assertEquals(puzzleConfig.soundlessGuessingPiano, true);
    }
}

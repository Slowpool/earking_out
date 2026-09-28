package org.swetlokognatsk.earking_out.infrastructure.adapters.serialization;

import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.PuzzleConfigDTO;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.perfectpitch.AudioPerfectPitchConfigDTO;
import static org.junit.Assert.*;
import static org.swetlokognatsk.earking_out.core.domain.model.exercises.ExercisesFactory.AUDIO_PERFECT_PITCH_EXERCISE;
import org.junit.*;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.AudioPerfectPitchConfigAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.PerfectPitchInputMode;
import tools.jackson.databind.module.SimpleModule;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.json.JsonMapper;

public class PuzzleConfigDTODeserializerTest {

    private ObjectMapper objectMapper;
    // APPE - (A)udio(P)erfect(P)itch(E)xercise
    private static final String APPE_SERIALIZED_CONFIG_DTO = """
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

    @Before
    public void setup() {
        var module = new SimpleModule()
                .addDeserializer(PuzzleConfigDTO.class, new PuzzleConfigDTODeserializer(PuzzleConfigDTO.class));

        objectMapper = JsonMapper.builder()
                .addModule(module)
                .build();
    }

    private void assertAudioPerfectPitchConfigDto(final AudioPerfectPitchConfigDTO puzzleConfig) {
        assertEquals(puzzleConfig.exercise, AUDIO_PERFECT_PITCH_EXERCISE);
        assertEquals(puzzleConfig.targetNumberOfPuzzles, 5);
        assertEquals(puzzleConfig.statsRecording, true);
        var pianoKeyNumbers = new PianoKeyNumber[] { PianoKeyNumber.valueOf(52), PianoKeyNumber.valueOf(53) };
        assertArrayEquals(puzzleConfig.normalizedNotesForPuzzle, pianoKeyNumbers);
        assertEquals(puzzleConfig.normalizedRootNote, PianoKeyNumber.valueOf(52));
        assertEquals(puzzleConfig.inputMode, PerfectPitchInputMode.KEYBOARD_AS_PIANO);
        assertEquals(puzzleConfig.soundlessGuessingPiano, true);
    }

    @Test
    public void deserializeWithVC() {
        var puzzleConfig = objectMapper.readValue(APPE_SERIALIZED_CONFIG_DTO, AudioPerfectPitchConfigDTO.class);

        assertAudioPerfectPitchConfigDto(puzzleConfig);
    }

    @Test
    public void deserializeWithAbstractVC() {
        var puzzleConfig = (AudioPerfectPitchConfigDTO) objectMapper.readValue(APPE_SERIALIZED_CONFIG_DTO, PuzzleConfigDTO.class);

        assertAudioPerfectPitchConfigDto(puzzleConfig);
    }

}

package org.swetlokognatsk.earking_out.infrastructure.adapters.serialization;

import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.PuzzleConfigDTO;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.perfectpitch.AudioPerfectPitchConfigDTO;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.perfectpitch.PerfectPitchConfigDTO;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.perfectpitch.VisualPerfectPitchConfigDTO;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.ExerciseNames;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.ExerciseTypes;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.PerfectPitchInputMode;

@SpringBootTest
public class PuzzleConfigDTODeserializerTest extends DeserializerBaseTest<PuzzleConfigDTO<?>, PuzzleConfigDTODeserializer> {

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
    private static final String VPPE_SERIALIZED_CONFIG_DTO = """
            {
                "exercise": {
                    "name": "PERFECT_PITCH",
                    "type": "VISUAL"
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

    protected Class<PuzzleConfigDTODeserializer> getTestedDeserializerClass() {
        return PuzzleConfigDTODeserializer.class;
    }

    @SuppressWarnings("unchecked")
    protected Class<PuzzleConfigDTO<?>> getTestedDeserializerType() {
        return (Class<PuzzleConfigDTO<?>>) (Class<?>) PuzzleConfigDTO.class;
    }

    private void assertAudioPerfectPitchConfigDto(final PuzzleConfigDTO<?> puzzleConfig) {
        if (puzzleConfig instanceof AudioPerfectPitchConfigDTO typedDto) {
            assertEquals(typedDto.exercise.type, ExerciseTypes.AUDIO);

            assertPerfectPitchConfigDto(typedDto);
        } else {
            fail();
        }
    }

    private void assertVisualPerfectPitchConfigDto(final PuzzleConfigDTO<?> puzzleConfig) {
        if (puzzleConfig instanceof VisualPerfectPitchConfigDTO typedDto) {
            assertEquals(typedDto.exercise.type, ExerciseTypes.VISUAL);

            assertPerfectPitchConfigDto(typedDto);
        } else {
            fail();
        }
    }

    private void assertPerfectPitchConfigDto(final PerfectPitchConfigDTO<?> puzzleConfig) {
        assertEquals(puzzleConfig.exercise.name, ExerciseNames.PERFECT_PITCH);
        assertEquals(puzzleConfig.targetNumberOfPuzzles, 5);
        assertEquals(puzzleConfig.statsRecording, true);
        var pianoKeyNumbers = new PianoKeyNumber[] { PianoKeyNumber.valueOf(52), PianoKeyNumber.valueOf(53) };
        assertArrayEquals(puzzleConfig.normalizedNotesForPuzzle, pianoKeyNumbers);
        assertEquals(puzzleConfig.normalizedRootNote, PianoKeyNumber.valueOf(52));
        assertEquals(puzzleConfig.inputMode, PerfectPitchInputMode.KEYBOARD_AS_PIANO);
        assertEquals(puzzleConfig.soundlessGuessingPiano, true);
    }

    @Test
    public void deserializeAudioPerfectPitchConfigWithVC() {
        var puzzleConfig = readValue(APPE_SERIALIZED_CONFIG_DTO, AudioPerfectPitchConfigDTO.class);

        assertAudioPerfectPitchConfigDto(puzzleConfig);
    }

    @Test
    public void deserializeAudioPerfectPitchConfigWithAbstractVC() {
        var puzzleConfig = (AudioPerfectPitchConfigDTO) readValue(APPE_SERIALIZED_CONFIG_DTO, PuzzleConfigDTO.class);

        assertAudioPerfectPitchConfigDto(puzzleConfig);
    }

    @Test
    public void deserializeVisualPerfectPitchConfigWithVC() {
        var puzzleConfig = readValue(VPPE_SERIALIZED_CONFIG_DTO, VisualPerfectPitchConfigDTO.class);

        assertVisualPerfectPitchConfigDto(puzzleConfig);
    }

    @Test
    public void deserializeVisualPerfectPitchConfigWithAbstractVC() {
        var puzzleConfig = (VisualPerfectPitchConfigDTO) readValue(VPPE_SERIALIZED_CONFIG_DTO, PuzzleConfigDTO.class);

        assertVisualPerfectPitchConfigDto(puzzleConfig);
    }

}

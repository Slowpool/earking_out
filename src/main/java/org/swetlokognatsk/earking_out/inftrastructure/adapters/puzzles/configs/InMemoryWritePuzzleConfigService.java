package org.swetlokognatsk.earking_out.inftrastructure.adapters.puzzles.configs;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfig;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.PerfectPitchConfig;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.PerfectPitchInputMode;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.typed.AudioPerfectPitchConfig;
import org.swetlokognatsk.earking_out.core.ports.config.WritePuzzleConfigService;

// TODO should such an implementations, that are utilized only in test purposes, be covered with tests? having it written it sounds like not, but i thought yes before.
public class InMemoryWritePuzzleConfigService implements WritePuzzleConfigService {

    /**
     * Always accepts `newValue` as `String` because it'll be stored serialized;
     * 
     * @param exercise
     * @param configProperty
     * @param newValue
     */
    public void updateProperty(Exercise exercise, String configProperty, Object newValue) {
        var oldConfig = InMemoryReadPuzzleConfigService.puzzleConfig;
        switch (configProperty) {
        case PuzzleConfig.TARGET_NUMBER_OF_PUZZLES_PROP:
            // TODO casting Object to String and then String to Integer is a little awkward, but probably that's how the cookies crumbles
            InMemoryReadPuzzleConfigService.puzzleConfig = new AudioPerfectPitchConfig((int) newValue, oldConfig.statsRecording, oldConfig.normalizedNotesForPuzzle, oldConfig.normalizedRootNote, oldConfig.inputMode);
            break;
        case PuzzleConfig.STATS_RECORDING_PROP:
            var newStatsRecording = (boolean) newValue;
            InMemoryReadPuzzleConfigService.puzzleConfig = new AudioPerfectPitchConfig(oldConfig.targetNumberOfPuzzles, newStatsRecording, oldConfig.normalizedNotesForPuzzle, oldConfig.normalizedRootNote, oldConfig.inputMode);
            break;
        case PerfectPitchConfig.NORMALIZED_NOTES_FOR_PUZZLE_PROP:
            var newNormalizedNotes = (byte[]) newValue;
            InMemoryReadPuzzleConfigService.puzzleConfig = new AudioPerfectPitchConfig(oldConfig.targetNumberOfPuzzles, oldConfig.statsRecording, newNormalizedNotes, oldConfig.normalizedRootNote, oldConfig.inputMode);
            break;
        case PerfectPitchConfig.NORMALIZED_ROOT_NOTE:
            var newNormalizedRootNote = (Byte)newValue;
            InMemoryReadPuzzleConfigService.puzzleConfig = new AudioPerfectPitchConfig(oldConfig.targetNumberOfPuzzles, oldConfig.statsRecording, oldConfig.normalizedNotesForPuzzle, newNormalizedRootNote, oldConfig.inputMode);
            break;
        case PerfectPitchConfig.INPUT_MODE_PROP:
            var newInputMode = (PerfectPitchInputMode)newValue;
            InMemoryReadPuzzleConfigService.puzzleConfig = new AudioPerfectPitchConfig(oldConfig.targetNumberOfPuzzles, oldConfig.statsRecording, oldConfig.normalizedNotesForPuzzle, oldConfig.normalizedRootNote, newInputMode);
            break;
        default:
            throw new RuntimeException("property saving is not defined");
        }
    }
}

package org.swetlokognatsk.earking_out.infrastructure.adapters.serialization;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.ExerciseNames;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.ExerciseTypes;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.ExercisesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.AudioPerfectPitchConfigAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.PerfectPitchInputMode;
import tools.jackson.core.JsonParser;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.deser.std.StdDeserializer;

@Deprecated(forRemoval = true)
public final class AudioPerfectPitchConfigAggregateDeserializer extends StdDeserializer<AudioPerfectPitchConfigAggregate> {

    public AudioPerfectPitchConfigAggregateDeserializer() {
        super(AudioPerfectPitchConfigAggregate.class);
    }

    // TODO is final for method params in interface propagated to implementing classes or not?
    @Override
    public AudioPerfectPitchConfigAggregate deserialize(final JsonParser parser, final DeserializationContext ctxt) {
        // nice. high-level JsonNode in low-level JsonParser
        var jsonTree = (JsonNode) parser.readValueAsTree();

        JsonNode idNode = jsonTree.get("id");
        var deserializedExercise = JacksonDeserializationHelper.deserializeExercise(idNode);

        var targetNumberOfPuzzles = jsonTree.get("targetNumberOfPuzzles").asInt();

        var statsRecording = jsonTree.get("statsRecording").asBoolean();

        var normalizedNotesForPuzzleNode = jsonTree.get("normalizedNotesForPuzzle");

        PianoKeyNumber[] normalizedNotesForPuzzle = ctxt.readTreeAsValue(normalizedNotesForPuzzleNode, PianoKeyNumber[].class);

        var normalizedRootNoteNode = jsonTree.get("normalizedRootNote");
        PianoKeyNumber normalizedRootNote = ctxt.readTreeAsValue(normalizedRootNoteNode, PianoKeyNumber.class);

        var inputModeNode = jsonTree.get("inputMode");
        var inputMode = ctxt.readTreeAsValue(inputModeNode, PerfectPitchInputMode.class);

        var soundlessGuessingPiano = jsonTree.get("soundlessGuessingPiano").asBoolean();

        var puzzleConfig = new AudioPerfectPitchConfigAggregate((AudioPerfectPitchExercise) deserializedExercise, targetNumberOfPuzzles, statsRecording, normalizedNotesForPuzzle, normalizedRootNote, inputMode, soundlessGuessingPiano);
        return puzzleConfig;
    }
}
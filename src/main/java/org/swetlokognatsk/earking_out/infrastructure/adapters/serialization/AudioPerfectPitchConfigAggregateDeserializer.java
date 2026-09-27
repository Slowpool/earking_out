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

public final class AudioPerfectPitchConfigAggregateDeserializer extends StdDeserializer<AudioPerfectPitchConfigAggregate> {

    public AudioPerfectPitchConfigAggregateDeserializer() {
        super(AudioPerfectPitchConfigAggregate.class);
    }

    // TODO is final for method params in interface propagated to implementing classes or not?
    @Override
    public AudioPerfectPitchConfigAggregate deserialize(final JsonParser parser, final DeserializationContext ctxt) {
        // TODO DEFINITELY NEEDS REFACTORING
        // nice. high-level JsonNode in low-level JsonParser
        var jsonTree = (JsonNode) parser.readValueAsTree();

        JsonNode idNode = jsonTree.get("id");
        var deserializedExercise = JacksonDeserializationHelper.deserializeExercise(idNode);

        var targetNumberOfPuzzles = jsonTree.get("targetNumberOfPuzzles").asInt();

        var statsRecording = jsonTree.get("statsRecording").asBoolean();

        var normalizedNotesForPuzzleNode = jsonTree.get("normalizedNotesForPuzzle").asArray();
        PianoKeyNumber[] normalizedNotesForPuzzle = new PianoKeyNumber[normalizedNotesForPuzzleNode.size()];
        for (int i = 0; i < normalizedNotesForPuzzleNode.size(); i++) {
            var node = normalizedNotesForPuzzleNode.get(i);
            var pianoKeyNumber = node.asInt();
            normalizedNotesForPuzzle[i] = PianoKeyNumber.valueOf(pianoKeyNumber);
        }

        var normalizedRootNoteNode = jsonTree.get("normalizedRootNote");
        PianoKeyNumber normalizedRootNote;
        if (normalizedRootNoteNode.isInt()) {
            var normalizedRootNoteNumber = normalizedRootNoteNode.asInt();
            normalizedRootNote = PianoKeyNumber.valueOf(normalizedRootNoteNumber);
        } else {
            normalizedRootNote = null;
        }

        var inputModeValue = jsonTree.get("inputMode").asString();
        var inputMode = PerfectPitchInputMode.valueOf(inputModeValue);

        var soundlessGuessingPiano = jsonTree.get("soundlessGuessingPiano").asBoolean();

        var puzzleConfig = new AudioPerfectPitchConfigAggregate((AudioPerfectPitchExercise) deserializedExercise, targetNumberOfPuzzles, statsRecording, normalizedNotesForPuzzle, normalizedRootNote, inputMode, soundlessGuessingPiano);
        return puzzleConfig;
    }
}
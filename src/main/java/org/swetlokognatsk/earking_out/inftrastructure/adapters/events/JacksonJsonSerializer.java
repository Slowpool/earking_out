package org.swetlokognatsk.earking_out.inftrastructure.adapters.events;

import org.swetlokognatsk.earking_out.core.domain.events.DomainEvent;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.ExerciseNames;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.ExerciseTypes;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.ExercisesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfigAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.AudioPerfectPitchConfigAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.PerfectPitchInputMode;
import org.swetlokognatsk.earking_out.core.ports.config.PuzzleConfigJsonSerializer;
import org.swetlokognatsk.earking_out.core.ports.events.DomainEventJsonSerializer;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.puzzles.configs.InMemoryPuzzleConfigRepository;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import tools.jackson.core.JsonGenerator;
import tools.jackson.core.JsonParser;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.JacksonModule;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.MapperFeature;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.PropertyNamingStrategy;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.SerializationFeature;
import tools.jackson.databind.deser.std.StdDeserializer;
import tools.jackson.databind.introspect.ClassIntrospector;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.databind.module.SimpleModule;
import tools.jackson.databind.ser.std.StdSerializer;

public final class JacksonJsonSerializer implements DomainEventJsonSerializer, PuzzleConfigJsonSerializer {

    private final ObjectMapper objectMapper;

    public JacksonJsonSerializer() {
        var audioPerfectPitchConfigSerializationModule = createAudioPerfectPitchConfigSerializationModule();

        var jsonMapper = JsonMapper.builder().configure(MapperFeature.PROPAGATE_TRANSIENT_MARKER, true).addModule(audioPerfectPitchConfigSerializationModule).build();
        this.objectMapper = jsonMapper;
    }

    private SimpleModule createAudioPerfectPitchConfigSerializationModule() {
        var module = new SimpleModule();

        return module;
    }

    public String serializeDomainEvent(final DomainEvent event) {
        return objectMapper.writeValueAsString(event);
    }

    public String serializePuzzleConfig(final PuzzleConfigAggregate<?> puzzleConfig) {
        return objectMapper.writeValueAsString(puzzleConfig);
    }

    public <E extends Exercise> PuzzleConfigAggregate<?> deserializePuzzleConfig(final E exercise, final String serializedPuzzleConfig) {
        // TODO select in specific method
        var jsonTree = objectMapper.readTree(serializedPuzzleConfig);

        JsonNode idNode = jsonTree.get("id");

        var exerciseNameToken = idNode.get("name").asString();
        var exerciseName = ExerciseNames.valueOf(exerciseNameToken);

        var exerciseTypeToken = idNode.get("type").asString();
        var exerciseType = ExerciseTypes.valueOf(exerciseTypeToken);

        var deserializedExercise = ExercisesFactory.create(exerciseName, exerciseType);
        if (!exercise.equals(deserializedExercise)) {
            throw new IllegalArgumentException("exercise arg and actual exercise in puzzle config are different");
        }

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
        
        // TODO see comment inside this method
        var depenentAggregates = InMemoryPuzzleConfigRepository.getAudioPerfectPitchConfigDependentAggregates(exercise);
        var pianoKeyboardAggregates = depenentAggregates.pianoKeyboardAggregates;
        
        var puzzleConfig = new AudioPerfectPitchConfigAggregate((AudioPerfectPitchExercise) exercise, targetNumberOfPuzzles, statsRecording, normalizedNotesForPuzzle, normalizedRootNote, inputMode, soundlessGuessingPiano, pianoKeyboardAggregates);
        return puzzleConfig;
    }

}

// // TODO delete or use it
// public class CustomAudioPerfectPitchConfigAggregateDeserializer extends StdDeserializer<AudioPerfectPitchConfigAggregate> {

//     public CustomAudioPerfectPitchConfigAggregateDeserializer() {
//         this(null);
//     }

//     public CustomAudioPerfectPitchConfigAggregateDeserializer(final Class<?> vc) {
//         super(vc);
//     }

//     @Override
//     // TODO is final for method params in interface propagated to implementing classes or not?
//     public AudioPerfectPitchConfigAggregate deserialize(final JsonParser parser, final DeserializationContext ctxt) {

//     }
// }
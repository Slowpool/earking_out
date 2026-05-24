package org.swetlokognatsk.earking_out.core.ports;

import org.swetlokognatsk.earking_out.core.ports.config.ReadPuzzleConfigService;
import org.swetlokognatsk.earking_out.core.ports.config.WritePuzzleConfigService;
import org.swetlokognatsk.earking_out.core.ports.hints.HintFinder;
import org.swetlokognatsk.earking_out.core.ports.hints.perfectPitch.PerfectPitchHintFinder;
import org.swetlokognatsk.earking_out.core.ports.music.NoteNormalizer;
import org.swetlokognatsk.earking_out.core.ports.puzzles.PuzzleGenerator;
import org.swetlokognatsk.earking_out.core.ports.session.services.ReadSessionService;
import org.swetlokognatsk.earking_out.core.ports.session.services.WriteSessionService;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.NoteNormalizerImpl;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.hints.HintFinderByExercise;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.hints.perfectPitch.FakeAudioPerfectPitchHints;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.hints.perfectPitch.FakePerfectPitchHintFinder;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.hints.perfectPitch.FakeVisualPerfectPitchHints;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.puzzles.FakePuzzleGenerator;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.puzzles.configs.InMemoryReadPuzzleConfigService;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.puzzles.configs.InMemoryWritePuzzleConfigService;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.session.services.InMemoryReadSessionService;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.session.services.InMemoryWriteSessionService;

// TODO for now this class was made strictly in test purposes, to postpone DI in java
final public class DI {

    private DI() {
    }

    public static <T> T get(Class<T> someClass) {
        var className = someClass.getName();
        // case IPuzzleGenerator.class.getName() -> new TestPuzzleGenerator();
        if (className == NoteNormalizer.class.getName()) {
            return (T) new NoteNormalizerImpl();
        } else if (className == PuzzleGenerator.class.getName()) {
            return (T) new FakePuzzleGenerator();
        } else if (className == HintFinder.class.getName()) {
            return (T) new HintFinderByExercise();
        } else if (className == PerfectPitchHintFinder.class.getName()) {
            return (T) new FakePerfectPitchHintFinder();
        } else if (className == FakeVisualPerfectPitchHints.class.getName()) {
            return (T) new FakeVisualPerfectPitchHints();
        } else if (className == FakeAudioPerfectPitchHints.class.getName()) {
            return (T) new FakeAudioPerfectPitchHints();
        } else if (className == ReadPuzzleConfigService.class.getName()) {
            return (T) new InMemoryReadPuzzleConfigService();
        } else if (className == WritePuzzleConfigService.class.getName()) {
            return (T) new InMemoryWritePuzzleConfigService();
        } else if (className == WriteSessionService.class.getName()) {
            return (T) new InMemoryWriteSessionService();
        } else if (className == ReadSessionService.class.getName()) {
            return (T) new InMemoryReadSessionService();
        } else {
            return null;
        }
    }
}

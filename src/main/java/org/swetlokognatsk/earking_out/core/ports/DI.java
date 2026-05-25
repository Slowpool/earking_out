package org.swetlokognatsk.earking_out.core.ports;

import org.swetlokognatsk.earking_out.app.desktop.services.AudioHintPlayer;
import org.swetlokognatsk.earking_out.app.desktop.services.AudioclipHintPlayer;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.typed.AudioPerfectPitchConfig;
import org.swetlokognatsk.earking_out.core.ports.config.ReadPuzzleConfigService;
import org.swetlokognatsk.earking_out.core.ports.config.WritePuzzleConfigService;
import org.swetlokognatsk.earking_out.core.ports.hints.HintFinder;
import org.swetlokognatsk.earking_out.core.ports.hints.perfectPitch.AudioPerfectPitchHints;
import org.swetlokognatsk.earking_out.core.ports.hints.perfectPitch.VisualPerfectPitchHints;
import org.swetlokognatsk.earking_out.core.ports.music.NoteNormalizer;
import org.swetlokognatsk.earking_out.core.ports.puzzles.PuzzleGenerator;
import org.swetlokognatsk.earking_out.core.ports.puzzles.generators.perfectpitch.AudioPerfectPitchPuzzleGenerator;
import org.swetlokognatsk.earking_out.core.ports.session.services.ReadSessionService;
import org.swetlokognatsk.earking_out.core.ports.session.services.WriteSessionService;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.NoteNormalizerImpl;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.hints.HintFinderDelegator;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.hints.perfectPitch.FakeAudioPerfectPitchHints;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.hints.perfectPitch.FakeVisualPerfectPitchHints;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.hints.perfectPitch.InMemoryAudioPerfectPitchHints;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.hints.perfectPitch.InMemoryVisualPerfectPitchHints;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.puzzles.configs.InMemoryReadPuzzleConfigService;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.puzzles.configs.InMemoryWritePuzzleConfigService;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.puzzles.generators.perfectpitch.FakeAudioPerfectPitchPuzzleGenerator;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.puzzles.generators.perfectpitch.RandomAudioPerfectPitchPuzzleGenerator;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.session.services.InMemoryReadSessionService;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.session.services.InMemoryWriteSessionService;

// TODO for now this class was made strictly in test purposes, to postpone DI in java
final public class DI {
    public static String TEST_ENV = "test_env";
    public static String PROD_ENV = "prod_env";
    public static String env = "test_env";

    private DI() {
    }

    public static <T> T get(Class<T> someClass, Object... args) {
        var className = someClass.getName();
        // case IPuzzleGenerator.class.getName() -> new TestPuzzleGenerator();
        if (className == NoteNormalizer.class.getName()) {
            return (T) new NoteNormalizerImpl();

        } else if (className == HintFinder.class.getName()) {
            return (T) new HintFinderDelegator();
        } else if (className == VisualPerfectPitchHints.class.getName()) {
            return (T) (env == TEST_ENV ? new FakeVisualPerfectPitchHints() : new InMemoryVisualPerfectPitchHints());
        } else if (className == AudioPerfectPitchHints.class.getName()) {
            return (T) (env == TEST_ENV ? new FakeAudioPerfectPitchHints() : new InMemoryAudioPerfectPitchHints());

        } else if (className == ReadPuzzleConfigService.class.getName()) {
            return (T) new InMemoryReadPuzzleConfigService();
        } else if (className == WritePuzzleConfigService.class.getName()) {
            return (T) new InMemoryWritePuzzleConfigService();

        } else if (className == WriteSessionService.class.getName()) {
            return (T) new InMemoryWriteSessionService();
        } else if (className == ReadSessionService.class.getName()) {
            return (T) new InMemoryReadSessionService();

        } else if (className == AudioPerfectPitchPuzzleGenerator.class.getName()) {
            return (T) (env == TEST_ENV ? new FakeAudioPerfectPitchPuzzleGenerator() : new RandomAudioPerfectPitchPuzzleGenerator((AudioPerfectPitchConfig)args[0]));

        } else if (className == AudioHintPlayer.class.getName()) {
            return (T) new AudioclipHintPlayer();

        } else {
            return null;
        }
    }
}

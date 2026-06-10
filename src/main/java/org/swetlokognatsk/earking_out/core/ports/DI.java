package org.swetlokognatsk.earking_out.core.ports;

import org.swetlokognatsk.earking_out.app.desktop.services.AudioHintPlayer;
import org.swetlokognatsk.earking_out.app.desktop.services.KeySoundsFromHintsService;
import org.swetlokognatsk.earking_out.app.desktop.services.KeySoundsService;
import org.swetlokognatsk.earking_out.app.desktop.services.AudioClipHintPlayer;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.AudioPerfectPitchConfig;
import org.swetlokognatsk.earking_out.core.domain.services.app.PuzzleConfigService;
import org.swetlokognatsk.earking_out.core.domain.services.domain.piano.PianoKeyColorService;
import org.swetlokognatsk.earking_out.core.domain.services.domain.piano.PianoKeyColorServiceImpl;
import org.swetlokognatsk.earking_out.core.ports.config.PuzzleConfigRepository;
import org.swetlokognatsk.earking_out.core.ports.config.ReadPuzzleConfigService;
import org.swetlokognatsk.earking_out.core.ports.config.WritePuzzleConfigService;
import org.swetlokognatsk.earking_out.core.ports.hints.HintFinder;
import org.swetlokognatsk.earking_out.core.ports.hints.perfectPitch.AudioPerfectPitchHints;
import org.swetlokognatsk.earking_out.core.ports.hints.perfectPitch.VisualPerfectPitchHints;
import org.swetlokognatsk.earking_out.core.ports.music.NoteNormalizer;
import org.swetlokognatsk.earking_out.core.ports.puzzles.generators.perfectpitch.AudioPerfectPitchPuzzleGenerator;
import org.swetlokognatsk.earking_out.core.ports.session.services.ReadSessionService;
import org.swetlokognatsk.earking_out.core.ports.session.services.WriteSessionService;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.NoteNormalizerImpl;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.hints.HintFinderDelegator;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.hints.perfectPitch.FakeAudioPerfectPitchHints;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.hints.perfectPitch.FakeVisualPerfectPitchHints;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.hints.perfectPitch.InMemoryAudioPerfectPitchHints;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.hints.perfectPitch.InMemoryVisualPerfectPitchHints;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.puzzles.configs.InMemoryPuzzleConfigRepository;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.puzzles.configs.InMemoryReadPuzzleConfigService;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.puzzles.configs.InMemoryWritePuzzleConfigService;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.puzzles.generators.perfectpitch.FakeAudioPerfectPitchPuzzleGenerator;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.puzzles.generators.perfectpitch.RandomAudioPerfectPitchPuzzleGenerator;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.session.services.InMemoryReadSessionService;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.session.services.InMemoryWriteSessionService;

// TODO for now this class was made strictly in test purposes, to postpone DI in java
public final class DI {
    public static String TEST_ENV = "test_env";
    public static String PROD_ENV = "prod_env";
    public static String env = "test_env";

    private DI() {
    }

    public static <T> T get(Class<T> someClass, Object... args) {
        var className = someClass.getName();
        if (className.equals(NoteNormalizer.class.getName())) {
            return (T) new NoteNormalizerImpl();

        } else if (className.equals(HintFinder.class.getName())) {
            return (T) new HintFinderDelegator();
        } else if (className.equals(VisualPerfectPitchHints.class.getName())) {
            return (T) (env.equals(TEST_ENV) ? new FakeVisualPerfectPitchHints() : new InMemoryVisualPerfectPitchHints());
        } else if (className == AudioPerfectPitchHints.class.getName()) {
            return (T) (env.equals(TEST_ENV) ? new FakeAudioPerfectPitchHints() : new InMemoryAudioPerfectPitchHints());

        } else if (className.equals(ReadPuzzleConfigService.class.getName())) {
            return (T) new InMemoryReadPuzzleConfigService();
        } else if (className.equals(WritePuzzleConfigService.class.getName())) {
            return (T) new InMemoryWritePuzzleConfigService();

        } else if (className.equals(WriteSessionService.class.getName())) {
            return (T) new InMemoryWriteSessionService();
        } else if (className.equals(ReadSessionService.class.getName())) {
            return (T) new InMemoryReadSessionService();

        } else if (className.equals(AudioPerfectPitchPuzzleGenerator.class.getName())) {
            return (T) (env.equals(TEST_ENV) ? new FakeAudioPerfectPitchPuzzleGenerator() : new RandomAudioPerfectPitchPuzzleGenerator((AudioPerfectPitchConfig) args[0]));

        } else if (className.equals(AudioHintPlayer.class.getName())) {
            return (T) new AudioClipHintPlayer();

        } else if (className.equals(KeySoundsService.class.getName())) {
            return (T) new KeySoundsFromHintsService();

        } else if (className.equals(PianoKeyColorService.class.getName())) {
            return (T) new PianoKeyColorServiceImpl();

        } else if (className.equals(PuzzleConfigService.class.getName())) {
            return (T) new PuzzleConfigService(get(PuzzleConfigRepository.class));

        } else if (className.equals(InMemoryWritePuzzleConfigService.class.getName())) {
            return (T) new InMemoryWritePuzzleConfigService();

        } else if (className.equals(InMemoryReadPuzzleConfigService.class.getName())) {
            return (T) new InMemoryReadPuzzleConfigService();

        } else if (className.equals(PuzzleConfigRepository.class.getName())) {
            var writeService = get(InMemoryWritePuzzleConfigService.class);
            var readService = get(InMemoryReadPuzzleConfigService.class);
            return (T) new InMemoryPuzzleConfigRepository(writeService, readService);

        } else {
            return null;
        }
    }
}

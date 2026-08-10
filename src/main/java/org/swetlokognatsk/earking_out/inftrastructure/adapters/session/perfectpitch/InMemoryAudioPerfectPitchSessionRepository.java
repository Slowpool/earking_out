package org.swetlokognatsk.earking_out.inftrastructure.adapters.session.perfectpitch;

import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardId;
import org.swetlokognatsk.earking_out.core.domain.model.session.factories.SessionAggregatesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.session.perfectpitch.AudioPerfectPitchSessionAggregate;
import org.swetlokognatsk.earking_out.core.ports.piano.PianoKeyboardRepository;
import org.swetlokognatsk.earking_out.core.ports.session.perfectpitch.AudioPerfectPitchSessionRepository;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.session.InMemorySessionRepository;

public final class InMemoryAudioPerfectPitchSessionRepository extends InMemorySessionRepository<AudioPerfectPitchSessionAggregate> implements AudioPerfectPitchSessionRepository {
    private final PianoKeyboardRepository pianoKeyboardRepository;

    public InMemoryAudioPerfectPitchSessionRepository(final SessionAggregatesFactory sessionAggregatesFactory, final PianoKeyboardRepository pianoKeyboardRepository) {
        super(sessionAggregatesFactory);

        this.pianoKeyboardRepository = pianoKeyboardRepository;
    }

    protected void loadDependentAggregates(final AudioPerfectPitchSessionAggregate sessionAggregate) {
        var notesGuessingPianoKeyboard = pianoKeyboardRepository.get(PianoKeyboardId.AUDIO_PERFECT_PITCH_NOTES_GUESSING);
        sessionAggregate.setGuessingPianoKeyboard(notesGuessingPianoKeyboard);
    }

    protected void saveDependentAggregates(final AudioPerfectPitchSessionAggregate sessionAggregate) {
        pianoKeyboardRepository.save(sessionAggregate.getGuessingPianoKeyboard());
    }
}

package org.swetlokognatsk.earking_out.core.domain.services.app.session.perfectpitch;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.AudioPerfectPitchConfigAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionId;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionStates;
import org.swetlokognatsk.earking_out.core.domain.model.session.exceptions.InvalidTextNoteException;
import org.swetlokognatsk.earking_out.core.domain.model.session.exceptions.OutOfRangeTextNoteException;
import org.swetlokognatsk.earking_out.core.domain.model.session.factories.SessionAggregatesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.session.perfectpitch.AudioPerfectPitchSessionAggregate;
import org.swetlokognatsk.earking_out.core.domain.services.app.session.EventLogPersistingException;
import org.swetlokognatsk.earking_out.core.domain.services.app.session.SessionService;
import org.swetlokognatsk.earking_out.core.domain.services.domain.puzzles.configs.perfectpitch.FinalizedAudioPerfectPitchConfigValidator;
import org.swetlokognatsk.earking_out.core.ports.config.PersistentPuzzleConfigRepository;
import org.swetlokognatsk.earking_out.core.ports.session.perfectpitch.AudioPerfectPitchSessionRepository;
import org.swetlokognatsk.earking_out.infrastructure.AsyncTaskExecutorByUser;
import static org.swetlokognatsk.earking_out.core.domain.model.exercises.ExercisesFactory.*;
import java.util.LinkedList;
import java.util.concurrent.ExecutionException;
import org.springframework.stereotype.Service;

@Service
public final class AudioPerfectPitchSessionService extends SessionService<AudioPerfectPitchExercise, AudioPerfectPitchSessionAggregate, AudioPerfectPitchSessionRepository, AudioPerfectPitchConfigAggregate, FinalizedAudioPerfectPitchConfigValidator> {
    private AsyncTaskExecutorByUser asyncTaskExecutorByUser;

    protected AudioPerfectPitchExercise getExercise() {
        return AUDIO_PERFECT_PITCH_EXERCISE;
    }

    public AudioPerfectPitchSessionService(final PersistentPuzzleConfigRepository puzzleConfigRepository, final AudioPerfectPitchSessionRepository sessionRepository, final SessionAggregatesFactory sessionAggregatesFactory, final FinalizedAudioPerfectPitchConfigValidator configValidator, final AsyncTaskExecutorByUser asyncTaskExecutorByUser) {
        super(puzzleConfigRepository, sessionRepository, sessionAggregatesFactory, configValidator);
        this.asyncTaskExecutorByUser = asyncTaskExecutorByUser;
    }

    public void guessViaPianoKeyPressing(final PianoKeyNumber keyNumber) {
        var session = (AudioPerfectPitchSessionAggregate) sessionRepository.getActiveSession();
        session.guessViaPianoKeyPressing(keyNumber);
        sessionRepository.save(session);

        tryPersistQueuedEventLogs(session.getState());
    }

    public void guessViaTextNote(final String textNote) throws InvalidTextNoteException, OutOfRangeTextNoteException {
        var session = (AudioPerfectPitchSessionAggregate) sessionRepository.getActiveSession();
        session.guessViaTextNote(textNote);
        sessionRepository.save(session);

        tryPersistQueuedEventLogs(session.getState());
    }

    // TODO now this is a temporary hack that executes all globally queued tasks. why to do it? events a logged asynchronously, and if user finishes the session before all events are logged, stats will be based on partial event logs and consequently they will be incorrect. debugging i figured out it's still imperfect, - there's still a possibility of wrong stats, but it's much lower
    // TODO use session state directly in calling code if possible
    private void tryPersistQueuedEventLogs(final SessionStates sessionState) {
        if (sessionState == SessionStates.COMPLETED || sessionState == SessionStates.ABORTED) {
            try {
                asyncTaskExecutorByUser.executeSynchronously();
            } catch (ExecutionException e) {
                throw new EventLogPersistingException(e.getCause());
            }
        }
    }

    @Deprecated
    public void hearAgain(final SessionId sessionId) {
        // TODO replace with getActiveSession()
        var session = (AudioPerfectPitchSessionAggregate) sessionRepository.get(sessionId);
        session.demonstrateHintAgain();
        sessionRepository.save(session);
    }

    public void hearAgain() {
        var session = (AudioPerfectPitchSessionAggregate) sessionRepository.getActiveSession();
        session.demonstrateHintAgain();
        sessionRepository.save(session);
    }
}

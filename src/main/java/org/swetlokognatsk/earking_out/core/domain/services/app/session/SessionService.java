package org.swetlokognatsk.earking_out.core.domain.services.app.session;

import java.util.Objects;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfigAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionId;
import org.swetlokognatsk.earking_out.core.domain.model.session.factories.SessionAggregatesFactory;
import org.swetlokognatsk.earking_out.core.domain.services.domain.puzzles.configs.FinalizedPuzzleConfigValidator;
import org.swetlokognatsk.earking_out.core.domain.services.domain.puzzles.configs.PuzzleConfigValidator;
import org.swetlokognatsk.earking_out.core.domain.services.domain.puzzles.configs.exceptions.InvalidPuzzleConfigException;
import org.swetlokognatsk.earking_out.core.ports.config.PuzzleConfigRepository;
import org.swetlokognatsk.earking_out.core.ports.session.SessionRepository;

public abstract class SessionService<E extends Exercise, SA extends SessionAggregate<E, ?, ?, ?>, SP extends SessionRepository<SA>, PCA extends PuzzleConfigAggregate<E>, FPCV extends FinalizedPuzzleConfigValidator<PCA>> {
    protected final PuzzleConfigRepository puzzleConfigRepository;
    protected final SP sessionRepository;
    protected final SessionAggregatesFactory sessionAggregatesFactory;
    protected final FPCV configValidator;

    protected abstract E getExercise();

    public SessionService(final PuzzleConfigRepository puzzleConfigRepository, final SP sessionRepository, final SessionAggregatesFactory sessionAggregatesFactory, final FPCV configValidator) {
        this.puzzleConfigRepository = Objects.requireNonNull(puzzleConfigRepository);
        this.sessionRepository = Objects.requireNonNull(sessionRepository);
        this.sessionAggregatesFactory = Objects.requireNonNull(sessionAggregatesFactory);
        this.configValidator = Objects.requireNonNull(configValidator);
    }

    public final SessionId start() throws InvalidPuzzleConfigException {
        validatePuzzleConfig();

        var session = (SA) sessionAggregatesFactory.create(getExercise());
        sessionRepository.save(session);
        return session.getId();
    }

    private void validatePuzzleConfig() throws InvalidPuzzleConfigException {
        var puzzleConfig = (PCA) puzzleConfigRepository.get(getExercise());

        var validationResult = configValidator.validate(puzzleConfig);
        if (!validationResult.isValid()) {
            var puzzleConfigDto = puzzleConfigRepository.getPuzzleConfigDTO(getExercise());
            throw new InvalidPuzzleConfigException(puzzleConfigDto, validationResult.errors());
        }
    }

    public final void abort(final SessionId sessionId) {
        var session = sessionRepository.get(sessionId);
        session.abort();
        sessionRepository.save(session);
    }

}

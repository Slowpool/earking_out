package org.swetlokognatsk.earking_out.core.domain.services.app;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.session.Session;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.PuzzleConfigDTO;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.PuzzleConfigDTOAssembler;
import org.swetlokognatsk.earking_out.core.domain.services.app.exceptions.InvalidPuzzleConfigException;
import org.swetlokognatsk.earking_out.core.ports.DI;
import org.swetlokognatsk.earking_out.core.ports.config.PuzzleConfigRepository;
import org.swetlokognatsk.earking_out.core.ports.session.services.ReadSessionService;
import org.swetlokognatsk.earking_out.core.ports.session.services.WriteSessionService;

public final class SessionService {
    protected final PuzzleConfigRepository puzzleConfigRepository;

    public SessionService(final PuzzleConfigRepository puzzleConfigRepository) {
        this.puzzleConfigRepository = puzzleConfigRepository;

    }

    public <E extends Exercise, PCDTO extends PuzzleConfigDTO<E>> Session<PCDTO> startSession(final E exercise) {
        var puzzleConfig = puzzleConfigRepository.get(exercise);

        var puzzleConfigDto = PuzzleConfigDTOAssembler.assemble(puzzleConfig);
        if (!puzzleConfig.isValid()) {
            throw new InvalidPuzzleConfigException(puzzleConfigDto);
        }

        var session = startSession(puzzleConfigDto);
        return (Session<PCDTO>) session;
    }

    // TODO review. how to design Session so that only one object of it can exist at a time
    private static <PCDTO extends PuzzleConfigDTO<?>> Session<PCDTO> startSession(final PCDTO puzzleConfigDto) {
        var writeSessionService = DI.get(WriteSessionService.class);
        writeSessionService.createSession(puzzleConfigDto);

        var readSessionService = DI.get(ReadSessionService.class);
        var session = (Session<PCDTO>) readSessionService.getCurrentSession();

        return session;
    }
}

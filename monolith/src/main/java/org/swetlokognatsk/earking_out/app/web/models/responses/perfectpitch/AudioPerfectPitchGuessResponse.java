package org.swetlokognatsk.earking_out.app.web.models.responses.perfectpitch;

import org.swetlokognatsk.earking_out.app.web.models.responses.GuessResponse;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionStates;

// possibly it's redundant, GuessResponse can be used instead
public final class AudioPerfectPitchGuessResponse extends GuessResponse {

    public AudioPerfectPitchGuessResponse(final SessionStates sessionState, final String newHint, final int numberOfCompletedPuzzles, final boolean guessIsSuccessful, final String sessionStatsHtml) {
        super(sessionState, newHint, numberOfCompletedPuzzles, guessIsSuccessful, sessionStatsHtml);
    }
}

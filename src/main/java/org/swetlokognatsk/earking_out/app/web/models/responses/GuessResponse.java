package org.swetlokognatsk.earking_out.app.web.models.responses;

import org.swetlokognatsk.earking_out.core.domain.model.session.SessionStates;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class GuessResponse {
    public final SessionStates sessionState;
    public final String newHint;
    public final int numberOfCompletedPuzzles;
    public final boolean guessIsSuccessful;
    public final String sessionStatsHtml;
}

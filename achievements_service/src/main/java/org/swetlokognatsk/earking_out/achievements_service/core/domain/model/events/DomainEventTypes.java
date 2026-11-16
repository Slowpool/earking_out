package org.swetlokognatsk.earking_out.achievements_service.core.domain.model.events;

public final class DomainEventTypes {
    public static final String NEW_PUZZLE = "NewPuzzleCreatedEvent";
    public static final String SESSION_FINISHED = "SessionFinishedEvent";
    public static final String SESSION_STARTED = "SessionStartedEvent";
    public static final String GUESS = "UserTriedToGuessPuzzleEvent";

    private DomainEventTypes() {
    }

}

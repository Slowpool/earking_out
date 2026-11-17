package org.swetlokognatsk.earking_out.achievements_service.core.domain.services.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.swetlokognatsk.earking_out.achievements_service.core.domain.model.UserId;
import org.swetlokognatsk.earking_out.achievements_service.core.domain.model.events.DomainEvent;
import org.swetlokognatsk.earking_out.achievements_service.core.domain.model.events.DomainEventTypes;
import static org.junit.jupiter.api.Assertions.*;
import static org.swetlokognatsk.earking_out.achievements_service.core.domain.model.achievements.Achievements.*;
import static org.swetlokognatsk.earking_out.achievements_service.core.domain.model.events.DomainEventTypes.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.swetlokognatsk.earking_out.achievements_service.core.domain.model.events.DomainEvents;
import org.swetlokognatsk.earking_out.achievements_service.core.domain.DomainEventsTestHelper;
import static org.swetlokognatsk.earking_out.achievements_service.core.domain.DomainEventsTestHelper.*;

public class AchievementsUnlockerServiceTest {

    private AchievementsUnlockerService achievementsUnlocker = new AchievementsUnlockerService();
    private final DomainEventsTestHelper domainEventsHelper = new DomainEventsTestHelper();

    private DomainEvents generateFakeAudioPerfectPitchFirstSuccessEvents(final boolean success) {
        var properties = newProperties();
        properties.put("success", success);
        var guessEvent = new DomainEvent(GUESS, USER_ID, LocalDateTime.now(), properties);

        var domainEvents = newDomainEvents(GUESS, guessEvent);
        return domainEvents;
    }

    private DomainEvents generateFakeFinishXAudioPerfectPitchSessionsEvents(final int numberOfFinishedSessions) {
        var properties = newProperties();
        var exercise = newProperties();
        exercise.put("name", PERFECT_PITCH);
        exercise.put("type", AUDIO);
        var puzzleConfigDto = newProperties();
        puzzleConfigDto.put("exercise", exercise);
        properties.put("puzzleConfigDto", puzzleConfigDto);
        var fakeEvents = new ArrayList<DomainEvent>(numberOfFinishedSessions);
        for (int i = 0; i < numberOfFinishedSessions; i++) {
            var event = new DomainEvent(SESSION_STARTED, USER_ID, LocalDateTime.now(), properties);
            fakeEvents.add(event);
        }
        var domainEvents = new DomainEvents();
        domainEvents.put(SESSION_STARTED, fakeEvents);
        return domainEvents;
    }

    private DomainEvents newDomainEvents(final String eventType, final DomainEvent... domainEvents) {
        var eventsCollection = new DomainEvents();
        eventsCollection.put(eventType, Arrays.asList(domainEvents));
        return eventsCollection;
    }

    private DomainEvent newSessionFinishedEvent(final UserId userId) {
        return new DomainEvent(SESSION_FINISHED, userId, LocalDateTime.now(), newProperties());
    }

    @Test
    void positiveUnlockFirstSuccessInAudioPerfectPitch() {
        var domainEvents = generateFakeAudioPerfectPitchFirstSuccessEvents(true);
        var guessEvent = domainEvents.get(GUESS)
                .getFirst();

        var unlockedAchievement = achievementsUnlocker.tryUnlock(FIRST_AUDIO_PERFECT_PITCH_SUCCESS, domainEvents, guessEvent);

        assertNotNull(unlockedAchievement);
        assertEquals(FIRST_AUDIO_PERFECT_PITCH_SUCCESS.id, unlockedAchievement.id);
        assertEquals(guessEvent.userId(), unlockedAchievement.userId);
    }

    @Test
    void negativeUnlockFirstSuccessInAudioPerfectPitch() {
        var domainEvents = generateFakeAudioPerfectPitchFirstSuccessEvents(false);
        var guessEvent = domainEvents.get(GUESS)
                .getFirst();

        var unlockedAchievement = achievementsUnlocker.tryUnlock(FIRST_AUDIO_PERFECT_PITCH_SUCCESS, domainEvents, guessEvent);

        assertNull(unlockedAchievement);
    }

    @Test
    void positiveUnlockFinish10AudioPerfectPitchSessions() {
        var domainEvents = generateFakeFinishXAudioPerfectPitchSessionsEvents(11);
        var lastSessionFinishedEvent = domainEvents.get(SESSION_STARTED)
                .getLast();

        var unlockedAchievement = achievementsUnlocker.tryUnlock(FINISH_10_AUDIO_PERFECT_PITCH_SESSIONS, domainEvents, lastSessionFinishedEvent);

        assertNotNull(unlockedAchievement);
        assertEquals(FINISH_10_AUDIO_PERFECT_PITCH_SESSIONS.id, unlockedAchievement.id);
        assertEquals(lastSessionFinishedEvent.userId(), unlockedAchievement.userId);
    }

    @Test
    void negativeUnlockFinish10AudioPerfectPitchSessions() {
        var domainEvents = generateFakeFinishXAudioPerfectPitchSessionsEvents(10);
        var lastSessionFinishedEvent = domainEvents.get(SESSION_STARTED)
                .getLast();

        var unlockedAchievement = achievementsUnlocker.tryUnlock(FINISH_10_AUDIO_PERFECT_PITCH_SESSIONS, domainEvents, lastSessionFinishedEvent);

        assertNull(unlockedAchievement);
    }

    @Test
    void negativeUnlockFinish10AudioPerfectPitchSessionsWithWrongUser() {
        var domainEvents = generateFakeFinishXAudioPerfectPitchSessionsEvents(9);
        domainEvents.compute(DomainEventTypes.SESSION_STARTED, (String type, List<DomainEvent> events) -> {
            events.add(newSessionFinishedEvent(ANOTHER_USER_ID));
            return events;
        });
        var lastSessionFinishedEvent = domainEvents.get(SESSION_STARTED)
                .getLast();

        var unlockedAchievement = achievementsUnlocker.tryUnlock(FINISH_10_AUDIO_PERFECT_PITCH_SESSIONS, domainEvents, lastSessionFinishedEvent);

        assertNull(unlockedAchievement);
    }

}

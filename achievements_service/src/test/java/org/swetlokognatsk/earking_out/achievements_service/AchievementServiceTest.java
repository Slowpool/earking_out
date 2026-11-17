package org.swetlokognatsk.earking_out.achievements_service;

import static org.swetlokognatsk.earking_out.achievements_service.core.domain.model.achievements.Achievements.FIRST_AUDIO_PERFECT_PITCH_SUCCESS;
import static org.swetlokognatsk.earking_out.achievements_service.core.domain.model.events.DomainEventTypes.GUESS;
import static org.swetlokognatsk.earking_out.achievements_service.core.domain.model.events.DomainEventTypes.SESSION_FINISHED;
import static org.swetlokognatsk.earking_out.achievements_service.core.domain.model.events.DomainEventTypes.SESSION_STARTED;
import java.time.LocalDateTime;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.swetlokognatsk.earking_out.achievements_service.core.domain.model.UserId;
import org.swetlokognatsk.earking_out.achievements_service.core.domain.model.achievements.Achievements;
import org.swetlokognatsk.earking_out.achievements_service.core.domain.model.achievements.UnlockedAchievement;
import org.swetlokognatsk.earking_out.achievements_service.core.domain.model.events.DomainEvent;
import org.swetlokognatsk.earking_out.achievements_service.core.domain.model.events.DomainEvents;
import org.swetlokognatsk.earking_out.achievements_service.core.domain.services.app.AchievementService;
import org.swetlokognatsk.earking_out.achievements_service.core.domain.services.domain.AchievementsUnlockerService;
import org.swetlokognatsk.earking_out.achievements_service.core.ports.HttpClient;
import org.swetlokognatsk.earking_out.achievements_service.core.ports.events.DomainEventDeserializer;
import org.swetlokognatsk.earking_out.achievements_service.core.ports.messagebroker.MessageBroker;
import org.swetlokognatsk.earking_out.achievements_service.infrastructure.adapters.events.JacksonDeserializer;
import static org.swetlokognatsk.earking_out.achievements_service.core.domain.DomainEventsTestHelper.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.swetlokognatsk.earking_out.achievements_service.core.domain.DomainEventsTestHelper.*;

@ExtendWith(MockitoExtension.class)
class AchievementServiceTest {

	@Mock
	private MessageBroker messageBroker;

	@Spy
	private DomainEventDeserializer domainEventDeserializer = new JacksonDeserializer();

	@Mock
	private HttpClient httpClient;

	@Spy
	private AchievementsUnlockerService unlockerService = new AchievementsUnlockerService();

	@InjectMocks
	private AchievementService service;

	private DomainEvent createGuessEvent(final boolean successfulGuess) {
		var properties = newProperties();
		properties.put("success", successfulGuess);
		return new DomainEvent(GUESS, USER_ID, LocalDateTime.now(), properties);
	}

	private void mockHttpClientGetDomainEvents(final DomainEvent guessEvent) {
		when(httpClient.getDomainEvents(any(UserId.class), any(String[].class)))
				.thenReturn(new DomainEvents(guessEvent));
	}

	private void assertRequestedEventsAre(final String[] eventTypes) {
		verify(httpClient)
				.getDomainEvents(USER_ID, eventTypes);
	}

	@Test
	void tryMakeNewAchievementsAfterFirstSuccessfulGuess() {
		var guessEvent = createGuessEvent(true);
		mockHttpClientGetDomainEvents(guessEvent);

		service.tryMakeNewAchievements(guessEvent);

		assertRequestedEventsAre(new String[] { GUESS });
		var expectedUnlockedAchievement = new UnlockedAchievement(FIRST_AUDIO_PERFECT_PITCH_SUCCESS.id, USER_ID);
		verify(messageBroker)
				.sendNewAchievement(expectedUnlockedAchievement);
	}

	@Test
	void tryMakeNewAchievementsWithoutSuccessfulGuess() {
		var guessEvent = createGuessEvent(false);
		mockHttpClientGetDomainEvents(guessEvent);

		service.tryMakeNewAchievements(guessEvent);

		assertRequestedEventsAre(new String[] { GUESS });
		verify(messageBroker, never())
				.sendNewAchievement(any(UnlockedAchievement.class));
	}
}

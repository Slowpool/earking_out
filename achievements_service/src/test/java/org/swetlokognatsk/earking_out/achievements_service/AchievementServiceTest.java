package org.swetlokognatsk.earking_out.achievements_service;

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

	@Test
	void tryMakeNewAchievementsAfterGuess() {
		var eventTypesCaptor = ArgumentCaptor.forClass(String[].class);
		var properties = newProperties();
		// properties.put();
		var guessEvent = new DomainEvent(GUESS, USER_ID, LocalDateTime.now(), properties);
		when(httpClient.getDomainEvents(any(UserId.class), any(String[].class)))
				.thenReturn(new DomainEvents());

		service.tryMakeNewAchievements(guessEvent);

		verify(httpClient)
				.getDomainEvents(any(UserId.class), eventTypesCaptor.capture());
		var requestedEventTypes = eventTypesCaptor.getValue();
		assertArrayEquals(new String[] { GUESS }, requestedEventTypes);
	}

	@Test
	void doSomething() {
		var eventTypesCaptor = ArgumentCaptor.forClass(String[].class);
		when(httpClient.getDomainEvents(any(UserId.class), any(String[].class)))
				.thenReturn(null);
		var sessionFinishedEvent = new DomainEvent(SESSION_FINISHED, USER_ID, LocalDateTime.now(), null);

		service.tryMakeNewAchievements(sessionFinishedEvent);

		verify(httpClient)
				.getDomainEvents(any(UserId.class), eventTypesCaptor.capture());
		var requestedEventTypes = eventTypesCaptor.getValue();
		assertArrayEquals(new String[] { SESSION_FINISHED, SESSION_STARTED }, requestedEventTypes);
	}

}

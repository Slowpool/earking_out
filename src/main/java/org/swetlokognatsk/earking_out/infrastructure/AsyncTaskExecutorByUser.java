package org.swetlokognatsk.earking_out.infrastructure;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.swetlokognatsk.earking_out.core.domain.events.DomainEvent;
import org.swetlokognatsk.earking_out.core.domain.events.handlers.DomainEventHandler;
import org.swetlokognatsk.earking_out.core.domain.model.identity.UserId;
import org.swetlokognatsk.earking_out.core.ports.identity.UserResolver;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class AsyncTaskExecutorByUser {
    private final ThreadPoolTaskExecutor taskExecutor;
    private final UserResolver userResolver;
    private final Map<UserId, CompletableFuture<Void>> asyncHandlers = new ConcurrentHashMap<>();

    public <DE extends DomainEvent> DomainEventHandler<DE> wrapInAsync(DomainEventHandler<DE> handler) {
        return (DE domainEvent) -> {
            var userId = userResolver.getCurrentUserId();
            asyncHandlers.compute(userId, (_, completableFuture) -> {
                CompletableFuture<Void> future;
                if (completableFuture == null || completableFuture.isDone()) {
                    future = CompletableFuture.runAsync(() -> handler.handle(domainEvent), taskExecutor);
                } else {
                    future = completableFuture.thenRunAsync(() -> handler.handle(domainEvent), taskExecutor);
                }
                return future;
            });
        };
    }

    public void executeSynchronously() throws ExecutionException {
        var userId = userResolver.getCurrentUserId();
        var future = asyncHandlers.get(userId);
        try {
            future.get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}

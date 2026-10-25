package org.swetlokognatsk.earking_out.infrastructure.adapters.puzzles.configs;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.swetlokognatsk.earking_out.core.domain.model.base.AggregateRoot;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.identity.UserId;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfigAggregate;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.PuzzleConfigDTO;
import org.swetlokognatsk.earking_out.core.ports.config.CachingPuzzleConfigRepository;
import org.swetlokognatsk.earking_out.core.ports.config.PuzzleConfigJsonSerializer;
import org.swetlokognatsk.earking_out.core.ports.identity.UserResolver;
import org.swetlokognatsk.earking_out.infrastructure.annotations.WebRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@WebRepository
@AllArgsConstructor
@Getter(AccessLevel.PROTECTED)
public class RedisPuzzleConfigRepository implements CachingPuzzleConfigRepository {

    private final StringRedisTemplate redisTemplate;
    private final PuzzleConfigJsonSerializer puzzleConfigJsonSerializer;
    private final UserResolver userResolver;

    public <E extends Exercise, PCA extends AggregateRoot<E>> PCA get(final E exercise) {
        var key = buildKey(exercise);
        // TODO what do these `ops` word mean? possible commands for that data structure?
        var serializedPuzzleConfig = redisTemplate.opsForValue()
                .get(key);
        return (PCA) getPuzzleConfigJsonSerializer()
                .deserializePuzzleConfig(exercise, serializedPuzzleConfig);
    }

    public <E extends Exercise, PCDTO extends PuzzleConfigDTO<E>> PCDTO getPuzzleConfigDTO(final E exercise) {

    }

    public <E extends Exercise, PCDTO extends PuzzleConfigDTO<E>> PCDTO getPuzzleConfigDTO(final E exercise, final UserId userId) {

    }

    public <PCA extends PuzzleConfigAggregate<? extends Exercise>> void save(final PCA puzzleConfigAggregate) {
        var serializedPuzzleConfig = getPuzzleConfigJsonSerializer()
                .serializePuzzleConfig(puzzleConfigAggregate);

        var key = buildKey(puzzleConfigAggregate);

        redisTemplate.opsForValue()
                .set(key, serializedPuzzleConfig);
    }

    private final String buildKey(final PuzzleConfigAggregate<?> puzzleConfigAggregate) {
        var userId = puzzleConfigAggregate.getUserId();
        var exercise = puzzleConfigAggregate.getId();
        return buildKey(userId, exercise);
    }

    private final String buildKey(final Exercise exercise) {
        var userId = userResolver.getCurrentUserId();
        return buildKey(userId, exercise);
    }

    private final String buildKey(final UserId userId, final Exercise exercise) {
        return "puzzle_configs:%d:%s".formatted(userId.id(), exercise.toString());
    }
}

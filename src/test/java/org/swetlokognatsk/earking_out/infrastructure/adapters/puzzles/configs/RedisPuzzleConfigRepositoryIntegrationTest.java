package org.swetlokognatsk.earking_out.infrastructure.adapters.puzzles.configs;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import org.swetlokognatsk.earking_out.EOSpringBootTest;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.identity.UserId;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfigAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.factories.PuzzleConfigAggregatesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.factories.perfectpitch.AudioPerfectPitchConfigAggregatesFactory;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.PuzzleConfigDTOAssembler;
import org.swetlokognatsk.earking_out.core.ports.config.PuzzleConfigJsonSerializer;
import org.swetlokognatsk.earking_out.core.ports.di.DI;
import org.swetlokognatsk.earking_out.core.ports.identity.UserResolver;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.data.redis.core.StringRedisTemplate;

@Testcontainers
@EOSpringBootTest
public class RedisPuzzleConfigRepositoryIntegrationTest {

    @Container
    @ServiceConnection(name = "redis")
    private static GenericContainer<?> redis;

    private RedisPuzzleConfigRepository repository;
    private PuzzleConfigAggregatesFactory<?> puzzleConfigsFactory;
    private PuzzleConfigJsonSerializer puzzleConfigSerializer;
    private StringRedisTemplate redisTemplate;
    private UserResolver userResolver;

    static {
        redis = new GenericContainer<>(DockerImageName.parse("redis:8.10.2"))
                .withExposedPorts(6379)
                .withCommand("redis-server", "--databases", "1");
    }

    private String getSerializedConfigDirectly(final UserId userId, final Exercise exercise) {
        var key = "puzzle_configs:%d:%s".formatted(userId.id(), exercise);
        return redisTemplate.opsForValue()
                .get(key);
    }

    private void saveDirectly(final PuzzleConfigAggregate<?> puzzleConfig) {
        var serializedPuzzleConfig = puzzleConfigSerializer.serializePuzzleConfig(puzzleConfig);
        var key = buildKey(puzzleConfig);
        redisTemplate.opsForValue()
                .set(key, serializedPuzzleConfig);
    }

    private String buildKey(final PuzzleConfigAggregate<?> puzzleConfig) {
        var userId = puzzleConfig.getUserId()
                .id();
        var exercise = puzzleConfig.getId()
                .toString();
        return "puzzle_configs:%d:%s".formatted(userId, exercise);
    }

    private void assertRedisHasKey(final String key) {
        if (!redisTemplate.hasKey(key)) {
            fail("redis does not have the expected key");
        }
    }

    private void flushAllInRedis() {
        redisTemplate.getConnectionFactory()
                .getConnection()
                .serverCommands()
                .flushAll();
    }

    @BeforeEach
    public void setup() {
        // using `new` instead of `DI.get` because `test` spring profile ignores this bean on scanning. activating `web` profile in tests would make a lots of bean to init, degrading the performance
        repository = new RedisPuzzleConfigRepository(DI.get(StringRedisTemplate.class), DI.get(PuzzleConfigJsonSerializer.class), DI.get(UserResolver.class), DI.get(PuzzleConfigDTOAssembler.class));

        puzzleConfigsFactory = DI.get(AudioPerfectPitchConfigAggregatesFactory.class);
        puzzleConfigSerializer = DI.get(PuzzleConfigJsonSerializer.class);
        redisTemplate = DI.get(StringRedisTemplate.class);
        userResolver = DI.get(UserResolver.class);

        flushAllInRedis();
    }

    @Test
    public void saveSomeConfig() {
        var config = puzzleConfigsFactory.createDefault();
        var serializedConfig = puzzleConfigSerializer.serializePuzzleConfig(config);

        repository.save(config);

        assertRedisHasKey(buildKey(config));
        var userId = userResolver.getCurrentUserId();
        var exercise = config.getId();
        var obtainedSerializedConfig = getSerializedConfigDirectly(userId, exercise);
        assertEquals(serializedConfig, obtainedSerializedConfig);
    }

    @Test
    public void getSomeConfig() {
        var config = puzzleConfigsFactory.createDefault();
        saveDirectly(config);

        var obtainedConfig = (PuzzleConfigAggregate<?>) repository.get(config.getId());

        // serializing here is only for the purpose of comparing
        var serializedConfig = puzzleConfigSerializer.serializePuzzleConfig(config);
        var obtainedSerializedConfig = puzzleConfigSerializer.serializePuzzleConfig(obtainedConfig);
        assertEquals(serializedConfig, obtainedSerializedConfig);
    }

}

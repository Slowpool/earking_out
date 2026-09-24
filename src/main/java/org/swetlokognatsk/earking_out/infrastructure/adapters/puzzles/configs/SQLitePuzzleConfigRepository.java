package org.swetlokognatsk.earking_out.infrastructure.adapters.puzzles.configs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfigAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.factories.PuzzleConfigAggregatesFactoryResolver;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.PuzzleConfigDTOAssembler;
import org.swetlokognatsk.earking_out.core.ports.config.PuzzleConfigJsonSerializer;

/**
    awkward ad-hoc implementation to just make sure the business logic works
*/
@Deprecated
public final class SQLitePuzzleConfigRepository extends PersistentPuzzleConfigRepository {

    public static final String fullDbPath = "/Java/earking_out/earking_out.db";
    public static final String connectionString = String.format("jdbc:sqlite:%s", fullDbPath);

    // wild cratch to avoid 10000000 configs creating due to recursion in method
    static List<Exercise> alreadyCreatedConfigs = new LinkedList<Exercise>();

    @Deprecated
    public SQLitePuzzleConfigRepository(final PuzzleConfigAggregatesFactoryResolver puzzleConfigAggregatesFactoryResolver, final PuzzleConfigJsonSerializer puzzleConfigJsonSerializer, final PuzzleConfigDTOAssembler dtoAssembler, final InMemoryPuzzleConfigRepository cacheRepository) {
        super(puzzleConfigAggregatesFactoryResolver, puzzleConfigJsonSerializer, dtoAssembler, cacheRepository);
    }

    @Deprecated
    protected final String getOrCreatePuzzleConfigJson(final Exercise exercise) {
        var selectCommand = "SELECT `serialized_config` FROM `puzzle_configs` WHERE `exercise` = ?";

        try (Connection connection = DriverManager.getConnection(connectionString); var statement = connection.prepareStatement(selectCommand);) {
            var exerciseId = exercise.toString();
            statement.setString(1, exerciseId);

            var resultSet = statement.executeQuery();
            if (puzzleConfigIsFound(resultSet)) {
                var puzzleConfigJson = resultSet.getString("serialized_config");
                return puzzleConfigJson;
            } else {
                if (alreadyCreatedConfigs.contains(exercise)) {
                    throw new RuntimeException("attempt to create config that already exists");
                }
                createAndSaveDefaultConfig(exercise);
                alreadyCreatedConfigs.add(exercise);
                return getOrCreatePuzzleConfigJson(exercise);
            }
        } catch (SQLException e) {
            // TODO use it
            // throw new EventSavingException("failed to append event", e);
            throw new RuntimeException("failed to append event", e);
        }
    }

    // TODO liquibase
    private static void createPuzzleConfigsTable(String connectionString) {
        // try (Connection connection = DriverManager.getConnection(connectionString); var statement = connection.createStatement();) {
        //     // statement.executeUpdate("CREATE TABLE `puzzle_configs` (`exercise` TEXT NOT NULL, `serialized_config` TEXT NOT NULL)"); // and event sourcing: CREATE TABLE `event_sourcing_events` (`id` TEXT NOT NULL, `type` TEXT NOT NULL, `created_on` TEXT NOT NULL, `payload` TEXT NOT NULL);
        //     int i = 1;
        // } catch (Throwable e) {
        //     int i = 1;
        // }
    }

    private static boolean puzzleConfigIsFound(final ResultSet resultSet) throws SQLException {
        return resultSet.next();
    }

    @Deprecated
    public void genericSave(final PuzzleConfigAggregate<?> puzzleConfigAggregate) {
        var exercise = puzzleConfigAggregate.getId();

        var deleteCommand = "DELETE FROM `puzzle_configs` WHERE `exercise` = ?";
        var selectCommand = "INSERT INTO `puzzle_configs` (`exercise`, `serialized_config`) VALUES (?, ?)";

        try (Connection connection = DriverManager.getConnection(connectionString); var deleteStatement = connection.prepareStatement(deleteCommand); var insertStatement = connection.prepareStatement(selectCommand);) {
            var exerciseId = exercise.toString();

            connection.setAutoCommit(false);

            deletePrevConfigVersionIfExists(exerciseId, deleteStatement);

            insertPuzzleConfig(exerciseId, puzzleConfigAggregate, insertStatement);

            connection.commit();
        } catch (SQLException e) {
            // TODO use it
            // throw new EventSavingException("failed to append event", e);
            throw new RuntimeException("failed to append event", e);
        }
    }

    private void deletePrevConfigVersionIfExists(final String exerciseId, final PreparedStatement deleteStatement) throws SQLException {
        deleteStatement.setString(1, exerciseId);
        deleteStatement.executeUpdate();
    }

    private void insertPuzzleConfig(final String exerciseId, final PuzzleConfigAggregate<?> puzzleConfigAggregate, final PreparedStatement insertStatement) throws SQLException {
        insertStatement.setString(1, exerciseId);

        var puzzleConfigJson = puzzleConfigJsonSerializer.serializePuzzleConfig(puzzleConfigAggregate);
        insertStatement.setString(2, puzzleConfigJson);

        insertStatement.executeUpdate();
    }
}

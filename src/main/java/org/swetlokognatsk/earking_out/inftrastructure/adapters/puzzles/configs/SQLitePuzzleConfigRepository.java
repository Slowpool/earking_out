package org.swetlokognatsk.earking_out.inftrastructure.adapters.puzzles.configs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import org.swetlokognatsk.earking_out.core.domain.model.base.DependentAggregatesDTO;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfigAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.factories.AbstractPuzzleConfigAggregatesFactory;
import org.swetlokognatsk.earking_out.core.ports.config.PuzzleConfigJsonSerializer;
import org.swetlokognatsk.earking_out.core.ports.config.PuzzleConfigRepository;

// TODO full rewriting. via hibernate?
public final class SQLitePuzzleConfigRepository implements PuzzleConfigRepository {
    // wild cratch to avoid 10000000 configs creating due to recursion in method
    static List<Exercise> alreadyCreatedConfigs = new LinkedList<Exercise>();

    private final AbstractPuzzleConfigAggregatesFactory abstractPuzzleConfigAggregatesFactory;
    private final PuzzleConfigJsonSerializer puzzleConfigJsonSerializer;

    public SQLitePuzzleConfigRepository(final AbstractPuzzleConfigAggregatesFactory abstractPuzzleConfigAggregatesFactory, final PuzzleConfigJsonSerializer puzzleConfigJsonSerializer) {
        this.abstractPuzzleConfigAggregatesFactory = abstractPuzzleConfigAggregatesFactory;
        this.puzzleConfigJsonSerializer = puzzleConfigJsonSerializer;
    }

    public <E extends Exercise, PCA extends PuzzleConfigAggregate<E>> PCA genericGet(final E exercise) {
        // TODO
        var puzzleConfigJson = getPuzzleConfigJson(exercise);
        var aggregate = mapJsonToAggregate(exercise, puzzleConfigJson);
        return (PCA) aggregate;
    }

    private String getPuzzleConfigJson(final Exercise exercise) {
        var fullDbPath = "/Java/earking_out/earking_out.db";
        var connectionString = String.format("jdbc:sqlite:%s", fullDbPath);

        createPuzzleConfigsTable(connectionString);
        return "";
        // var selectCommand = "SELECT `serialized_config` FROM `puzzle_configs` WHERE `exercise` = ?";

        // try (Connection connection = DriverManager.getConnection(connectionString); var statement = connection.prepareStatement(selectCommand);) {
        //     var exerciseDeterminant = buildExerciseDeteminant(exercise);
        //     statement.setString(1, exerciseDeterminant);

        //     var resultSet = statement.executeQuery();
        //     if (puzzleConfigIsFound(resultSet)) {
        //         var puzzleConfigJson = resultSet.getString("serialized_config");
        //         return puzzleConfigJson;
        //     } else {
        //         if (alreadyCreatedConfigs.contains(exercise)) {
        //             throw new RuntimeException("attempt to create config that already exists");
        //         }
        //         createAndSaveDefaultConfig(exercise);
        //         alreadyCreatedConfigs.add(exercise);
        //         return getPuzzleConfigJson(exercise);
        //     }
        // } catch (SQLException e) {
        //     // TODO use it
        //     // throw new EventSavingException("failed to append event", e);
        //     throw new RuntimeException("failed to append event", e);
        // }
    }

    private static void createPuzzleConfigsTable(String connectionString) {
        try (Connection connection = DriverManager.getConnection(connectionString); var statement = connection.createStatement();) {
            // statement.executeUpdate("CREATE TABLE `event_sourcing_events` (`id` TEXT NOT NULL, `type` TEXT NOT NULL, `created_on` TEXT NOT NULL, `payload` TEXT NOT NULL)");
            // statement.executeUpdate("INSERT INTO `event_sourcing_events` VALUES (\"test\", \"\", \"\", \"\")");
            // var result = statement.executeQuery("SELECT * FROM `event_sourcing_events` WHERE `id` = \"test\"");
            // var nextResult = result.next();
            // var deletedRows = statement.executeUpdate("DELETE FROM `event_sourcing_events` WHERE `id` = \"test\"");
            int i = 1;
        } catch (Throwable e) {
            int i = 1;
        }
    }

    private static boolean puzzleConfigIsFound(final ResultSet resultSet) throws SQLException {
        return resultSet.next();
    }

    private static String buildExerciseDeteminant(final Exercise exercise) {
        return String.format("%s_%s", exercise.name.toString(), exercise.type.toString());
    }

    private void createAndSaveDefaultConfig(final Exercise exercise) {
        var factory = abstractPuzzleConfigAggregatesFactory.createFactory(exercise);
        var newPuzzleConfig = factory.createDefault(null);
        genericSave(newPuzzleConfig);
    }

    private <E extends Exercise, PCA extends PuzzleConfigAggregate<E>> PCA mapJsonToAggregate(final E exercise, final String puzzleConfigJson) {
        var aggregate = puzzleConfigJsonSerializer.deserializePuzzleConfig(exercise, puzzleConfigJson);
        return (PCA) aggregate;
    }

    public void genericSave(final PuzzleConfigAggregate<?> puzzleConfigAggregate) {
        // TODO
    }

}

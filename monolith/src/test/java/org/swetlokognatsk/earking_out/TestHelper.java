package org.swetlokognatsk.earking_out;

import static org.junit.jupiter.api.Assertions.*;
import java.sql.SQLException;
import java.util.Set;
import org.swetlokognatsk.earking_out.core.domain.model.base.AggregateNotFoundException;
import org.swetlokognatsk.earking_out.core.domain.model.identity.Languages;
import org.swetlokognatsk.earking_out.core.domain.model.identity.TestUserResolver;
import org.swetlokognatsk.earking_out.core.domain.model.identity.UserAggregatesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.identity.UserId;
import org.swetlokognatsk.earking_out.core.domain.model.identity.UserUuid;
import org.swetlokognatsk.earking_out.core.ports.di.DI;
import org.swetlokognatsk.earking_out.core.ports.identity.UserRepository;
import org.swetlokognatsk.earking_out.infrastructure.adapters.identity.SpringJpaUserRepository;
import jakarta.persistence.EntityManager;
import liquibase.exception.LiquibaseException;
import liquibase.integration.spring.SpringLiquibase;
import javax.sql.DataSource;

public final class TestHelper {
    public static final UserId USER_ID = new UserId(1);
    public static final UserId ANOTHER_USER_ID = new UserId(2);

    public static void ensureH2IsUsed() {
        ensureThisDatabaseIsUsed("H2");
    }

    public static void ensurePostgresIsUsed() {
        ensureThisDatabaseIsUsed("postgresql");
    }

    private static void ensureThisDatabaseIsUsed(String thisDatabase) {
        var dataSource = DI.get(DataSource.class);
        thisDatabase = thisDatabase.toLowerCase();
        try {
            var db = dataSource.getConnection()
                    .getMetaData()
                    .getDatabaseProductName()
                    .toLowerCase();
            assertEquals(thisDatabase, db);
        } catch (SQLException e) {
            fail();
        }
    }

    public static void switchToUser(final UserId userId) {
        DI.get(TestUserResolver.class)
                .setCurrentUserId(userId);
    }

    public static void switchToHomeUser() {
        DI.get(TestUserResolver.class)
                .setCurrentUserId(USER_ID);
    }

    public static void switchToAnotherUser() {
        DI.get(TestUserResolver.class)
                .setCurrentUserId(ANOTHER_USER_ID);
    }

    public static void rerunLiquibaseMigration() {
        var liquibase = DI.get(SpringLiquibase.class);

        liquibase.setDropFirst(true);
        try {
            liquibase.afterPropertiesSet();
        } catch (LiquibaseException e) {
            throw new RuntimeException(e);
        }
    }
}

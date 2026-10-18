package org.swetlokognatsk.earking_out.infrastructure.adapters.identity;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import static org.swetlokognatsk.earking_out.SpringProfiles.*;
import org.swetlokognatsk.earking_out.core.domain.model.identity.Languages;
import org.swetlokognatsk.earking_out.core.domain.model.identity.UserAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.identity.UserAggregatesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.identity.UserId;
import org.swetlokognatsk.earking_out.core.domain.model.identity.UserUuid;
import org.swetlokognatsk.earking_out.core.ports.identity.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Repository
@Profile({ DESKTOP, WEB })
@AllArgsConstructor
public class SpringJpaUserRepository implements UserRepository {

    private static final String NAME_TEMPLATE = "%s%d";

    @Getter(AccessLevel.PRIVATE)
    private final UserAggregatesFactory userAggregatesFactory;

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public UserAggregate createAndSaveGuestUser(final String namePrefix) {
        var userEntity = new UserEntity("unknown guest", Languages.DEFAULT.value);
        entityManager.persist(userEntity);

        // TODO ensure it works
        var name = NAME_TEMPLATE.formatted(namePrefix, userEntity.getId());
        userEntity.setName(name);
        entityManager.persist(userEntity);

        // TODO what if use get()? will it be in the same transaciton?
        return mapUserEntityToAggregate(userEntity);
    }

    public UserAggregate get(final UserId userId) {
        var userEntity = findById(userId);
        return mapUserEntityToAggregate(userEntity);
    }

    private UserEntity findById(final UserId userId) {
        return entityManager.find(UserEntity.class, userId.id());
    }

    private UserAggregate mapUserEntityToAggregate(final UserEntity userEntity) {
        var userId = new UserId(userEntity.getId());
        var userUuid = new UserUuid(userEntity.getUuid());
        var lang = Languages.valueOf(userEntity.getLang());
        return getUserAggregatesFactory()
                .create(userId, userUuid, userEntity.getName(), lang);
    }

    @Transactional
    public void save(final UserAggregate userAggregate) {
        var userEntity = mapUserAggregateToEntity(userAggregate);
        entityManager.persist(userEntity);
    }

    private UserEntity mapUserAggregateToEntity(final UserAggregate userAggregate) {
        var entity = findById(userAggregate.getId());
        entity.setLang(userAggregate.getLang().value);
        return entity;
    }
}

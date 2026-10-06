package org.swetlokognatsk.earking_out.infrastructure.adapters.identity;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.swetlokognatsk.earking_out.core.domain.model.identity.UserAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.identity.UserAggregatesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.identity.UserId;
import org.swetlokognatsk.earking_out.core.ports.identity.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.AllArgsConstructor;

@Repository
@Lazy
@AllArgsConstructor
public class SpringJpaUserRepository implements UserRepository {

    private static final String NAME_TEMPLATE = "%s_%d";

    private final UserAggregatesFactory userAggregatesFactory;
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public UserAggregate createAndSaveGuestUser(final String namePrefix) {
        var userEntity = new UserEntity("unknown guest");
        entityManager.persist(userEntity);

        // TODO ensure it works
        var name = NAME_TEMPLATE.formatted(namePrefix, userEntity.getId());
        userEntity.setName(name);
        entityManager.persist(userEntity);

        // TODO what if use get()? will it be in the same transaciton?
        return mapUserEntityToAggregate(userEntity);
    }

    @Transactional
    public UserAggregate get(final UserId id) {
        var userEntity = entityManager.find(UserEntity.class, id);
        return mapUserEntityToAggregate(userEntity);
    }

    private UserAggregate mapUserEntityToAggregate(final UserEntity userEntity) {
        return userAggregatesFactory.create(userEntity.getId(), userEntity.getUuid(), userEntity.getName());
    }

    @Transactional
    public void save(final UserAggregate userAggregate) {
        entityManager.persist(userAggregate);
    }

}

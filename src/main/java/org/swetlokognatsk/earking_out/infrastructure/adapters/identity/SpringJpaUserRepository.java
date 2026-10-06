package org.swetlokognatsk.earking_out.infrastructure.adapters.identity;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.swetlokognatsk.earking_out.core.domain.model.identity.UserAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.identity.UserId;
import org.swetlokognatsk.earking_out.core.ports.identity.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
@Lazy
public class SpringJpaUserRepository implements UserRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public UserAggregate createAndSaveGuestUser(final String namePrefix) {
        
    }

    @Transactional
    public UserAggregate get(final UserId id) {
        var userEntity = entityManager.find(UserEntity.class, id);
        return mapUserEntityToAggregate(userEntity);
    }

    private UserAggregate mapUserEntityToAggregate(final UserEntity userEntity) {

    }

    @Transactional
    public void save(final UserAggregate userAggregate) {
        entityManager.persist(userAggregate);
    }

}

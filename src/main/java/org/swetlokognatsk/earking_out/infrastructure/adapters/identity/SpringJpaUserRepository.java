package org.swetlokognatsk.earking_out.infrastructure.adapters.identity;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import static org.swetlokognatsk.earking_out.SpringProfiles.*;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;
import org.swetlokognatsk.earking_out.core.domain.model.base.AggregateNotFoundException;
import org.swetlokognatsk.earking_out.core.domain.model.identity.Languages;
import org.swetlokognatsk.earking_out.core.domain.model.identity.UserAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.identity.UserAggregatesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.identity.UserId;
import org.swetlokognatsk.earking_out.core.domain.model.identity.UserUuid;
import org.swetlokognatsk.earking_out.core.domain.model.identity.oauth.OauthIdentity;
import org.swetlokognatsk.earking_out.core.domain.model.identity.oauth.OauthIdentityId;
import org.swetlokognatsk.earking_out.core.domain.model.identity.oauth.OauthProviders;
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

    @Transactional
    public UserAggregate get(final UserId userId) {
        var userEntity = findById(userId);
        if (userEntity == null) {
            throw new AggregateNotFoundException();
        }
        return mapUserEntityToAggregate(userEntity);
    }

    // TODO according clean ddd, it should be this way, in userSerivce:
    // 
    // var user = repository.get();
    // user.attachNewOauthIdentity(new NewOauthIdentity(/* the same properties as in OauthIdentity except Id */)); // also changing the user name
    // repository.save(user);
    // 
    // then, inside repository:
    //
    // users.save(user); // saving new name
    // oauthIdentities.save(user.newOauthIdentity); // creates OauthIdenity, assigning new id
    //
    @Transactional
    public void attachOauthIdentity(final UserId userId, final OauthProviders provider, final String providerUserId, final String userName, final String pictureUrl) {
        saveNewOauthIdentity(userId, provider, providerUserId, pictureUrl);

        updateUserName(userId, userName);
    }

    @Transactional
    private void saveNewOauthIdentity(final UserId userId, final OauthProviders provider, final String providerUserId, final String pictureUrl) {
        var newOauthIdentity = new OauthIdentityEntity(userId.id(), provider, providerUserId, pictureUrl, LocalDateTime.now(), LocalDateTime.now());
        entityManager.persist(newOauthIdentity);
    }

    @Transactional
    private void updateUserName(final UserId userId, final String userName) {
        var user = get(userId);
        user.setName(userName);
        save(user);
    }

    @Transactional
    private UserEntity findById(final UserId userId) {
        return entityManager.find(UserEntity.class, userId.id());
    }

    @Transactional
    private UserAggregate mapUserEntityToAggregate(final UserEntity userEntity) {
        var userId = new UserId(userEntity.getId());
        var userUuid = new UserUuid(userEntity.getUuid());
        var lang = Languages.valueOf(userEntity.getLang());
        var oauthIdentities = userEntity.getOauthIdentities()
                .stream()
                .map(this::mapOauthIdentityEntityToAggregate)
                .collect(Collectors.toSet());
        return getUserAggregatesFactory()
                .create(userId, userUuid, userEntity.getName(), lang, oauthIdentities);
    }

    private OauthIdentity mapOauthIdentityEntityToAggregate(final OauthIdentityEntity entity) {
        var id = new OauthIdentityId(entity.getId());
        var userId = new UserId(entity.userId);
        return new OauthIdentity(id, userId, entity.provider, entity.providerUserId, entity.getPictureUrl(), entity.getConnectedAt(), entity.getLastLoginOn());
    }

    @Transactional
    public void save(final UserAggregate userAggregate) {
        var userEntity = mapUserAggregateToEntity(userAggregate);
        entityManager.persist(userEntity);
    }

    private UserEntity mapUserAggregateToEntity(final UserAggregate userAggregate) {
        var entity = findById(userAggregate.getId());
        entity.setLang(userAggregate.getLang().value);
        entity.setName(userAggregate.getName());
        return entity;
    }
}

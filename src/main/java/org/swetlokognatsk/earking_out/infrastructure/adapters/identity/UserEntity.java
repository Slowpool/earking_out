package org.swetlokognatsk.earking_out.infrastructure.adapters.identity;

import org.swetlokognatsk.earking_out.core.domain.model.identity.UserId;
import org.swetlokognatsk.earking_out.core.domain.model.identity.UserUuid;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    // TODO explore these strategies
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UserId id;
    private UserUuid uuid;
    private String name;

    public UserId getId() {
        return id;
    }

    public UserUuid getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        if (name == null) {
            throw new IllegalArgumentException("invalid name");
        }

        this.name = name;
    }

    public UserEntity() {
    }

    public UserEntity(final String name) {
        this.uuid = UserUuid.random();
        this.name = name;
    }

    public UserEntity(final UserId id, final UserUuid uuid, final String name) {
        this.id = id;
        this.uuid = uuid;
        this.name = name;
    }
}

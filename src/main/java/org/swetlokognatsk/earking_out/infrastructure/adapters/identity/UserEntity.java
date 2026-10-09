package org.swetlokognatsk.earking_out.infrastructure.adapters.identity;

import java.util.UUID;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.swetlokognatsk.earking_out.core.domain.model.identity.UserUuid;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JdbcTypeCode(SqlTypes.UUID)
    @Column(nullable = false)
    // TODO use this from jakarta.validation. package: https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-validation
    // @NotNull
    private UUID uuid;
    
    @Column(nullable = false)
    private String name;

    public int getId() {
        return id;
    }

    public UUID getUuid() {
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
        this.uuid = UserUuid.random()
                .uuid();
        this.name = name;
    }

    public UserEntity(final int id, final UUID uuid, final String name) {
        this.id = id;
        this.uuid = uuid;
        this.name = name;
    }
}

package org.swetlokognatsk.earking_out.infrastructure.adapters.identity;

import java.time.LocalDateTime;
import org.swetlokognatsk.earking_out.core.domain.model.identity.UserId;
import org.swetlokognatsk.earking_out.core.domain.model.identity.oauth.OauthProviders;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "oauth_identity")
@NoArgsConstructor
@Getter
public class OauthIdentityEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;

    @Column(name = "user_id")
    public int userId;

    @Column(name = "provider_id")
    @Convert(converter = OauthProviderConverter.class)
    public OauthProviders provider;

    @Column(name = "provider_user_id")
    public String providerUserId;

    @Column(name = "picture_url")
    @Setter
    private String pictureUrl;

    @Column(name = "connected_at")
    private LocalDateTime connectedAt;

    @Setter
    @Column(name = "last_login_on")
    private LocalDateTime lastLoginOn;

    public OauthIdentityEntity(final int id, final int userId, final OauthProviders provider, final String providerUserId, final String pictureUrl, final LocalDateTime connectedAt, final LocalDateTime lastLoginAt) {
        this.id = id;
        this.userId = userId;
        this.provider = provider;
        this.providerUserId = providerUserId;
        this.pictureUrl = pictureUrl;
        this.connectedAt = connectedAt;
        this.lastLoginOn = lastLoginAt;
    }

    public OauthIdentityEntity(final int userId, final OauthProviders provider, final String providerUserId, final String pictureUrl, final LocalDateTime connectedAt, final LocalDateTime lastLoginAt) {
        this.userId = userId;
        this.provider = provider;
        this.providerUserId = providerUserId;
        this.pictureUrl = pictureUrl;
        this.connectedAt = connectedAt;
        this.lastLoginOn = lastLoginAt;
    }
}

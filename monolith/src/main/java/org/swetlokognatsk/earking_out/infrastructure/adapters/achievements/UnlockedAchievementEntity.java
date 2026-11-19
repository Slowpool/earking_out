package org.swetlokognatsk.earking_out.infrastructure.adapters.achievements;

import java.time.LocalDateTime;
import java.util.UUID;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "unlocked_achievements")
@NoArgsConstructor
@Getter
public class UnlockedAchievementEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "achievement_id")
    private UUID achievementId;

    // TODO use zoned time
    @Column(name = "unlocked_on")
    private LocalDateTime unlockedOn;

    @Column(name = "notification_is_sent")
    private boolean notificationIsSent;

    public UnlockedAchievementEntity(final UUID id, final Integer userId, final UUID achievementId, final LocalDateTime unlockedOn, final boolean notificationIsSent) {
        this.id = id;
        this.userId = userId;
        this.achievementId = achievementId;
        this.unlockedOn = unlockedOn;
        this.notificationIsSent = notificationIsSent;
    }

    public UnlockedAchievementEntity(final Integer userId, final UUID achievementId, final LocalDateTime unlockedOn, final boolean notificationIsSent) {
        this(null, userId, achievementId, unlockedOn, notificationIsSent);
    }
}

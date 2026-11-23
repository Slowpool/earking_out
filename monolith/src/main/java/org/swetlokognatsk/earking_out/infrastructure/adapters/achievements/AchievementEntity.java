package org.swetlokognatsk.earking_out.infrastructure.adapters.achievements;

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
@Table(name = "achievements")
@NoArgsConstructor
public class AchievementEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Getter
    private UUID id;

    @Column(name = "technical_name")
    @Getter
    private String technicalName;

    public AchievementEntity(final String technicalName) {
        this.technicalName = technicalName;
    }
}

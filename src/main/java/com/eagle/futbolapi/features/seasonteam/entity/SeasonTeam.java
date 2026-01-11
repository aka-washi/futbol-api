package com.eagle.futbolapi.features.seasonteam.entity;

import java.time.LocalDate;
import java.util.Objects;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;

import com.eagle.futbolapi.features.season.entity.Season;
import com.eagle.futbolapi.features.shared.BaseEntity;
import com.eagle.futbolapi.features.team.entity.Team;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@Accessors(chain = false)
@Entity
@Table(name = "season_team", uniqueConstraints = {
                @UniqueConstraint(columnNames = { "season_id", "team_id" })
})
@AttributeOverrides({
                @AttributeOverride(name = "id", column = @Column(name = "se_id")),
                @AttributeOverride(name = "createdAt", column = @Column(name = "se_created_at", nullable = false, updatable = false)),
                @AttributeOverride(name = "createdBy", column = @Column(name = "se_created_by", length = 100, updatable = false)),
                @AttributeOverride(name = "updatedAt", column = @Column(name = "se_updated_at")),
                @AttributeOverride(name = "updatedBy", column = @Column(name = "se_updated_by", length = 100))
})
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class SeasonTeam extends BaseEntity {

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "season_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
        @NotNull
        private Season season;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "team_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
        @NotNull
        private Team team;

        @NotNull
        @Column(name = "se_joined_date", nullable = false)
        private LocalDate joinedDate;

        @Column(name = "se_left_date")
        private LocalDate leftDate;

        @Column(name = "set_active", nullable = false)
        @Builder.Default
        private Boolean active = true;

        @Override
        public int hashCode() {
                return Objects.hash(
                        season,
                        team,
                        joinedDate,
                        leftDate,
                        active
                );
        }

        @Override
        public boolean equals(Object obj) {
                if (this == obj)
                        return true;
                if (!(obj instanceof SeasonTeam))
                        return false;
                SeasonTeam other = (SeasonTeam) obj;
                return Objects.equals(season, other.season)
                        && Objects.equals(team, other.team)
                        && Objects.equals(joinedDate, other.joinedDate)
                        && Objects.equals(leftDate, other.leftDate)
                        && Objects.equals(active, other.active);
        }
}

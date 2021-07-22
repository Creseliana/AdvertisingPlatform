package com.creseliana.model;

import com.creseliana.RatingLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ratings")
public class Rating extends Model implements Serializable {
    @Serial
    private static final long serialVersionUID = -5550554714144631841L;

    @Enumerated(EnumType.STRING)
    @Column(name = "level")
    private RatingLevel level;
    @ManyToOne
    @JoinColumn(name = "rater_id")
    private User rater;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Column(name = "date")
    private LocalDateTime date;

    @Override
    public String toString() {
        return "Rating{" +
                "id=" + getId() +
                ", level=" + level +
                ", rater=" + rater.getId() +
                ", user=" + user.getId() +
                ", date=" + date +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Rating)) return false;
        if (!super.equals(o)) return false;
        Rating rating = (Rating) o;
        return level == rating.level && rater.equals(rating.rater)
                && user.equals(rating.user) && date.equals(rating.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), level, rater, user, date);
    }
}

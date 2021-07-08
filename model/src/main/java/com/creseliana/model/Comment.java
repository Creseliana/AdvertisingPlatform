package com.creseliana.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "comments")
public class Comment extends Model implements Serializable {
    @Serial
    private static final long serialVersionUID = 8529853455705018364L;

    @ManyToOne
    @JoinColumn(name = "ad_id")
    private Advertisement ad;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User author;
    @Column(name = "date")
    private LocalDateTime date;
    @Column(name = "comment")
    private String comment;

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + getId() +
                ", ad=" + ad +
                ", author=" + author +
                ", date=" + date +
                ", comment='" + comment + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Comment)) return false;
        if (!super.equals(o)) return false;
        Comment comment1 = (Comment) o;
        return ad.equals(comment1.ad) && author.equals(comment1.author)
                && date.equals(comment1.date) && comment.equals(comment1.comment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), ad, author, date, comment);
    }
}

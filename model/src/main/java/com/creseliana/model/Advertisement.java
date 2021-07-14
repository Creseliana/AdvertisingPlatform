package com.creseliana.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ads")
public class Advertisement extends Model implements Serializable {
    @Serial
    private static final long serialVersionUID = -4522831499426542322L;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User author;
    @NotEmpty
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    @NotEmpty
    @Column(name = "title")
    private String title;
    @NotEmpty
    @Column(name = "description")
    private String description;
    @NotEmpty
    @Column(name = "price")
    private BigDecimal price;
    @Column(name = "creation_date")
    private LocalDateTime creationDate;
    @OneToMany(mappedBy = "ad")
    private List<Comment> comments;
    @OneToMany(mappedBy = "ad")
    private List<Image> images;
    @Column(name = "is_closed")
    private boolean isClosed;
    @Column(name = "is_deleted")
    private boolean isDeleted;

    @Override
    public String toString() {
        return "Advertisement{" +
                "id=" + getId() +
                ", author=" + author +
                ", category=" + category +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", creationDate=" + creationDate +
                ", comments=" + comments +
                ", images=" + images +
                ", isClosed=" + isClosed +
                ", isDeleted=" + isDeleted +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Advertisement)) return false;
        if (!super.equals(o)) return false;
        Advertisement that = (Advertisement) o;
        return isClosed == that.isClosed && isDeleted == that.isDeleted
                && author.equals(that.author) && category.equals(that.category)
                && title.equals(that.title) && description.equals(that.description)
                && Objects.equals(price, that.price) && creationDate.equals(that.creationDate)
                && Objects.equals(comments, that.comments) && Objects.equals(images, that.images);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), author, category, title, description,
                price, creationDate, comments, images, isClosed, isDeleted);
    }
}

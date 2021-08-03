package com.creseliana.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ads")
public class Advertisement extends Model implements Serializable {
    private static final long serialVersionUID = -4522831499426542322L;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User author;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    @Column(name = "title")
    private String title;
    @Column(name = "description")
    private String description;
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
                ", isClosed=" + isClosed +
                ", isDeleted=" + isDeleted +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Advertisement that = (Advertisement) o;
        return author.equals(that.author) && category.equals(that.category)
                && title.equals(that.title) && description.equals(that.description)
                && price.equals(that.price) && creationDate.equals(that.creationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(author, category, title, description, price, creationDate);
    }
}

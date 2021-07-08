package com.creseliana.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serial;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "images")
public class Image extends Model implements Serializable {
    @Serial
    private static final long serialVersionUID = 107674908043675321L;

    @ManyToOne
    @JoinColumn(name = "ad_id")
    private Advertisement ad;
    @Column(name = "name")
    private String name;
    @Lob
    @Column(name = "content")
    private byte[] content;

    @Override
    public String toString() {
        return "Image{" +
                "id=" + getId() +
                ", ad=" + ad +
                ", name='" + name + '\'' +
                ", content=" + Arrays.toString(content) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Image)) return false;
        if (!super.equals(o)) return false;
        Image image = (Image) o;
        return ad.equals(image.ad) && name.equals(image.name) && Arrays.equals(content, image.content);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(super.hashCode(), ad, name);
        result = 31 * result + Arrays.hashCode(content);
        return result;
    }
}

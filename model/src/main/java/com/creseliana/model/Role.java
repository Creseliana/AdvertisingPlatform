package com.creseliana.model;

import com.creseliana.RoleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "roles")
public class Role extends Model implements GrantedAuthority, Serializable {
    @Serial
    private static final long serialVersionUID = -4613393898862773865L;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private RoleType type;

    @Override
    public String getAuthority() {
        return this.type.toString();
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + getId() +
                ", type=" + type +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Role)) return false;
        if (!super.equals(o)) return false;
        Role role = (Role) o;
        return type == role.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), type);
    }
}

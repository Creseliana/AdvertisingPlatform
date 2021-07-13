package com.creseliana.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User extends Model implements UserDetails, Serializable {
    @Serial
    private static final long serialVersionUID = -6430820976776918962L;

    @NotNull()
    @Column(name = "username")
    private String username;
    @NotNull
    @Column(name = "password")
    private String password;
    @NotNull
    @Column(name = "first_name")
    private String firstName;
    @NotNull
    @Column(name = "last_name")
    private String lastName;
    @Email
    @Column(name = "email")
    private String email;
    @NotNull
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "is_active")
    private boolean isActive;
    @Column(name = "registration_date")
    private LocalDateTime registrationDate;
    @ElementCollection
    @CollectionTable(name = "user_rating",
            joinColumns = @JoinColumn(name = "rating"))
    private List<Byte> ratings;
    @Column(name = "rating")
    private BigDecimal rating;
    @ManyToMany
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;
    @OneToMany(mappedBy = "author")
    private List<Advertisement> ads;
    @ManyToMany
    @JoinTable(name = "user_chat",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "chat_id"))
    private List<Chat> chats;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.isActive;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return this.isActive;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + getId() +
                ", username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", isActive=" + isActive +
                ", registrationDate=" + registrationDate +
                ", ratings=" + ratings +
                ", rating=" + rating +
                ", roles=" + roles +
                ", ads=" + ads +
                ", chats=" + chats +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        if (!super.equals(o)) return false;
        User user = (User) o;
        return isActive == user.isActive && username.equals(user.username)
                && password.equals(user.password) && firstName.equals(user.firstName)
                && lastName.equals(user.lastName) && email.equals(user.email)
                && phoneNumber.equals(user.phoneNumber) && registrationDate.equals(user.registrationDate)
                && Objects.equals(ratings, user.ratings) && Objects.equals(rating, user.rating)
                && roles.equals(user.roles) && Objects.equals(ads, user.ads) && Objects.equals(chats, user.chats);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), username, password, firstName, lastName, email,
                phoneNumber, isActive, registrationDate, ratings, rating, roles, ads, chats);
    }
}

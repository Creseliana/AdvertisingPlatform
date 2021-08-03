package com.creseliana.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "chats")
public class Chat extends Model implements Serializable {
    private static final long serialVersionUID = -1053337832332682329L;

    @ManyToOne
    @JoinColumn(name = "first_user_id")
    private User firstUser;
    @ManyToOne
    @JoinColumn(name = "second_user_id")
    private User secondUser;

    @Override
    public String toString() {
        return "Chat{" +
                "id=" + getId() +
                ", firstUser=" + firstUser +
                ", secondUser=" + secondUser +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Chat chat = (Chat) o;
        return firstUser.equals(chat.firstUser) && secondUser.equals(chat.secondUser);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstUser, secondUser);
    }
}

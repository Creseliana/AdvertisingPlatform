package com.creseliana.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "chats")
public class Chat extends Model implements Serializable {
    @Serial
    private static final long serialVersionUID = -1053337832332682329L;

    @ManyToMany(mappedBy = "chats")
    private Set<User> users;
    @OneToMany(mappedBy = "chat")
    private List<Message> messages;

    @Override
    public String toString() {
        return "Chat{" +
                "id=" + getId() +
                ", users=" + users +
                ", messages=" + messages +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Chat)) return false;
        if (!super.equals(o)) return false;
        Chat chat = (Chat) o;
        return Objects.equals(users, chat.users) && Objects.equals(messages, chat.messages);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), users, messages);
    }
}
